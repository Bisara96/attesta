package com.fyp.agent.dbhandlers;

import com.fyp.agent.models.TestCaseResult;
import com.fyp.agent.models.TestStepResult;
import com.fyp.agent.sessionfactory.TestCaseResultFactory;
import com.fyp.agent.sessionfactory.TestStepResultFactory;

import java.util.List;

public class ReportDBHandler {

    public List<TestCaseResult> getTestCaseResults(int testCaseID) {
        TestCaseResultFactory tcResultFactory = new TestCaseResultFactory();
        List<TestCaseResult> resultList = tcResultFactory.getTestCaseResults(testCaseID);
        tcResultFactory.exit();
        return resultList;
    }

    public List<TestStepResult> getTestStepResults(int tcResultID) {
        TestStepResultFactory tsResultFactory = new TestStepResultFactory();
        List<TestStepResult> resultList = tsResultFactory.getTestStepResults(tcResultID);
        tsResultFactory.exit();
        return resultList;
    }
}
