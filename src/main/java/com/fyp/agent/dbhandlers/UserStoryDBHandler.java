package com.fyp.agent.dbhandlers;

import java.util.List;

import com.fyp.agent.models.UserStory;
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

}
