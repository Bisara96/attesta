package com.fyp.agent.models;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("SELECT")
public class SelectStep extends TestStep {
	
	private String selectedOption;

	public SelectStep(TestStepTypes stepType, String screenshot, UIObject uiObject, String selectedOption) {
		super(stepType, screenshot, uiObject);
		this.selectedOption = selectedOption;
	}

	public String getSelectedOption() {
		return selectedOption;
	}

	public void setSelectedOption(String selectedOption) {
		this.selectedOption = selectedOption;
	}
	
	

}
