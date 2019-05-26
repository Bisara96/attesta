package com.fyp.agent.dbhandlers;

import java.util.List;

import com.fyp.agent.models.AcceptanceCriteria;
import com.fyp.agent.models.Project;
import com.fyp.agent.models.Sprint;
import com.fyp.agent.models.UserStory;
import com.fyp.agent.sessionfactory.AcceptanceCriteriaFactory;
import com.fyp.agent.sessionfactory.ProjectFactory;
import com.fyp.agent.sessionfactory.SprintFactory;
import com.fyp.agent.sessionfactory.UserStoryFactory;

public class UserStoryDBHandler {
	
	public List<UserStory> getAll() {
		UserStoryFactory USFactory = new UserStoryFactory();
	    List<UserStory> usList = USFactory.read();
	    USFactory.exit();
	    return usList;
	}
	
	public void updateUserStory(UserStory userStory) {
		UserStoryFactory USFactory = new UserStoryFactory();
	    USFactory.update(userStory);
	    USFactory.exit();
	}
	
	public UserStory getUserStory(int id) {
		UserStoryFactory USFactory = new UserStoryFactory();
		UserStory story = USFactory.read(id);
	    USFactory.exit();
	    return story;
	}

	public List<Project> getAllProjects() {
		ProjectFactory projectFactory = new ProjectFactory();
		List<Project> projList = projectFactory.read();
		projectFactory.exit();
		return projList;
	}

	public List<Sprint> getAllSprints() {
		SprintFactory sprintFactory = new SprintFactory();
		List<Sprint> sprintList = sprintFactory.read();
		sprintFactory.exit();
		return sprintList;
	}

	public List<Sprint> getProjectSprints(int id) {
		SprintFactory sprintFactory = new SprintFactory();
		List<Sprint> sprintList = sprintFactory.getProjectSprints(id);
		sprintFactory.exit();
		return sprintList;
	}

	public List<UserStory> getSprintStories(int id) {
		UserStoryFactory storyFactory = new UserStoryFactory();
		List<UserStory> storyList = storyFactory.getSprintStories(id);
		storyFactory.exit();
		return storyList;
	}

	public UserStory addStory(UserStory story) {
		UserStoryFactory userStoryFactory = new UserStoryFactory();
		UserStory res = userStoryFactory.create(story);
		userStoryFactory.exit();
		return  res;
	}

	public Sprint getSprint(int id){
		SprintFactory sprintFactory = new SprintFactory();
		Sprint sprint = sprintFactory.read(id);
		sprintFactory.exit();
		return sprint;
	}

	public void addACriteria(AcceptanceCriteria criteria){
		AcceptanceCriteriaFactory acFactory = new AcceptanceCriteriaFactory();
		acFactory.create(criteria);
		acFactory.exit();
	}

}
