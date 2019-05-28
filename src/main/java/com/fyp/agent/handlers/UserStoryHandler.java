package com.fyp.agent.handlers;

import java.util.List;

import com.fyp.agent.dbhandlers.UserStoryDBHandler;
import com.fyp.agent.models.AcceptanceCriteria;
import com.fyp.agent.models.Project;
import com.fyp.agent.models.Sprint;
import com.fyp.agent.models.UserStory;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class UserStoryHandler {
	
	UserStoryDBHandler uDBHandler = null;
	
	public UserStoryHandler() {
		uDBHandler = new UserStoryDBHandler();
	}
	
	public List<UserStory> readAllUserStories() {
		return uDBHandler.getAll();
	}

	public List<UserStory> getSprintStories(int id) {
		return uDBHandler.getSprintStories(id);
	}
	
	public UserStory readUserStory(int id) {
		return uDBHandler.getUserStory(id);
	}

	public List<Project> readAllProjects() {
		return uDBHandler.getAllProjects();
	}

	public Sprint addSprint(int id) {
		Project proj = uDBHandler.getProject(id);
		List<Sprint> ls = uDBHandler.getProjectSprints(id);
		int no = ls.size() == 0 ? 1 : ls.size();
		Sprint sprint = new Sprint("Sprint "+no,proj);
		return uDBHandler.newSprint(sprint);
	}

	public List<Sprint> readAllSprints() {
		return uDBHandler.getAllSprints();
	}

	public List<Sprint> getProjectSprints(int id) {
		return uDBHandler.getProjectSprints(id);
	}

	public UserStory addUserStory(JSONObject res) throws JSONException {
		Sprint sprint = uDBHandler.getSprint(Integer.parseInt(res.getString("sprint")));
		UserStory story = new UserStory(res.getString("description"), res.getString("status"), "", sprint);
		story = uDBHandler.addStory(story);
		JSONArray array = res.getJSONArray("acList");
		for(int i = 0;i < array.length();i++){
			uDBHandler.addACriteria(new AcceptanceCriteria(story, array.getString(i)));
		}
		return story;
	}

}
