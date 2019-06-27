package com.fyp.agent.controllers;

import com.fyp.agent.handlers.UserStoryHandler;
import com.fyp.agent.models.Project;
import com.fyp.agent.models.Sprint;
import com.fyp.agent.models.UserStory;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(maxAge = 3600)
@RequestMapping("/userstory")
public class UserStoryController {

	@Autowired
	private UserStoryHandler handler;

	@GetMapping("/getall")
	private List<UserStory> getUserStories() {
		return handler.readAllUserStories();
	}

	@GetMapping("/sprint_stories")
	private List<UserStory> getSprintStories(@RequestParam(name = "id", required = true, defaultValue = "1") int id) {
		return handler.getSprintStories(id);
	}

	@GetMapping("/get")
	private UserStory getUserStory(@RequestParam(name = "id", required = true, defaultValue = "1") int id) {
		return handler.readUserStory(id);
	}

	@GetMapping("/projects")
	private List<Project> getProjects() {
		return handler.readAllProjects();
	}

	@GetMapping("/sprints")
	private List<Sprint> getSprints() {
		return handler.readAllSprints();
	}

	@GetMapping("/project_sprints")
	private List<Sprint> getProjectSprints(@RequestParam(name = "id", required = true, defaultValue = "1") int id) {
		return handler.getProjectSprints(id);
	}

	@PostMapping("/add_story")
	private UserStory addUserStory(@RequestBody String jsonResponse) {
		try {
			return handler.addUserStory(new JSONObject(jsonResponse));
		} catch (JSONException e) {
			return null;
		}
	}

	@GetMapping("/add_sprint")
	private Sprint addSprint(@RequestParam(name = "id", required = true, defaultValue = "1") int id) {
		return handler.addSprint(id);
	}

}


