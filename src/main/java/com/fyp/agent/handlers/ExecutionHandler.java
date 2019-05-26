package com.fyp.agent.handlers;

import com.fyp.agent.dbhandlers.ExecutionDBHandler;
import com.fyp.agent.dbhandlers.UserStoryDBHandler;
import com.fyp.agent.models.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.Select;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ExecutionHandler {

    WebDriver driver = null;
    ExecutionDBHandler executionDBH;
    UserStoryDBHandler ustoryDBH;

    public ExecutionHandler() {
        executionDBH = new ExecutionDBHandler();
        ustoryDBH = new UserStoryDBHandler();
    }

    public String executeStory(int id) throws MalformedURLException {
        List<TestCase> testCases = executionDBH.getStoryTestCases(id);
        final DesiredCapabilities capability = DesiredCapabilities.chrome();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("user-data-dir=D:\\Documents\\IIT\\FinalYear\\FYP\\Implementation\\Misc\\ChromeProfile2");
        options.addArguments("--start-maximized");
        capability.setCapability(ChromeOptions.CAPABILITY, options);

        driver = new RemoteWebDriver(new URL("http://192.168.56.1:4444/wd/hub"), capability);
        for(TestCase tc : testCases){
            executeTestCase(tc);
        }

        driver.quit();
        return "Broo did you see that?";
    }

    public String executeTestCase(int id) throws MalformedURLException {

        TestCase testCase = executionDBH.getTestCase(id);
        String url = testCase.getuStory().getUrl();

        final DesiredCapabilities capability = DesiredCapabilities.chrome();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("user-data-dir=D:\\Documents\\IIT\\FinalYear\\FYP\\Implementation\\Misc\\ChromeProfile2");
        options.addArguments("--start-maximized");
        capability.setCapability(ChromeOptions.CAPABILITY, options);

        driver = new RemoteWebDriver(new URL("http://192.168.56.1:4444/wd/hub"), capability);
        driver.get(url);

        List<TestCaseSteps> tcSteps = executionDBH.getTestCaseSteps(id);
        for (TestCaseSteps st : tcSteps) {
            TestStep step = st.getTestStep();
            UIObject obj = step.getUiObject();

            try {
                WebElement element = driver.findElement(By.xpath(obj.getXpath()));

                switch (step.getStepType()) {
                    case CLICK:
                        System.out.println("Executing step : Click on " + obj.getName());
                        try {
                            element.click();
                        } catch (NoSuchElementException e) {
                            JavascriptExecutor js = (JavascriptExecutor) driver;
                            js.executeScript("arguments[0].click();", element);
                        }
                        break;
                    case TYPE:
                        System.out.println("Executing step : Type " + ((TypeStep) step).getValue() + " on " + obj.getXpath());
                        element.sendKeys(((TypeStep) step).getValue());
                        break;
                    case PRESS:
                        System.out.println("Executing step : Press " + ((PressKeyStep) step).getKeyCode() + " on " + obj.getXpath());
                        element.sendKeys(getKeyFromkeyCode(((PressKeyStep) step).getKeyCode()));
                        break;
                    case SELECT:
                        System.out.println("Executing step : Select " + ((SelectStep) step).getSelectedOption() + " of " + obj.getXpath());
                        Select dropdown = new Select(element);
                        dropdown.selectByVisibleText(((SelectStep) step).getSelectedOption());
                        break;
                }

                String base64 = ((TakesScreenshot)driver).getScreenshotAs(OutputType.BASE64);
                System.out.println(base64);

            } catch (ElementNotVisibleException e) {
                System.out.println("Couldnt find the element!. Step failed, moving onto the next step.");
                continue;
            }
        }
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        testCase.setLastExecutedDate(dateFormat.format(date));
        executionDBH.updateTestCase(testCase);
//        driver.quit();
        return "Okayy";
    }

    public String executeTestCase(TestCase testCase) throws MalformedURLException {
        int id = testCase.getId();
        String url = testCase.getuStory().getUrl();
        driver.get(url);

        List<TestCaseSteps> tcSteps = executionDBH.getTestCaseSteps(id);
        for (TestCaseSteps st : tcSteps) {
            TestStep step = st.getTestStep();
            UIObject obj = step.getUiObject();

            try {
                WebElement element = driver.findElement(By.xpath(obj.getXpath()));

                switch (step.getStepType()) {
                    case CLICK:
                        System.out.println("Executing step : Click on " + obj.getName());
                        try {
                            element.click();
                        } catch (NoSuchElementException e) {
                            JavascriptExecutor js = (JavascriptExecutor) driver;
                            js.executeScript("arguments[0].click();", element);
                        }
                        break;
                    case TYPE:
                        System.out.println("Executing step : Type " + ((TypeStep) step).getValue() + " on " + obj.getXpath());
                        element.sendKeys(((TypeStep) step).getValue());
                        break;
                    case PRESS:
                        System.out.println("Executing step : Press " + ((PressKeyStep) step).getKeyCode() + " on " + obj.getXpath());
                        element.sendKeys(getKeyFromkeyCode(((PressKeyStep) step).getKeyCode()));
                        break;
                    case SELECT:
                        System.out.println("Executing step : Select " + ((SelectStep) step).getSelectedOption() + " of " + obj.getXpath());
                        Select dropdown = new Select(element);
                        dropdown.selectByVisibleText(((SelectStep) step).getSelectedOption());
                        break;
                }

            } catch (ElementNotVisibleException e) {
                System.out.println("Couldnt find the element!. Step failed, moving onto the next step.");
                continue;
            }
        }
//        driver.quit();
        return "Okayy";
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
