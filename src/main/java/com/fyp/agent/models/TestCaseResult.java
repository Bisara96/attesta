package com.fyp.agent.models;

import javax.persistence.*;

@Entity
@Table(name = "testcase_result")
public class TestCaseResult {

    @Id
    @Column(name = "tc_result_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "testcase_id", nullable = false)
    private TestCase testCase;
    private String result;
    private String screenshot;
    private String executionTime;
    private String executionInstance;
    private String status;

    @ManyToOne
    @JoinColumn(name = "agent_id", nullable = false)
    private Agent agent;

    public TestCaseResult() {
    }

    public TestCaseResult(TestCase testCase, String executionTime, String executionInstance) {
        this.testCase = testCase;
        this.executionTime = executionTime;
        this.executionInstance = executionInstance;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public TestCase getTestCase() {
        return testCase;
    }

    public void setTestCase(TestCase testCase) {
        this.testCase = testCase;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
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

    public String getExecutionInstance() {
        return executionInstance;
    }

    public void setExecutionInstance(String executionInstance) {
        this.executionInstance = executionInstance;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Agent getAgent() {
        return agent;
    }

    public void setAgent(Agent agent) {
        this.agent = agent;
    }
}
