package com.fyp.agent.dbhandlers;

import com.fyp.agent.models.TestCase;
import com.fyp.agent.models.TestCaseSteps;
import com.fyp.agent.sessionfactory.TestCaseFactory;
import com.fyp.agent.sessionfactory.TestCaseStepsFactory;
import org.json.Test;

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

}
