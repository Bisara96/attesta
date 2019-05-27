package com.fyp.agent.dbhandlers;

import com.fyp.agent.models.TestCase;
import com.fyp.agent.models.TestCaseResult;
import com.fyp.agent.models.TestCaseSteps;
import com.fyp.agent.models.TestStepResult;
import com.fyp.agent.sessionfactory.TestCaseFactory;
import com.fyp.agent.sessionfactory.TestCaseResultFactory;
import com.fyp.agent.sessionfactory.TestCaseStepsFactory;
import com.fyp.agent.sessionfactory.TestStepResultFactory;

import java.util.List;

public class ExecutionDBHandler {

    public TestCase getTestCase(int id){
        TestCaseFactory testCaseFactory = new TestCaseFactory();
        TestCase tc = testCaseFactory.read(id);
        testCaseFactory.exit();
        return tc;
    }

    public List<TestCaseSteps> getTestCaseSteps(int id){
        TestCaseStepsFactory testCaseFactory = new TestCaseStepsFactory();
        List<TestCaseSteps> tcs = testCaseFactory.getTestCaseSteps(id);
        testCaseFactory.exit();
        return tcs;
    }

    public List<TestCase> getStoryTestCases(int id) {
        TestCaseFactory testCaseFactory = new TestCaseFactory();
        List<TestCase> tcList = testCaseFactory.getStoryTestCases(id);
        testCaseFactory.exit();
        return tcList;
    }

    public void updateTestCase(TestCase testCase){
        TestCaseFactory testCaseFactory = new TestCaseFactory();
        testCaseFactory.update(testCase);
        testCaseFactory.exit();
    }

    public TestCaseResult createTestCaseResult(TestCaseResult result) {
        TestCaseResultFactory tcResultFactory = new TestCaseResultFactory();
        TestCaseResult tcResult = tcResultFactory.create(result);
        tcResultFactory.exit();
        return tcResult;
    }

    public TestStepResult createTestStepResult(TestStepResult result) {
        TestStepResultFactory tsResultFactory = new TestStepResultFactory();
        TestStepResult tsResult = tsResultFactory.create(result);
        tsResultFactory.exit();
        return tsResult;
    }

    public void updateTestCaseResult(TestCaseResult result) {
        TestCaseResultFactory tcResultFactory = new TestCaseResultFactory();
        tcResultFactory.update(result);
        tcResultFactory.exit();
    }

    public List<TestCaseResult> getTestCaseResults(int testCaseID) {
        TestCaseResultFactory tcResultFactory = new TestCaseResultFactory();
        List<TestCaseResult> resultList = tcResultFactory.getTestCaseResults(testCaseID);
        tcResultFactory.exit();
        return resultList;
    }

}
