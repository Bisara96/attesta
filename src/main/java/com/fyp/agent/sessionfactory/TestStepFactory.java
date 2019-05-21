package com.fyp.agent.sessionfactory;

import java.util.List;

import org.hibernate.Session;

import com.fyp.agent.models.TestStep;

public class TestStepFactory extends DBFactory {

	public TestStepFactory() {
		super();
	}
	
	public int create(TestStep testStep) {
	    Session session = sessionFactory.openSession();
	    session.beginTransaction();
	    session.save(testStep);	 
	    session.getTransaction().commit();
	    session.close();
	    return testStep.getId();
	}
	
	public TestStep read(int testStepID) {
		Session session = sessionFactory.openSession();
	    session.beginTransaction();
	    
	    TestStep testStep = session.get(TestStep.class, testStepID);
	    
	    session.getTransaction().commit();
	    session.close();
	    return testStep;
	}
	
	@SuppressWarnings({ "deprecation", "unchecked" })
	public List<TestStep> read() {
		Session session = sessionFactory.openSession();
	    session.beginTransaction();
	    
	    List<TestStep> testStepList = session.createCriteria(TestStep.class).list();
	    
	    session.close();
	    return testStepList;
	}
	
	public void update(TestStep testStep) {
		Session session = sessionFactory.openSession();
	    session.beginTransaction();
	 
	    session.update(testStep);
	    
	    session.getTransaction().commit();
	    session.close();
	}
	
	public void delete(TestStep testStep) {
		Session session = sessionFactory.openSession();
	    session.beginTransaction();
	 
	    session.delete(testStep);
	 
	    session.getTransaction().commit();
	    session.close();
	}
	
//	public static void main(String[] args) {
//		TestStepFactory manager = new TestStepFactory();
//	    manager.setup();
//	    TestStep testStep = new TestStep();
//	    manager.create(testStep);
//	    manager.exit();
//	}

}
