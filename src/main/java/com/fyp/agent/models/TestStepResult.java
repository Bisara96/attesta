package com.fyp.agent.models;

import javax.persistence.*;

@Entity
@Table(name = "teststep_result")
public class TestStepResult {

    @Id
    @Column(name = "teststep_result_id")
    private int id;

    @ManyToOne
    @JoinColumn(name = "teststep_id", nullable = false)
    private TestStep testStep;
    private String screenshot;
    private String executionTime;

    @ManyToOne
    @JoinColumn(name = "testcase_result_id", nullable = false)
    private TestCaseResult testCaseResult;

    public TestStepResult() {
    }

    public TestStepResult(int id, TestStep testStep, String screenshot, String executionTime, TestCaseResult testCaseResult) {
        this.id = id;
        this.testStep = testStep;
        this.screenshot = screenshot;
        this.executionTime = executionTime;
        this.testCaseResult = testCaseResult;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public TestStep getTestStep() {
        return testStep;
    }

    public void setTestStep(TestStep testStep) {
        this.testStep = testStep;
    }

    public String getScreenshot() {
        return screenshot;
    }

    public void setScreenshot(String screenshot) {
        this.screenshot = screenshot;
    }

    public String getExecutionTime() {
        return executionTime;
    }

    public void setExecutionTime(String executionTime) {
        this.executionTime = executionTime;
    }

    public TestCaseResult getTestCaseResult() {
        return testCaseResult;
    }

    public void setTestCaseResult(TestCaseResult testCaseResult) {
        this.testCaseResult = testCaseResult;
    }
}
