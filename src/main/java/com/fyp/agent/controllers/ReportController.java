package com.fyp.agent.controllers;

import com.fyp.agent.handlers.ReportHandler;
import com.fyp.agent.models.TestCaseResult;
import com.fyp.agent.models.TestStepResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(maxAge = 3600)
@RequestMapping("/report")
public class ReportController {

    @Autowired
    private ReportHandler handler;

    @GetMapping("/testcase_results")
    private List<TestCaseResult> getTestCaseResults(@RequestParam(name = "id", required = true, defaultValue = "1") int id) {
        return handler.getTestCaseResults(id);
    }

    @GetMapping("/teststep_results")
    private List<TestStepResult> getTestStepResults(@RequestParam(name = "id", required = true, defaultValue = "1") int id) {
        return handler.getTestStepResults(id);
    }

}
