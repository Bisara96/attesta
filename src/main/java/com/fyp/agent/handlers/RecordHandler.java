package com.fyp.agent.handlers;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
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

import com.fyp.agent.models.ParsedStep;
import com.fyp.agent.models.Step;

public class RecordHandler {

	WebDriver driver = null;

	public RecordHandler() {
	}

	public String startRecording(String url) throws MalformedURLException {
		
		final DesiredCapabilities capability = DesiredCapabilities.chrome();
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--start-maximized");
		options.addExtensions(new File("D:\\Documents\\IIT\\FinalYear\\FYP\\Implementation\\Projects\\test-step-recorder-chrome-ext.crx"));
		capability.setCapability(ChromeOptions.CAPABILITY, options);

		driver = new RemoteWebDriver(new URL("http://192.168.56.1:4444/wd/hub"), capability);
		driver.get(url);

		JavascriptExecutor js = (JavascriptExecutor) driver;
		
		js.executeScript("console.log('hiiiiiiiii')");
		js.executeScript("window.postMessage({ type: 'startRecording' }, '*');");
		//		driver.quit();

		return "Recorded at " + url;
	}

	public List<ParsedStep> parseSteps(List<Step> rawSteps) throws MalformedURLException {
		System.out.println("Found " + rawSteps.size() + " steps");
		List<ParsedStep> parsedSteps = new ArrayList<ParsedStep>();
		for (Step step : rawSteps) {
			System.out.println(step.getType() + " " + step.getXpath());
			if (step.getType().equalsIgnoreCase("click")) {
				ParsedStep pStep = new ParsedStep();
				pStep.setType(step.getType());
				pStep.setXpath(step.getXpath());
				parsedSteps.add(pStep);
			} else if (step.getType().equalsIgnoreCase("type")){
				ParsedStep pStep = new ParsedStep();
				pStep.setType(step.getType());
				pStep.setXpath(step.getXpath());
				pStep.setValue(step.getValue());
				parsedSteps.add(pStep);
			}
		}

		return executeParsedSteps(parsedSteps);

	}

	public List<ParsedStep> executeParsedSteps(List<ParsedStep> parsedSteps) throws MalformedURLException {
		
		final DesiredCapabilities capability = DesiredCapabilities.chrome();
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--start-maximized");
		capability.setCapability(ChromeOptions.CAPABILITY, options);

		driver = new RemoteWebDriver(new URL("http://192.168.56.1:4444/wd/hub"), capability);
		driver.get("https://www.google.lk/?gws_rd=ssl");
		
		for (ParsedStep step : parsedSteps) {
			
			try {
				WebElement element = driver.findElement(By.xpath(step.getXpath()));
				if (step.getType().equalsIgnoreCase("click")) {
					System.out.println("Executing step : Click on " + step.getXpath());
					element.click();
				} else if (step.getType().equalsIgnoreCase("type")) {
					System.out.println("Executing step : Type "+step.getValue()+" on " + step.getXpath());
					element.sendKeys(step.getValue());
				} else {
					System.out.println("Executing step : press "+step.getKeyCode()+" on " + step.getXpath());
					element.sendKeys(getKeyFromkeyCode(step.getKeyCode()));
				}
			} catch (ElementNotVisibleException e) {
				System.out.println("Couldnt find the element!. Step failed, moving onto the next step.");
				continue;
			}
		}

		return parsedSteps;
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
