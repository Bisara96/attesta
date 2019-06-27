package com.fyp.agent.dbhandlers;

import com.fyp.agent.models.TestStep;
import com.fyp.agent.models.UIObject;
import com.fyp.agent.models.UserStorySteps;
import com.fyp.agent.sessionfactory.TestStepFactory;
import com.fyp.agent.sessionfactory.UIObjectFactory;
import com.fyp.agent.sessionfactory.UStoryStepFactory;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

@Component
@Repository
public class RecordDBHandler {
	
	public int addUIObject(UIObject uObj) {
		UIObjectFactory uiObjFactory = new UIObjectFactory();
		int id = uiObjFactory.create(uObj);
		uiObjFactory.exit();
		return id;
	}
	
	public int addTestStep(TestStep tStep) {
		TestStepFactory tStepFactory = new TestStepFactory();
		int id = tStepFactory.create(tStep);
		tStepFactory.exit();
		return id;
	}

	public int addUStoryStep(UserStorySteps uStep) {
		UStoryStepFactory usStepFactory = new UStoryStepFactory();
		int id = usStepFactory.create(uStep);
		usStepFactory.exit();
		return id;
	}

	public int getStorySteps(int userStoryID) {
		UStoryStepFactory usStepFactory = new UStoryStepFactory();
		List<UserStorySteps> stepList = usStepFactory.getStorySteps(userStoryID);
		usStepFactory.exit();
		return stepList.size();
	}

	public void dropExisitingMapping(int id) {
		UStoryStepFactory usStepFactory = new UStoryStepFactory();
		usStepFactory.dropExisitingMapping(id);
		usStepFactory.exit();
	}

}
