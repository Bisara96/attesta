package com.fyp.agent.models;

import java.util.List;

public class TestCase {

	private int testcase_id;
	private String testcaseName;
	private UserStory uStory;
	private AcceptanceCriteria acceptanceCriteria;
	private List<TestStep> testSteps;
	
	public TestCase(int testcase_id, String testcaseName, UserStory uStory, AcceptanceCriteria acceptanceCriteria,
			List<TestStep> testSteps) {
		super();
		this.testcase_id = testcase_id;
		this.testcaseName = testcaseName;
		this.uStory = uStory;
		this.acceptanceCriteria = acceptanceCriteria;
		this.testSteps = testSteps;
	}

	public int getTestcase_id() {
		return testcase_id;
	}

	public void setTestcase_id(int testcase_id) {
		this.testcase_id = testcase_id;
	}

	public String getTestcaseName() {
		return testcaseName;
	}

	public void setTestcaseName(String testcaseName) {
		this.testcaseName = testcaseName;
	}

	public UserStory getuStory() {
		return uStory;
	}

	public void setuStory(UserStory uStory) {
		this.uStory = uStory;
	}

	public AcceptanceCriteria getAcceptanceCriteria() {
		return acceptanceCriteria;
	}

	public void setAcceptanceCriteria(AcceptanceCriteria acceptanceCriteria) {
		this.acceptanceCriteria = acceptanceCriteria;
	}

	public List<TestStep> getTestSteps() {
		return testSteps;
	}

	public void setTestSteps(List<TestStep> testSteps) {
		this.testSteps = testSteps;
	}
	
	
	
	

}
