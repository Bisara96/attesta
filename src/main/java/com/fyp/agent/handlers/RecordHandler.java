package com.fyp.agent.handlers;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fyp.agent.dbhandlers.RecordDBHandler;
import com.fyp.agent.dbhandlers.UserStoryDBHandler;
import com.fyp.agent.models.AcceptanceCriteria;
import com.fyp.agent.models.ClickStep;
import com.fyp.agent.models.PressKeyStep;
import com.fyp.agent.models.SelectStep;
import com.fyp.agent.models.TestCase;
import com.fyp.agent.models.TestStep;
import com.fyp.agent.models.TestStepTypes;
import com.fyp.agent.models.TypeStep;
import com.fyp.agent.models.UIObject;
import com.fyp.agent.models.UserStory;

public class RecordHandler {

    WebDriver driver = null;
    UserStoryDBHandler ustoryDBH = null;
    RecordDBHandler recordDBH = null;
    List<TestStep> testSteps = new ArrayList<TestStep>();
    List<TestCase> testCases = new ArrayList<TestCase>();

    public RecordHandler() {
        ustoryDBH = new UserStoryDBHandler();
        recordDBH = new RecordDBHandler();
    }

    public String startRecording(String url, int id) throws MalformedURLException {

        System.out.println("Initializing record of " + id + " at " + url);

        final DesiredCapabilities capability = DesiredCapabilities.chrome();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("user-data-dir=D:\\Documents\\IIT\\FinalYear\\FYP\\Implementation\\Misc\\ChromeProfile");
        options.addArguments("--start-maximized");
        capability.setCapability(ChromeOptions.CAPABILITY, options);

        driver = new RemoteWebDriver(new URL("http://192.168.56.1:4444/wd/hub"), capability);
        driver.get(url);

        JavascriptExecutor js = (JavascriptExecutor) driver;

        js.executeScript("window.postMessage({ type: 'startRecording', id: " + id + " }, '*');");
        // driver.quit();

        UserStory story = ustoryDBH.getUserStory(id);
        story.setUrl(url);
        ustoryDBH.updateUserStory(story);
        return "Recording " + id + " at " + url;
    }

    public String parseSteps(JSONObject json) throws MalformedURLException, JSONException {
//		driver.quit();
        int storyID = json.getInt("id");
        JSONArray stepsArray = json.getJSONArray("steps");
        List<TestStep> testStepList = new ArrayList<TestStep>();

        for (int i = 0; i < stepsArray.length(); i++) {

            JSONObject step = stepsArray.getJSONObject(i);
            JSONObject uiObject = step.getJSONObject("UIObject");
            UIObject uiObj = new UIObject(
                    uiObject.getString("name"),
                    uiObject.getString("tagName"),
                    uiObject.getString("label"),
                    uiObject.getString("xpath"),
                    uiObject.getString("innerText"),
                    uiObject.getString("placeholder"),
                    uiObject.getString("elementID")
            );

            uiObj.setId(recordDBH.addUIObject(uiObj));

            TestStepTypes type = TestStepTypes.valueOf(step.getString("type").toUpperCase());

            switch (type) {
                case TYPE:
                    TestStep typeStep = new TypeStep(TestStepTypes.TYPE, step.getString("screenshot"), uiObj,
                            step.getString("value"));
                    typeStep.setId(recordDBH.addTestStep(typeStep));
                    testStepList.add(typeStep);
                    break;
                case CLICK:
                    TestStep clickStep = new ClickStep(TestStepTypes.TYPE, step.getString("screenshot"), uiObj,
                            step.getInt("x"), step.getInt("x"));
                    clickStep.setId(recordDBH.addTestStep(clickStep));
                    testStepList.add(clickStep);
                    break;
                case PRESS:
                    TestStep pressKeyStep = new PressKeyStep(TestStepTypes.TYPE, step.getString("screenshot"), uiObj,
                            step.getInt("keyCode"));
                    pressKeyStep.setId(recordDBH.addTestStep(pressKeyStep));
                    testStepList.add(pressKeyStep);
                    break;
                case SELECT:
                    TestStep selectStep = new SelectStep(TestStepTypes.TYPE, step.getString("screenshot"), uiObj,
                            step.getString("selectedOption"));
                    selectStep.setId(recordDBH.addTestStep(selectStep));
                    testStepList.add(selectStep);
                    break;
                default:
                    break;
            }
        }
        generateTestCases(storyID, testStepList);
        return stepsArray.toString();

    }

