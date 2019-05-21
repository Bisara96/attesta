package com.fyp.agent.handlers;

import java.util.List;

import com.fyp.agent.dbhandlers.UserStoryDBHandler;
import com.fyp.agent.models.UserStory;

public class UserStoryHandler {
	
	UserStoryDBHandler uDBHandler = null;
	
	public UserStoryHandler() {
		uDBHandler = new UserStoryDBHandler();
	}
	
	public List<UserStory> readAllUserStories() {
		return uDBHandler.getAll();
	}
	
	public UserStory readUserStory(int id) {
		return uDBHandler.getUserStory(id);
	}

}
