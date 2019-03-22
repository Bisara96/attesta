package com.fyp.agent.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fyp.agent.handlers.UserStoryHandler;
import com.fyp.agent.models.UserStory;

@RestController
@CrossOrigin(maxAge = 3600)
@RequestMapping("/userstory")
public class UserStoryController {
	
	UserStoryHandler uStoryHandler = null;
	
	public UserStoryController() {
		uStoryHandler = new UserStoryHandler();
	}

	@GetMapping("/getall")
	private List<UserStory> getUserStories() {
		return uStoryHandler.readAllUserStories();
	}
}