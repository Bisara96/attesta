package com.fyp.agent.sessionfactory;

import com.fyp.agent.models.Agent;
import org.hibernate.Session;

import java.util.List;

public class AgentFactory extends DBFactory {


	public AgentFactory() {
		super();
	}

	public int create(Agent agent) {
	    Session session = sessionFactory.openSession();
	    session.beginTransaction();
	    session.save(agent);
	    session.getTransaction().commit();
	    session.close();
	    return agent.getId();
	}

	public Agent read(int agentID) {
		Session session = sessionFactory.openSession();
	    session.beginTransaction();

	    Agent agent = session.get(Agent.class, agentID);

	    session.getTransaction().commit();
	    session.close();
	    return agent;
	}

	@SuppressWarnings({ "deprecation", "unchecked" })
	public List<Agent> read() {
		Session session = sessionFactory.openSession();
	    session.beginTransaction();

	    List<Agent> agentList = session.createCriteria(Agent.class).list();

	    session.close();
	    return agentList;
	}

	public void update(Agent agent) {
		Session session = sessionFactory.openSession();
	    session.beginTransaction();

	    session.update(agent);

	    session.getTransaction().commit();
	    session.close();
	}

	public void delete(Agent agent) {
		Session session = sessionFactory.openSession();
	    session.beginTransaction();

	    session.delete(agent);

	    session.getTransaction().commit();
	    session.close();
	}

//	public static void main(String[] args) {
//		AgentFactory manager = new AgentFactory();
//	    manager.setup();
//	    Agent agent = new Agent("test","test","test","test","test","test","test");
//	    manager.create(agent);
//	    manager.exit();
//	}

}
