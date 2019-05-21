package com.fyp.agent.models;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("CLICK")
public class ClickStep extends TestStep {

	private int x;
	private int y;
	
	public ClickStep(TestStepTypes stepType, String screenshot, UIObject uiObject, int x, int y) {
		super(stepType, screenshot, uiObject);
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
	
	

}
