package com.fyp.agent.models;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("TYPE")
public class TypeStep extends TestStep {

	private String value;

	public TypeStep(){}

	public TypeStep(TestStepTypes stepType, String screenshot, UIObject uiObject, String value) {
		super(stepType, screenshot, uiObject);
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	

}
