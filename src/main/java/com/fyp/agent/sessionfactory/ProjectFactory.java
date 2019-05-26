package com.fyp.agent.sessionfactory;

import com.fyp.agent.models.Project;
import org.hibernate.Session;

import java.util.List;

public class ProjectFactory extends DBFactory {

    public ProjectFactory() {
        super();
    }

    public int create(Project project) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.save(project);
        session.getTransaction().commit();
        session.close();
        return project.getId();
    }

    public Project read(int projectId) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        Project project = session.get(Project.class, projectId);

        session.getTransaction().commit();
        session.close();
        return project;
    }

    @SuppressWarnings({ "deprecation", "unchecked" })
    public List<Project> read() {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        List<Project> projectList = session.createCriteria(Project.class).list();

        session.close();
        return projectList;
    }

    public void update(Project project) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        session.update(project);

        session.getTransaction().commit();
        session.close();
    }

    public void delete(Project project) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        session.delete(project);

        session.getTransaction().commit();
        session.close();
    }

//	public static void main(String[] args) {
//		UserStoryFactory manager = new UserStoryFactory();
//	    manager.setup();
//	    Project story = new Project();
//	    story.setDescription("As a site member, I can send an email to any member via a form so that we can connect.");
//	    story.setId(1);
//	    story.setStatus("new");
//	    manager.update(story);
//	    manager.exit();
//	}
}
