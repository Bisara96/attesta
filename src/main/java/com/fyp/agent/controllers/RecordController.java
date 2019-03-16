package com.fyp.agent.controllers;

import java.net.MalformedURLException;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fyp.agent.handlers.RecordHandler;
import com.fyp.agent.models.ParsedStep;
import com.fyp.agent.models.Step;

@RestController
@RequestMapping("/record")
public class RecordController {
	
	RecordHandler handler = null;
	
	public RecordController() {
		handler = new RecordHandler();
	}
	
	@GetMapping("/start")
	private String start(@RequestParam(name="url", required=true, defaultValue="https://mail.google.com") String url) {
		try {
			return handler.startRecording(url);
		} catch (MalformedURLException e) {
			return e.getMessage();
		}
	}
	
	@PostMapping("/stop")
	private List<ParsedStep> invoices(@RequestBody List <Step> steps) {
	    try {
			return handler.parseSteps(steps);
		} catch (MalformedURLException e) {
			return null;
		}
	}

}
