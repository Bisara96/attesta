package com.fyp.agent.handlers;

import com.fyp.agent.dbhandlers.ReportDBHandler;
import com.fyp.agent.models.TestCaseResult;
import com.fyp.agent.models.TestStepResult;

import java.util.List;

public class ReportHandler {

    ReportDBHandler reportDBHandler;

    public ReportHandler() {
        reportDBHandler = new ReportDBHandler();
    }

    public List<TestCaseResult> getTestCaseResults(int testcaseID) {
        return reportDBHandler.getTestCaseResults(testcaseID);
    }

    public List<TestStepResult> getTestStepResults(int tsResultID) {
        return reportDBHandler.getTestStepResults(tsResultID);
    }


}
