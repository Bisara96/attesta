package com.fyp.agent.sessionfactory;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.query.Query;

import com.fyp.agent.models.AcceptanceCriteria;

public class AcceptanceCriteriaFactory extends DBFactory {

	public AcceptanceCriteriaFactory() {
		super();
	}
	
	public void create(AcceptanceCriteria acptCriteria) {
	    Session session = sessionFactory.openSession();
	    session.beginTransaction();
	    session.save(acptCriteria);	 
	    session.getTransaction().commit();
	    session.close();
	}
	
	public AcceptanceCriteria read(int acptCriteriaId) {
		Session session = sessionFactory.openSession();
	    session.beginTransaction();
	    
	    AcceptanceCriteria acptCriteria = session.get(AcceptanceCriteria.class, acptCriteriaId);
	    
	    session.getTransaction().commit();
	    session.close();
	    return acptCriteria;
	}
	
	@SuppressWarnings({ "deprecation", "unchecked" })
	public List<AcceptanceCriteria> getStoryAcceptanceCriteria(int userStoryID) {
		Session session = sessionFactory.openSession();
	    session.beginTransaction();
	    
	    Query query = session.createQuery("FROM AcceptanceCriteria WHERE userstory_id = :userstory_id");
	    query.setParameter("userstory_id", userStoryID);
	    List<AcceptanceCriteria> acptCriteriaList = query.getResultList();
	    
	    session.close();
	    return acptCriteriaList;
	}
	
	public void update(AcceptanceCriteria acptCriteria) {
		Session session = sessionFactory.openSession();
	    session.beginTransaction();
	 
	    session.update(acptCriteria);
	    
	    session.getTransaction().commit();
	    session.close();
	}
	
	public void delete(AcceptanceCriteria acptCriteria) {
		Session session = sessionFactory.openSession();
	    session.beginTransaction();
	 
	    session.delete(acptCriteria);
	 
	    session.getTransaction().commit();
	    session.close();
	}

}
