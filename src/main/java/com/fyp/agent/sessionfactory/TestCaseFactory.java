package com.fyp.agent.sessionfactory;

import com.fyp.agent.models.TestCase;
import com.fyp.agent.models.TestCaseSteps;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class TestCaseFactory extends DBFactory {

    public TestCaseFactory() {
        super();
    }

    public int create(TestCase testCase) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.save(testCase);
        session.getTransaction().commit();
        session.close();
        return testCase.getId();
    }

    public TestCase read(int testCaseId) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        TestCase testCase = session.get(TestCase.class, testCaseId);

        session.getTransaction().commit();
        session.close();
        return testCase;
    }

    public List<TestCase> getStoryTestCases(int storyID) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        Query query = session.createQuery("FROM TestCase WHERE userstory_id = :userstory_id AND active = TRUE");
        query.setParameter("userstory_id", storyID);
        List<TestCase> storyTestCases = query.getResultList();

        session.close();
        return storyTestCases;
    }

    @SuppressWarnings({ "deprecation", "unchecked" })
    public List<TestCase> read() {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        List<TestCase> testCaseList = session.createCriteria(TestCase.class).list();

        session.close();
        return testCaseList;
    }

    public void update(TestCase testCase) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        session.update(testCase);

        session.getTransaction().commit();
        session.close();
    }

    public void delete(TestCase testCase) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        session.delete(testCase);

        session.getTransaction().commit();
        session.close();
    }

    public void removeOldTestCases(int userStoryID) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Query query = session.createQuery("UPDATE TestCase SET active = FALSE WHERE userstory_id = :userstory_id AND active = TRUE");
        query.setParameter("userstory_id", userStoryID);
        query.executeUpdate();
        session.close();
    }

//    public static void main(String[] args){
//        TestCaseFactory testCaseFactory = new TestCaseFactory();
//        testCaseFactory.removeOldTestCases(1);
//        testCaseFactory.exit();
//    }


    
}
