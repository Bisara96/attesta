package com.fyp.agent.controllers;

import com.fyp.agent.handlers.RecordHandler;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.net.MalformedURLException;

@RestController
@CrossOrigin(maxAge = 3600)
@RequestMapping("/record")
public class RecordController {

	@Autowired
	private RecordHandler handler;
	
	@GetMapping("/start")
	private String start(@RequestParam(name="url", required=true, defaultValue="https://mail.google.com") String url, @RequestParam(name="id", required=true, defaultValue="1") int id, @RequestParam(name="agent", required=true, defaultValue="default") String agent) {
		try {
			return handler.startRecording(url, id, agent);
		} catch (MalformedURLException e) {
			return e.getMessage();
		}
	}
	
	@PostMapping("/stop")
	private String generateStepsJson(@RequestBody String jsonResponse) {
	    try {
			return handler.parseSteps(new JSONObject(jsonResponse));
		} catch (JSONException e) {
			return e.getMessage();
		}
	}

    @GetMapping("/status")
    private Boolean getStatus(@RequestParam(name="id", required=true, defaultValue="1") int id) {
        return handler.checkStatus(id);
    }
}
