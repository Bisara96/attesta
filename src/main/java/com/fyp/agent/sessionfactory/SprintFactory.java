package com.fyp.agent.sessionfactory;

import com.fyp.agent.models.Sprint;
import com.fyp.agent.models.UserStorySteps;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class SprintFactory extends DBFactory {

    public SprintFactory() {
        super();
    }

    public Sprint create(Sprint sprint) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.save(sprint);
        session.getTransaction().commit();
        session.close();
        return sprint;
    }

    public Sprint read(int sprintId) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        Sprint sprint = session.get(Sprint.class, sprintId);

        session.getTransaction().commit();
        session.close();
        return sprint;
    }

    @SuppressWarnings({ "deprecation", "unchecked" })
    public List<Sprint> read() {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        List<Sprint> sprintList = session.createCriteria(Sprint.class).list();

        session.close();
        return sprintList;
    }

    public List<Sprint> getProjectSprints(int projectID) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        Query query = session.createQuery("FROM Sprint WHERE project_id = :project_id");
        query.setParameter("project_id", projectID);
        List<Sprint> sprintList = query.getResultList();

        session.close();
        return sprintList;
    }

    public void update(Sprint sprint) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        session.update(sprint);

        session.getTransaction().commit();
        session.close();
    }

    public void delete(Sprint sprint) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        session.delete(sprint);

        session.getTransaction().commit();
        session.close();
    }

//	public static void main(String[] args) {
//		UserStoryFactory manager = new UserStoryFactory();
//	    manager.setup();
//	    Sprint story = new Sprint();
//	    story.setDescription("As a site member, I can send an email to any member via a form so that we can connect.");
//	    story.setId(1);
//	    story.setStatus("new");
//	    manager.update(story);
//	    manager.exit();
//	}
}
