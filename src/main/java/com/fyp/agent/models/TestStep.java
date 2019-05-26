package com.fyp.agent.models;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity(name="teststep")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="ref", discriminatorType = DiscriminatorType.STRING)
public class TestStep {
	
	@Id
	@Column(name = "teststep_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String stepType;
	private String screenshot;
	@ManyToOne
    @JoinColumn(name = "uiobject_id", nullable = false)
	private UIObject uiObject;

    public TestStep() {
    }

    public TestStep(TestStepTypes stepType, String screenshot, UIObject uiObject) {
		super();
		this.stepType = stepType.name();
		this.screenshot = screenshot;
		this.uiObject = uiObject;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public TestStepTypes getStepType() {
		return TestStepTypes.valueOf(stepType);
	}

	public void setStepType(TestStepTypes stepType) {
		this.stepType = stepType.name();
	}

	public String getScreenshot() {
		return screenshot;
	}

	public void setScreenshot(String screenshot) {
		this.screenshot = screenshot;
	}

	public UIObject getUiObject() {
		return uiObject;
	}

	public void setUiObject(UIObject uiObject) {
		this.uiObject = uiObject;
	}
	
	

}
