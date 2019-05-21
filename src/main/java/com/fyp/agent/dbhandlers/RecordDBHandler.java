package com.fyp.agent.dbhandlers;

import java.util.List;

import com.fyp.agent.models.AcceptanceCriteria;
import com.fyp.agent.models.TestStep;
import com.fyp.agent.models.UIObject;
import com.fyp.agent.sessionfactory.AcceptanceCriteriaFactory;
import com.fyp.agent.sessionfactory.TestStepFactory;
import com.fyp.agent.sessionfactory.UIObjectFactory;

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
	
	public List<AcceptanceCriteria> getStoryACriteria(int userStoryID) {
		AcceptanceCriteriaFactory acFactory = new AcceptanceCriteriaFactory();
		List<AcceptanceCriteria> acList = acFactory.getStoryAcceptanceCriteria(userStoryID);
		acFactory.exit();
		return acList;
	}

}