    public void generateTestCases(int id, List<TestStep> testSteps) {
        List<AcceptanceCriteria> acList = recordDBH.getStoryACriteria(id);
        String[] rulesList = getRulesOfAcceptanceCriteria(acList);
        for (int i = 0; i < acList.size(); i++) {
            switch (rulesList[i]) {
                case "REQUIRED":
                    List<TestStep> requiredSteps = testSteps;
                    int[] tsIndexls = getTestStep(acList.get(i).getAcceptanceCriteria(), testSteps);
                    for (int index : tsIndexls) {
                        requiredSteps.remove(index);
                    }
                    break;
                case "MinCharacters":
                    break;
                case "Data":
                    break;
                case "Type":
                    break;
                case "Dropdown":
                    break;
            }
        }
    }

    @SuppressWarnings("unchecked")
    public int[] getTestStep(String criteria, List<TestStep> testSteps) {
        String[] words = {"username", "password"};
        int[] tsIndex = new int[words.length];
        for (int x = 0; x < words.length; x++) {
            int maxScore = 0;
            int maxIndex = -999;
            int curIndex = 0;
            for (TestStep ts : testSteps) {
                curIndex++;
                UIObject uiObj = ts.getUiObject();
                ObjectMapper oMapper = new ObjectMapper();
                Map<String, String> map = oMapper.convertValue(uiObj, Map.class);
                Iterator<Entry<String, String>> objIt = map.entrySet().iterator();
                int score = 0;
                while (objIt.hasNext()) {
                    if (objIt.next().getValue().equalsIgnoreCase(words[x])) {
                        score = +1;
                    }
                }
                if (maxScore < score) {
                    maxScore = score;
                    maxIndex = curIndex;
                }
            }
            tsIndex[x] = maxIndex;
        }
        return tsIndex;
    }

//	public String executeParsedSteps(int id) throws MalformedURLException {
//		
//		UserStory story = ustoryDBH.getUserStory(id);
//		String url = story.getUrl();
//		String jsonString = story.getStepsJson();
//		if(jsonString != null && url != null) {
//			
//			Type listType = new TypeToken<List<Step>>(){}.getType();
//			List<Step> steps = new Gson().fromJson(jsonString, listType);
//			
//			final DesiredCapabilities capability = DesiredCapabilities.chrome();
//			ChromeOptions options = new ChromeOptions();
//			options.addArguments("user-data-dir=D:\\Documents\\IIT\\FinalYear\\FYP\\Implementation\\Misc\\ChromeProfile2");
//			options.addArguments("--start-maximized");
//			capability.setCapability(ChromeOptions.CAPABILITY, options);
//
//			driver = new RemoteWebDriver(new URL("http://192.168.56.1:4444/wd/hub"), capability);
//			driver.get(url);
//			
//			for (Step step : steps) {
//				
//				try {
//					WebElement element = driver.findElement(By.xpath(step.getXpath()));
//					if (step.getType().equalsIgnoreCase("click")) {
//						System.out.println("Executing step : Click on " + step.getXpath());
//						try {
//							element.click();
//						} catch (NoSuchElementException e) {
//							JavascriptExecutor js = (JavascriptExecutor)driver;
//							js.executeScript("arguments[0].click();", element);
//						}
//					} else if (step.getType().equalsIgnoreCase("type")) {
//						System.out.println("Executing step : Type "+step.getValue()+" on " + step.getXpath());
//						element.sendKeys(step.getValue());
//					} else if (step.getType().equalsIgnoreCase("press")) {
//						System.out.println("Executing step : Press "+step.getKeycode()+" on " + step.getXpath());
//						element.sendKeys(getKeyFromkeyCode(step.getKeycode()));
//					} else if (step.getType().equalsIgnoreCase("select")) {
//						System.out.println("Executing step : Select "+step.getValue()+" of " + step.getXpath());
//						Select dropdown = new Select(element);
//						dropdown.selectByVisibleText(step.getValue());
//					}
//				} catch (ElementNotVisibleException e) {
//					System.out.println("Couldnt find the element!. Step failed, moving onto the next step.");
//					continue;
//				}
//			}
//			return jsonString;			
//		} else {
//			return "Steps not recorded yet";
//		}
//
//	}

    private String[] getRulesOfAcceptanceCriteria(List<AcceptanceCriteria> acList) {
        String[] rulesArray = new String[10];
        return rulesArray;
    }

    private Keys getKeyFromkeyCode(int keyCode) {
        switch (keyCode) {
            case 8:
                return Keys.BACK_SPACE;
            case 9:
                return Keys.TAB;
            case 13:
                return Keys.ENTER;
            case 16:
                return Keys.SHIFT;
            case 17:
                return Keys.CONTROL;
        }
        return null;
    }

}
