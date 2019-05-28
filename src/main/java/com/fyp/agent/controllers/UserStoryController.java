package com.fyp.agent.controllers;

import java.util.List;

import com.fyp.agent.models.Project;
import com.fyp.agent.models.Sprint;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.*;

import com.fyp.agent.handlers.UserStoryHandler;
import com.fyp.agent.models.UserStory;

@RestController
@CrossOrigin(maxAge = 3600)
@RequestMapping("/userstory")
public class UserStoryController {

	UserStoryHandler uStoryHandler = null;
	UserStory uStory = null;

	public UserStoryController() {
		uStoryHandler = new UserStoryHandler();
	}

	@GetMapping("/getall")
	private List<UserStory> getUserStories() {
		return uStoryHandler.readAllUserStories();
	}

	@GetMapping("/sprint_stories")
	private List<UserStory> getSprintStories(@RequestParam(name = "id", required = true, defaultValue = "1") int id) {
		return uStoryHandler.getSprintStories(id);
	}

	@GetMapping("/get")
	private UserStory getUserStory(@RequestParam(name = "id", required = true, defaultValue = "1") int id) {
		return uStoryHandler.readUserStory(id);
	}

	@GetMapping("/projects")
	private List<Project> getProjects() {
		return uStoryHandler.readAllProjects();
	}

	@GetMapping("/sprints")
	private List<Sprint> getSprints() {
		return uStoryHandler.readAllSprints();
	}

	@GetMapping("/project_sprints")
	private List<Sprint> getProjectSprints(@RequestParam(name = "id", required = true, defaultValue = "1") int id) {
		return uStoryHandler.getProjectSprints(id);
	}

	@PostMapping("/add_story")
	private UserStory addUserStory(@RequestBody String jsonResponse) {
		try {
			return uStoryHandler.addUserStory(new JSONObject(jsonResponse));
		} catch (JSONException e) {
			return null;
		}
	}

	@GetMapping("/add_sprint")
	private Sprint addSprint(@RequestParam(name = "id", required = true, defaultValue = "1") int id) {
		return uStoryHandler.addSprint(id);
	}

}


