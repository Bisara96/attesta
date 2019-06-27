package com.fyp.agent.dbhandlers;

import com.fyp.agent.models.TestCaseResult;
import com.fyp.agent.models.TestStepResult;
import com.fyp.agent.sessionfactory.TestCaseResultFactory;
import com.fyp.agent.sessionfactory.TestStepResultFactory;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

@Component
@Repository
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
