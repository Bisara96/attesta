package com.fyp.agent.controllers;

import java.net.MalformedURLException;
import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fyp.agent.handlers.RecordHandler;
import com.fyp.agent.models.ParsedStep;
import com.fyp.agent.models.Step;

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
	private String generateStepsJson(@RequestBody Recording record) {
	    try {
			return handler.parseSteps(record.getSteps(),record.getId());
		} catch (MalformedURLException e) {
			return null;
		}
	}
	
	@GetMapping("/play")
	private String play(@RequestParam(name="id", required=true, defaultValue="1") int id) {
	    try {
			return handler.executeParsedSteps(id);
		} catch (MalformedURLException e) {
			return e.toString();
		}
	}

}

class Recording {
	
    private int id;
    private List <Step> steps;
    
    public Recording() {
	}
    
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public List <Step> getSteps() {
		return steps;
	}
	public void setSteps(List <Step> steps) {
		this.steps = steps;
	}
       
}
