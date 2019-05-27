package com.fyp.agent.controllers;

import com.fyp.agent.handlers.ExecutionHandler;
import org.springframework.web.bind.annotation.*;

import java.net.MalformedURLException;

@RestController
@CrossOrigin(maxAge = 3600)
@RequestMapping("/execute")
public class ExecutionController {

    ExecutionHandler handler;

    public ExecutionController() {
        handler = new ExecutionHandler();
    }

    @GetMapping("/execute_tc")
    private String excuteTestCase(@RequestParam(name="id", required=true, defaultValue="1") int id) {
        try {
            return handler.getTestCaseToExecute(id);
        } catch (MalformedURLException e) {
            return e.getMessage();
        }
    }

    @GetMapping("/execute_story")
    private String executeStory(@RequestParam(name="id", required=true, defaultValue="1") int story_id) {
        try {
            return handler.executeStory(story_id);
        } catch (MalformedURLException e) {
            return e.getMessage();
        }
    }

}
