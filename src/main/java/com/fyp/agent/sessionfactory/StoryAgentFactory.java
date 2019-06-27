package com.fyp.agent.sessionfactory;

import com.fyp.agent.models.Agent_;
import com.fyp.agent.models.StoryAgent_;
import com.fyp.agent.models.Agent;
import com.fyp.agent.models.StoryAgent;
import org.hibernate.Session;
import org.hibernate.query.Query;

import javax.persistence.EntityManager;
import javax.persistence.criteria.*;
import javax.persistence.metamodel.Metamodel;
import java.util.List;

public class StoryAgentFactory extends DBFactory {

	public StoryAgentFactory() {
		super();
	}
	
	public void create(StoryAgent storyAgent) {
	    Session session = sessionFactory.openSession();
	    session.beginTransaction();
	    session.save(storyAgent);	 
	    session.getTransaction().commit();
	    session.close();
	}
	
	public StoryAgent read(int storyAgentID) {
		Session session = sessionFactory.openSession();
	    session.beginTransaction();
	    
	    StoryAgent storyAgent = session.get(StoryAgent.class, storyAgentID);
	    
	    session.getTransaction().commit();
	    session.close();
	    return storyAgent;
	}
	
	@SuppressWarnings({ "deprecation", "unchecked" })
	public List<StoryAgent> getStoryAgents(int userStoryID) {
		Session session = sessionFactory.openSession();
	    session.beginTransaction();
	    
	    Query query = session.createQuery("FROM StoryAgent WHERE userstory_id = :userstory_id");
	    query.setParameter("userstory_id", userStoryID);
	    List<StoryAgent> storyAgentList = query.getResultList();
	    
	    session.close();
	    return storyAgentList;
	}
	
	public void update(StoryAgent storyAgent) {
		Session session = sessionFactory.openSession();
	    session.beginTransaction();
	 
	    session.update(storyAgent);
	    
	    session.getTransaction().commit();
	    session.close();
	}
	
	public void delete(StoryAgent storyAgent) {
		Session session = sessionFactory.openSession();
	    session.beginTransaction();
	 
	    session.delete(storyAgent);
	 
	    session.getTransaction().commit();
	    session.close();
	}

    public void removeAgent(int storyID, int agentID) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaDelete<StoryAgent> criteriaQuery = criteriaBuilder.createCriteriaDelete(StoryAgent.class);
        Root<StoryAgent> root = criteriaQuery.from(StoryAgent.class);

        criteriaQuery.where(criteriaBuilder.and(criteriaBuilder.equal(root.get(StoryAgent_.userStory), storyID), criteriaBuilder.equal(root.get(StoryAgent_.agent), agentID)));

        session.createQuery(criteriaQuery).executeUpdate();

        session.close();
    }

    public List<Agent> getAssignedAgents(int storyID) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        EntityManager entityManager = sessionFactory.createEntityManager();
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Agent> criteriaQuery = criteriaBuilder.createQuery(Agent.class);
        Metamodel metamodel = entityManager.getMetamodel();
        Root<Agent> root = criteriaQuery.from(metamodel.entity(Agent.class));

        Subquery<Integer> subquery = criteriaQuery.subquery(Integer.class);
        Root<StoryAgent> subRoot = subquery.from(metamodel.entity(StoryAgent.class));
        subquery.select(subRoot.get(StoryAgent_.agent).get(Agent_.id));
//        ParameterExpression<Integer> storyIDParam = criteriaBuilder.parameter(Integer.class);
        Predicate storyIDExp = criteriaBuilder.equal(subRoot.get(StoryAgent_.userStory), storyID);
        subquery.where(storyIDExp);

        criteriaQuery.where(criteriaBuilder.in(root.get(Agent_.id)).value(subquery));

        List<Agent> agentList = entityManager.createQuery(criteriaQuery).getResultList();

        session.close();
        return agentList;
    }

	public List<Agent> getUnAssignedAgents(int storyID) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();

        EntityManager entityManager = sessionFactory.createEntityManager();
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Agent> criteriaQuery = criteriaBuilder.createQuery(Agent.class);
        Metamodel metamodel = entityManager.getMetamodel();
        Root<Agent> root = criteriaQuery.from(metamodel.entity(Agent.class));

        Subquery<Integer> subquery = criteriaQuery.subquery(Integer.class);
        Root<StoryAgent> subRoot = subquery.from(metamodel.entity(StoryAgent.class));
        subquery.select(subRoot.get(StoryAgent_.agent).get(Agent_.id));
//        ParameterExpression<Integer> storyIDParam = criteriaBuilder.parameter(Integer.class);
        Predicate storyIDExp = criteriaBuilder.equal(subRoot.get(StoryAgent_.userStory), storyID);
        subquery.where(storyIDExp);

        criteriaQuery.where(criteriaBuilder.in(root.get(Agent_.id)).value(subquery).not());

		List<Agent> agentList = entityManager.createQuery(criteriaQuery).getResultList();

		session.close();
		return agentList;
	}

    public static void main(String[] args) {
        StoryAgentFactory storyAgentFactory = new StoryAgentFactory();
        storyAgentFactory.getAssignedAgents(1);
    }

}
