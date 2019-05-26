package com.fyp.agent.models;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("PRESS")
public class PressKeyStep extends TestStep {
	
	private int keyCode;

	public PressKeyStep() {
	}

	public PressKeyStep(TestStepTypes stepType, String screenshot, UIObject uiObject, int keyCode) {
		super(stepType, screenshot, uiObject);
		this.keyCode = keyCode;
	}

	public int getKeyCode() {
		return keyCode;
	}

	public void setKeyCode(int keyCode) {
		this.keyCode = keyCode;
	}
	
	

}
