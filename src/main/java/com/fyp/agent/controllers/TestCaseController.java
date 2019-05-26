package com.fyp.agent.controllers;

import com.fyp.agent.handlers.TestCaseHandler;
import com.fyp.agent.models.TestCase;
import com.fyp.agent.models.TestCaseSteps;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(maxAge = 3600)
@RequestMapping("/testcase")
public class TestCaseController {

    private TestCaseHandler handler;

    public TestCaseController() {
        handler = new TestCaseHandler();
    }

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

}
