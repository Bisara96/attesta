package com.fyp.agent.handlers;

import java.awt.event.KeyEvent;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
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

	final DesiredCapabilities capability = DesiredCapabilities.chrome();
	WebDriver driver = null;

	public RecordHandler() {
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--start-maximized");
		capability.setCapability(ChromeOptions.CAPABILITY, options);
	}

	public String startRecording(String url) throws MalformedURLException {

		driver = new RemoteWebDriver(new URL("http://192.168.56.1:4444/wd/hub"), capability);
		driver.get(url);

		WebElement searchBar = driver
				.findElement(By.xpath("//*[@id=\"tsf\"]/DIV[2]/DIV[1]/DIV[1]/DIV[1]/DIV[1]/INPUT[1]"));
		searchBar.sendKeys("selenium");
		searchBar.sendKeys(Keys.ENTER);

		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript(
				"var script = document.createElement('script');script.type = 'text/javascript';script.src = 'https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js';document.head.appendChild(script);");
		js.executeScript("$(\"body\").prepend(\"<b>Prepended text</b>. \");");
		js.executeScript(
				"function getPathTo(e){if(\"\"!==e.id)return'[@id=\"'+e.id+'\"]';if(e===document.body)return e.tagName;for(var t=0,n=e.parentNode.childNodes,o=0;o<n.length;o++){var a=n[o];if(a===e)return getPathTo(e.parentNode)+\"/\"+e.tagName+\"[\"+(t+1)+\"]\";1===a.nodeType&&a.tagName===e.tagName&&t++}}document.addEventListener?(document.addEventListener(\"click\",function(e){var t=e.target||e.srcElement;console.log(\"click on \"+getPathTo(t))}),document.addEventListener(\"keyup\",function(e){var t=e.target||e.srcElement;console.log(\"type \"+e.keyCode+\" on \"+getPathTo(t))})):document.attachEvent&&(document.attachEvent(\"onclick\",function(){var e=event.target||event.srcElement;console.log(\"click on \"+getPathTo(e))}),document.attachEvent(\"onkeyup\",function(){var e=event.target||event.srcElement;console.log(\"type \"+event.keyCode+\" on \"+getPathTo(e))}));");
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
			} else {
				if ((step.getKeycode() >= 48 && step.getKeycode() <= 57)
						|| (step.getKeycode() >= 65 && step.getKeycode() <= 90)) {
					if (parsedSteps.size() > 0
							&& parsedSteps.get(parsedSteps.size() - 1).getType().equalsIgnoreCase(step.getType())
							&& parsedSteps.get(parsedSteps.size() - 1).getXpath().equalsIgnoreCase(step.getXpath())) {
						String newVal = parsedSteps.get(parsedSteps.size() - 1).getValue() + (char) step.getKeycode();
						parsedSteps.get(parsedSteps.size() - 1).setValue(newVal);
						System.out.println("New value "+parsedSteps.get(parsedSteps.size() - 1).getValue());
					} else {
						ParsedStep pStep = new ParsedStep();
						pStep.setType(step.getType());
						pStep.setXpath(step.getXpath());
						pStep.setValue("" + (char) step.getKeycode());
						parsedSteps.add(pStep);
					}
				} else {
					ParsedStep pStep = new ParsedStep();
					pStep.setType("press");
					pStep.setXpath(step.getXpath());
					pStep.setKeyCode(step.getKeycode());
					parsedSteps.add(pStep);
				}
			}
		}

		return executeParsedSteps(parsedSteps);

	}

	public List<ParsedStep> executeParsedSteps(List<ParsedStep> parsedSteps) throws MalformedURLException {

		driver = new RemoteWebDriver(new URL("http://192.168.56.1:4444/wd/hub"), capability);
		driver.get("https://www.google.lk/?gws_rd=ssl");

		/*
		 * WebElement searchBar = driver .findElement(By.
		 * xpath("/html[1]/body[1]/nav[contains(concat(\" \", @class, \" \"),\" navbar navbar-expand-lg navbar-light fixed-top \")]/div[@id=\"navbarSupportedContent\"]/form[1]/div[contains(concat(\" \", @class, \" \"),\" form-inline my-2 my-lg-0 search-form \")]/input[contains(concat(\" \", @class, \" \"),\" form-control mr-sm-2 search-bar \")]"
		 * )); searchBar.sendKeys("hi");
		 */

		for (ParsedStep step : parsedSteps) {
			
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
