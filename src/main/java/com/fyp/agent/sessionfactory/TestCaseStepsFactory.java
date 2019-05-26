package com.fyp.agent.sessionfactory;

import com.fyp.agent.models.TestCaseSteps;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class TestCaseStepsFactory extends DBFactory {

    public TestCaseStepsFactory() {
        super();
    }

    public int create(TestCaseSteps tCaseSteps) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.save(tCaseSteps);
        session.getTransaction().commit();
        session.close();
        return tCaseSteps.getId();
    }

    public List<TestCaseSteps> batchCreate(List<TestCaseSteps> tCaseSteps) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        for(int i = 0; i < tCaseSteps.size();i++) {
            session.save(tCaseSteps.get(i));
            if( i % 50 == 0 ) {
                session.flush();
                session.clear();
            }
        }

        session.getTransaction().commit();
        session.close();
        return tCaseSteps;
    }


    public TestCaseSteps read(int tCaseStepsID) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        TestCaseSteps tCaseSteps = session.get(TestCaseSteps.class, tCaseStepsID);

        session.getTransaction().commit();
        session.close();
        return tCaseSteps;
    }

    @SuppressWarnings({ "deprecation", "unchecked" })
    public List<TestCaseSteps> read() {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        List<TestCaseSteps> tCaseStepsList = session.createCriteria(TestCaseSteps.class).list();

        session.close();
        return tCaseStepsList;
    }

    public List<TestCaseSteps> getTestCaseSteps(int testCaseID) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        Query query = session.createQuery("FROM TestCaseSteps WHERE testcase_id = :testcase_id");
        query.setParameter("testcase_id", testCaseID);
        List<TestCaseSteps> tCaseStepList = query.getResultList();

        session.close();
        return tCaseStepList;
    }

    public void update(TestCaseSteps tCaseSteps) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        session.update(tCaseSteps);

        session.getTransaction().commit();
        session.close();
    }

    public void delete(TestCaseSteps tCaseSteps) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        session.delete(tCaseSteps);

        session.getTransaction().commit();
        session.close();
    }

//    public static void main(String[] args) {
//        UStoryStepFactory manager = new UStoryStepFactory();
//        manager.setup();
//        List<TestCaseSteps> storyStepList = manager.getStorySteps(1);
//        manager.exit();
//    }

}
