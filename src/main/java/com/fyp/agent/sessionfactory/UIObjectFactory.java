package com.fyp.agent.sessionfactory;

import com.fyp.agent.models.UIObject;
import org.hibernate.Session;

import java.util.List;

public class UIObjectFactory extends DBFactory {
	
	
	public UIObjectFactory() {
		super();
	}

	public int create(UIObject uObj) {
	    Session session = sessionFactory.openSession();
	    session.beginTransaction();
	    session.save(uObj);	 
	    session.getTransaction().commit();
	    session.close();
	    return uObj.getId();
	}
	
	public UIObject read(int uObjID) {
		Session session = sessionFactory.openSession();
	    session.beginTransaction();
	    
	    UIObject uObj = session.get(UIObject.class, uObjID);
	    
	    session.getTransaction().commit();
	    session.close();
	    return uObj;
	}
	
	@SuppressWarnings({ "deprecation", "unchecked" })
	public List<UIObject> read() {
		Session session = sessionFactory.openSession();
	    session.beginTransaction();
	    
	    List<UIObject> uObjList = session.createCriteria(UIObject.class).list();
	    
	    session.close();
	    return uObjList;
	}
	
	public void update(UIObject uObj) {
		Session session = sessionFactory.openSession();
	    session.beginTransaction();
	 
	    session.update(uObj);
	    
	    session.getTransaction().commit();
	    session.close();
	}
	
	public void delete(UIObject uObj) {
		Session session = sessionFactory.openSession();
	    session.beginTransaction();
	 
	    session.delete(uObj);
	 
	    session.getTransaction().commit();
	    session.close();
	}
	
	public static void main(String[] args) {
		UIObjectFactory manager = new UIObjectFactory();
	    manager.setup();
	    UIObject uObj = new UIObject("test","test","test","test","test","test","test");
	    manager.create(uObj);
	    manager.exit();
	}

}
