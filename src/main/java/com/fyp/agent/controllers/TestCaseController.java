package com.fyp.agent.controllers;

import com.fyp.agent.handlers.TestCaseHandler;
import com.fyp.agent.models.TestCase;
import com.fyp.agent.models.TestCaseResult;
import com.fyp.agent.models.TestCaseSteps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(maxAge = 3600)
@RequestMapping("/testcase")
public class TestCaseController {

    @Autowired
    private TestCaseHandler handler;

    @GetMapping("/generate")
    private String generate(@RequestParam(name="id", required=true, defaultValue="1") int id) {
        return handler.generateTestCases(id);
    }

    @GetMapping("/get_story")
    private List<TestCase> getTestCases(@RequestParam(name="id", required=true, defaultValue="1") int id) {
        return handler.getStoryTestCases(id);
    }

    @GetMapping("/get")
    private TestCase getTestCaseDetails(@RequestParam(name="id", required=true, defaultValue="1") int id) {
        return handler.getTestCase(id);
    }

    @GetMapping("/get_tc_steps")
    private List<TestCaseSteps> getTestCaseSteps(@RequestParam(name="id", required=true, defaultValue="1") int id) {
        return handler.getTestCaseSteps(id);
    }

    @GetMapping("/get_testcase_lastresult")
    private List<TestCaseResult> getTestCaseLastResult(@RequestParam(name="id", required=true, defaultValue="1") int id) {
        return handler.getTCLastResult(id);
    }

    @GetMapping("/get_story_tclast")
    private List<TestCaseResult> getStoryTestCases_Result(@RequestParam(name="id", required=true, defaultValue="1") int id) {
        return handler.getStoryTestCases_Result(id);
    }



}
