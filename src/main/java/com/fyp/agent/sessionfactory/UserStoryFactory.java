package com.fyp.agent.sessionfactory;

import java.util.List;

import com.fyp.agent.models.Sprint;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import com.fyp.agent.models.UserStory;
import org.hibernate.query.Query;

public class UserStoryFactory extends DBFactory {
	
	
	public UserStoryFactory() {
		super();
	}

	public UserStory create(UserStory ustory) {
	    Session session = sessionFactory.openSession();
	    session.beginTransaction();
	    session.save(ustory);	 
	    session.getTransaction().commit();
	    session.close();
	    return ustory;
	}
	
	public UserStory read(int ustoryId) {
		Session session = sessionFactory.openSession();
	    session.beginTransaction();
	    
	    UserStory ustory = session.get(UserStory.class, ustoryId);
	    
	    session.getTransaction().commit();
	    session.close();
	    return ustory;
	}
	
	@SuppressWarnings({ "deprecation", "unchecked" })
	public List<UserStory> read() {
		Session session = sessionFactory.openSession();
	    session.beginTransaction();
	    
	    List<UserStory> ustoryList = session.createCriteria(UserStory.class).list();
	    
	    session.close();
	    return ustoryList;
	}

	public List<UserStory> getSprintStories(int sprintID) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();

		Query query = session.createQuery("FROM UserStory WHERE sprint_id = :sprint_id");
		query.setParameter("sprint_id", sprintID);
		List<UserStory> storyList = query.getResultList();

		session.close();
		return storyList;
	}
	
	public void update(UserStory ustory) {
		Session session = sessionFactory.openSession();
	    session.beginTransaction();
	 
	    session.update(ustory);
	    
	    session.getTransaction().commit();
	    session.close();
	}
	
	public void delete(UserStory ustory) {
		Session session = sessionFactory.openSession();
	    session.beginTransaction();
	 
	    session.delete(ustory);
	 
	    session.getTransaction().commit();
	    session.close();
	}
	
//	public static void main(String[] args) {
//		UserStoryFactory manager = new UserStoryFactory();
//	    manager.setup();
//	    UserStory story = new UserStory();
//	    story.setDescription("As a site member, I can send an email to any member via a form so that we can connect.");
//	    story.setId(1);
//	    story.setStatus("new");
//	    manager.update(story);
//	    manager.exit();
//	}
}
