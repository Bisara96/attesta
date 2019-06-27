package com.fyp.agent.handlers;

import com.fyp.agent.dbhandlers.RecordDBHandler;
import com.fyp.agent.dbhandlers.UserStoryDBHandler;
import com.fyp.agent.models.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Component
@Service
public class RecordHandler {

    @Autowired
    private Environment env;

    private RecordDBHandler recordDBH;

    private UserStoryDBHandler ustoryDBH;

    private TestCaseHandler testCaseHandler;

    private WebDriver driver = null;
    private List<TestStep> testSteps = new ArrayList<TestStep>();
    private List<TestCase> testCases = new ArrayList<TestCase>();

    public RecordHandler(RecordDBHandler recordDBH, UserStoryDBHandler ustoryDBH, TestCaseHandler testCaseHandler) {
        this.recordDBH = recordDBH;
        this.ustoryDBH = ustoryDBH;
        this.testCaseHandler = testCaseHandler;
    }

    public String startRecording(String url, int id, String agent) throws MalformedURLException {

        recordDBH.dropExisitingMapping(id);

        System.out.println("Initializing record of " + id + " at " + url);

        final DesiredCapabilities capability = DesiredCapabilities.chrome();
        String chromeProf = this.getClass().getClassLoader().getResource("chrome/ChromeProfile").getPath();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("user-data-dir=C://Projects//AT2//agent//target//classes//chrome//ChromeProfile");
        options.addArguments("--start-maximized");
        capability.setCapability("applicationName", agent);
        capability.setCapability(ChromeOptions.CAPABILITY, options);

        driver = new RemoteWebDriver(new URL("http://"+env.getProperty("server.address")+":4444/wd/hub"), capability);
        driver.get(url);

        JavascriptExecutor js = (JavascriptExecutor) driver;

        js.executeScript("window.postMessage({ type: 'startRecording', id: " + id + ", server: 'http://"+env.getProperty("server.address")+":8080' }, '*');");
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

        testCaseHandler.generateTestCases(storyID);
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

}
