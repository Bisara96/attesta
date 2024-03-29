package com.fyp.agent.dbhandlers;

import com.fyp.agent.models.*;
import com.fyp.agent.sessionfactory.*;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Component
@Repository
public class TestCaseDBHandler {

    public List<AcceptanceCriteria> getStoryACriteria(int userStoryID) {
        AcceptanceCriteriaFactory acFactory = new AcceptanceCriteriaFactory();
        List<AcceptanceCriteria> acList = acFactory.getStoryAcceptanceCriteria(userStoryID);
        acFactory.exit();
        return acList;
    }

    public List<TestStep> getStorySteps(int userStoryID) {
        UStoryStepFactory usStepFactory = new UStoryStepFactory();
        List<UserStorySteps> stepList = usStepFactory.getStorySteps(userStoryID);
        usStepFactory.exit();
        List<TestStep> testStepList = new ArrayList<TestStep>();
        for(UserStorySteps uStep: stepList){
            testStepList.add(uStep.getTestStep());
        }
        return testStepList;
    }

    public void removeOldTestCases(int storyID){
        TestCaseFactory testCaseFactory = new TestCaseFactory();
        testCaseFactory.removeOldTestCases(storyID);
        testCaseFactory.exit();
    }

    public int addTestCase(TestCase testCase){
        TestCaseFactory testCaseFactory = new TestCaseFactory();
        int id = testCaseFactory.create(testCase);
        testCaseFactory.exit();
        return id;
    }

    public void addTestCaseStep(TestCaseSteps testCaseSteps){
        TestCaseStepsFactory tcStepsFactory = new TestCaseStepsFactory();
        int id = tcStepsFactory.create(testCaseSteps);
        tcStepsFactory.exit();
    }

    public List<TestCase> getStoryTestCases(int id) {
        TestCaseFactory testCaseFactory = new TestCaseFactory();
        List<TestCase> tcList = testCaseFactory.getStoryTestCases(id);
        testCaseFactory.exit();
        return tcList;
    }

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

    public void updateTestCase(TestCase testCase){
        TestCaseFactory testCaseFactory = new TestCaseFactory();
        testCaseFactory.update(testCase);
        testCaseFactory.exit();
    }

    public List<TestCaseResult> getTestCaseLastResult(int id) {
        TestCaseResultFactory tcResultFactory = new TestCaseResultFactory();
        List<TestCaseResult> result = tcResultFactory.getLastResult(id);
        tcResultFactory.exit();
        return result;
    }

    public int addTestStep(TestStep tStep) {
        TestStepFactory tStepFactory = new TestStepFactory();
        int id = tStepFactory.create(tStep);
        tStepFactory.exit();
        return id;
    }
}
