package com.fyp.agent.controllers;

import java.net.MalformedURLException;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(maxAge = 3600)
@RequestMapping("/userstory")
public class UserStoryController {

	@GetMapping("/userstories")
	private String start() {
		return "hi";
	}
}
