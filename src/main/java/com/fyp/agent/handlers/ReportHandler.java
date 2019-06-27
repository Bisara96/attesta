package com.fyp.agent.handlers;

import com.fyp.agent.dbhandlers.ReportDBHandler;
import com.fyp.agent.models.TestCaseResult;
import com.fyp.agent.models.TestStepResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Component
@Service
public class ReportHandler {

    private ReportDBHandler reportDBH;

    public ReportHandler(ReportDBHandler reportDBH) {
        this.reportDBH = reportDBH;
    }

    public List<TestCaseResult> getTestCaseResults(int testcaseID) {
        return reportDBH.getTestCaseResults(testcaseID);
    }

    public List<TestStepResult> getTestStepResults(int tsResultID) {
        return reportDBH.getTestStepResults(tsResultID);
    }


}
