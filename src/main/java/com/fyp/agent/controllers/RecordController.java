package com.fyp.agent.controllers;

import java.net.MalformedURLException;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fyp.agent.handlers.RecordHandler;

@RestController
@CrossOrigin(maxAge = 3600)
@RequestMapping("/record")
public class RecordController {
	
	RecordHandler handler = null;
	
	public RecordController() {
		handler = new RecordHandler();
	}
	
	@GetMapping("/start")
	private String start(@RequestParam(name="url", required=true, defaultValue="https://mail.google.com") String url, @RequestParam(name="id", required=true, defaultValue="1") int id) {
		try {
			return handler.startRecording(url,id);
		} catch (MalformedURLException e) {
			return e.getMessage();
		}
	}
	
	@PostMapping("/stop")
	private String generateStepsJson(@RequestBody String jsonResponse) {
	    try {
			return handler.parseSteps(new JSONObject(jsonResponse));
		} catch (MalformedURLException e) {
			return e.getMessage();
		} catch (JSONException e) {
			return e.getMessage();
		}
	}
	
//	@GetMapping("/play")
//	private String play(@RequestParam(name="id", required=true, defaultValue="1") int id) {
//	    try {
//			return handler.executeParsedSteps(id);
//		} catch (MalformedURLException e) {
//			return e.toString();
//		}
//	}

}

//class RecordedRawStep {
//	String type;
//	String value;
//	UIObject UIObject;
//	String time;
//	String screenshot;
//}
//
//class Recording {
//    int id;
//    List <RecordedRawStep> steps;
//}
