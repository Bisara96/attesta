package com.fyp.agent.handlers;

import java.io.File;
import java.lang.reflect.Type;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.fyp.agent.dbhandlers.RecordDBHandler;
import com.fyp.agent.models.Step;
import com.fyp.agent.models.UserStory;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class RecordHandler {

	WebDriver driver = null;
	RecordDBHandler recordDBH = null;

	public RecordHandler() {
		recordDBH = new RecordDBHandler();
	}

	public String startRecording(String url, int id) throws MalformedURLException {
		
		System.out.println("Initializing record of "+url);
		
		final DesiredCapabilities capability = DesiredCapabilities.chrome();
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--start-maximized");
		options.addExtensions(new File("D:\\Documents\\IIT\\FinalYear\\FYP\\Implementation\\Projects\\test-step-recorder-chrome-ext.crx"));
		capability.setCapability(ChromeOptions.CAPABILITY, options);

		driver = new RemoteWebDriver(new URL("http://192.168.56.1:4444/wd/hub"), capability);
		driver.get(url);

		JavascriptExecutor js = (JavascriptExecutor) driver;
		
		js.executeScript("console.log('hiiiiiiiii')");
		js.executeScript("window.postMessage({ type: 'startRecording', id: "+id+" }, '*');");
		//		driver.quit();
		
		UserStory story = recordDBH.getUserStory(id);
		story.setUrl(url);
		recordDBH.updateUserStory(story);
		return "Recording "+id+" at "+ url;
	}

	public String parseSteps(List<Step> rawSteps,int id) throws MalformedURLException {
		System.out.println("Found " + rawSteps.size() + " steps");
		UserStory story = recordDBH.getUserStory(id);
		story.setStepsJson(new Gson().toJson(rawSteps));
		recordDBH.updateUserStory(story);
//		executeParsedSteps(rawSteps);
		return new Gson().toJson(rawSteps).toString();
	}

	public String executeParsedSteps(int id) throws MalformedURLException {
		
		UserStory story = recordDBH.getUserStory(id);
		String url = story.getUrl();
		String jsonString = story.getStepsJson();
		if(jsonString != null && url != null) {
			
			Type listType = new TypeToken<List<Step>>(){}.getType();
			List<Step> steps = new Gson().fromJson(jsonString, listType);
			
			final DesiredCapabilities capability = DesiredCapabilities.chrome();
			ChromeOptions options = new ChromeOptions();
			options.addArguments("--start-maximized");
			capability.setCapability(ChromeOptions.CAPABILITY, options);

			driver = new RemoteWebDriver(new URL("http://192.168.56.1:4444/wd/hub"), capability);
			driver.get(url);
			
			for (Step step : steps) {
				
				try {
					WebElement element = driver.findElement(By.xpath(step.getXpath()));
					if (step.getType().equalsIgnoreCase("click")) {
						System.out.println("Executing step : Click on " + step.getXpath());
						element.click();
					} else if (step.getType().equalsIgnoreCase("type")) {
						System.out.println("Executing step : Type "+step.getValue()+" on " + step.getXpath());
						element.sendKeys(step.getValue());
					} else {
						System.out.println("Executing step : press "+step.getKeycode()+" on " + step.getXpath());
						element.sendKeys(getKeyFromkeyCode(step.getKeycode()));
					}
				} catch (ElementNotVisibleException e) {
					System.out.println("Couldnt find the element!. Step failed, moving onto the next step.");
					continue;
				}
			}
			return jsonString;			
		} else {
			return "Steps not recorded yet";
		}

	}

	private Keys getKeyFromkeyCode(int keyCode) {
		switch (keyCode) {
		case 8: return Keys.BACK_SPACE;
		case 9: return Keys.TAB;
		case 13: return Keys.ENTER;
		case 16: return Keys.SHIFT;
		case 17: return Keys.CONTROL;
		}
		return null;
	}

}
