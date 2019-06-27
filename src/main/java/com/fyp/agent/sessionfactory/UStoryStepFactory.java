package com.fyp.agent.sessionfactory;

import com.fyp.agent.models.UserStorySteps;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class UStoryStepFactory extends DBFactory {

    public UStoryStepFactory() {
        super();
    }

    public int create(UserStorySteps usSteps) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.save(usSteps);
        session.getTransaction().commit();
        session.close();
        return usSteps.getId();
    }

    public UserStorySteps read(int usStepsID) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        UserStorySteps usSteps = session.get(UserStorySteps.class, usStepsID);

        session.getTransaction().commit();
        session.close();
        return usSteps;
    }

    @SuppressWarnings({ "deprecation", "unchecked" })
    public List<UserStorySteps> read() {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        List<UserStorySteps> usStepsList = session.createCriteria(UserStorySteps.class).list();

        session.close();
        return usStepsList;
    }

    public List<UserStorySteps> getStorySteps(int userStoryID) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        Query query = session.createQuery("FROM UserStorySteps WHERE userstory_id = :userstory_id");
        query.setParameter("userstory_id", userStoryID);
        List<UserStorySteps> storyStepList = query.getResultList();

        session.close();
        return storyStepList;
    }

    public void update(UserStorySteps usSteps) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        session.update(usSteps);

        session.getTransaction().commit();
        session.close();
    }

    public void delete(UserStorySteps usSteps) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        session.delete(usSteps);

        session.getTransaction().commit();
        session.close();
    }

    public void dropExisitingMapping(int storyID) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Query query = session.createQuery("DELETE UserStorySteps  WHERE userstory_id = :userstory_id");
        query.setParameter("userstory_id", storyID);
        query.executeUpdate();
        session.getTransaction().commit();
        session.close();
    }

    public static void main(String[] args) {
        UStoryStepFactory manager = new UStoryStepFactory();
        manager.setup();
        List<UserStorySteps> storyStepList = manager.getStorySteps(1);
//        manager.dropExisitingMapping(1);
        manager.exit();
    }
}
