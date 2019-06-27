package com.fyp.agent.handlers;

import com.fyp.agent.dbhandlers.UserStoryDBHandler;
import com.fyp.agent.models.AcceptanceCriteria;
import com.fyp.agent.models.Project;
import com.fyp.agent.models.Sprint;
import com.fyp.agent.models.UserStory;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Component
@Service
public class UserStoryHandler {

	private UserStoryDBHandler ustoryDBH;

    public UserStoryHandler(UserStoryDBHandler ustoryDBH) {
        this.ustoryDBH = ustoryDBH;
    }

    public List<UserStory> readAllUserStories() {
		return ustoryDBH.getAll();
	}

	public List<UserStory> getSprintStories(int id) {
		return ustoryDBH.getSprintStories(id);
	}
	
	public UserStory readUserStory(int id) {
		return ustoryDBH.getUserStory(id);
	}

	public List<Project> readAllProjects() {
		return ustoryDBH.getAllProjects();
	}

	public Sprint addSprint(int id) {
		Project proj = ustoryDBH.getProject(id);
		List<Sprint> ls = ustoryDBH.getProjectSprints(id);
		int no = ls.size() == 0 ? 1 : ls.size();
		Sprint sprint = new Sprint("Sprint "+no,proj);
		return ustoryDBH.newSprint(sprint);
	}

	public List<Sprint> readAllSprints() {
		return ustoryDBH.getAllSprints();
	}

	public List<Sprint> getProjectSprints(int id) {
		return ustoryDBH.getProjectSprints(id);
	}

	public UserStory addUserStory(JSONObject res) throws JSONException {
		Sprint sprint = ustoryDBH.getSprint(Integer.parseInt(res.getString("sprint")));
		UserStory story = new UserStory(res.getString("description"), res.getString("status"), "", sprint);
		story = ustoryDBH.addStory(story);
		JSONArray array = res.getJSONArray("acList");
		for(int i = 0;i < array.length();i++){
            ustoryDBH.addACriteria(new AcceptanceCriteria(story, array.getString(i)));
		}
		return story;
	}

}
