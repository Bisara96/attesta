package com.fyp.agent.handlers;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.fyp.agent.models.*;
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

        recordDBH.dropExisitingMapping(id);

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
		driver.quit();
        int storyID = json.getInt("id");
        UserStory story = ustoryDBH.getUserStory(storyID);
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
                    typeStep.setId(addStoryStep(typeStep, story));
                    testStepList.add(typeStep);
                    break;
                case CLICK:
                    TestStep clickStep = new ClickStep(TestStepTypes.CLICK, step.getString("screenshot"), uiObj,
                            step.getInt("x"), step.getInt("x"));
                    clickStep.setId(addStoryStep(clickStep, story));
                    testStepList.add(clickStep);
                    break;
                case PRESS:
                    TestStep pressKeyStep = new PressKeyStep(TestStepTypes.PRESS, step.getString("screenshot"), uiObj,
                            step.getInt("keyCode"));
                    pressKeyStep.setId(addStoryStep(pressKeyStep, story));
                    testStepList.add(pressKeyStep);
                    break;
                case SELECT:
                    TestStep selectStep = new SelectStep(TestStepTypes.SELECT, step.getString("screenshot"), uiObj,
                            step.getString("selectedOption"));
                    selectStep.setId(addStoryStep(selectStep, story));
                    testStepList.add(selectStep);
                    break;
                default:
                    break;
            }
        }
//        generateTestCases(storyID, testStepList);
        TestCaseHandler hand = new TestCaseHandler();
        hand.generateTestCases(storyID);
        return stepsArray.toString();

    }

    public Boolean checkStatus(int id) {
        return recordDBH.getStorySteps(id) == 0 ? Boolean.TRUE : Boolean.FALSE;
    }

    private int addStoryStep(TestStep step,UserStory ustory) {
        int testStepID = recordDBH.addTestStep(step);
        step.setId(testStepID);
        recordDBH.addUStoryStep(new UserStorySteps(ustory, step));
        return testStepID;
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

}
