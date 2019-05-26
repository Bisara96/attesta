package com.fyp.agent.models;

import javax.persistence.*;

@Entity
@Table(name = "testcasesteps")
public class TestCaseSteps {

    @Id
    @Column(name = "testcasesteps_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "testcase_id", nullable = false)
    private TestCase testCase;

    @OneToOne
    @JoinColumn(name = "teststep_id", nullable = false)
    private TestStep testStep;
    private String expectedResult;

    public TestCaseSteps() {
    }

    public TestCaseSteps(TestCase testCase, TestStep testStep, String expectedResult) {
        this.testCase = testCase;
        this.testStep = testStep;
        this.expectedResult = expectedResult;
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

    public TestStep getTestStep() {
        return testStep;
    }

    public void setTestStep(TestStep testStep) {
        this.testStep = testStep;
    }

    public String getExpectedResult() {
        return expectedResult;
    }

    public void setExpectedResult(String expectedResult) {
        this.expectedResult = expectedResult;
    }
}
