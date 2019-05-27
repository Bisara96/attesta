package com.fyp.agent.sessionfactory;

import com.fyp.agent.models.TestCaseResult;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class TestCaseResultFactory extends DBFactory {
    public TestCaseResultFactory() {
        super();
    }

    public TestCaseResult create(TestCaseResult tcResult) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.save(tcResult);
        session.getTransaction().commit();
        session.close();
        return tcResult;
    }

    public TestCaseResult read(int tcResultId) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        TestCaseResult tcResult = session.get(TestCaseResult.class, tcResultId);

        session.getTransaction().commit();
        session.close();
        return tcResult;
    }

    @SuppressWarnings({ "deprecation", "unchecked" })
    public List<TestCaseResult> read() {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        List<TestCaseResult> tcResultList = session.createCriteria(TestCaseResult.class).list();

        session.close();
        return tcResultList;
    }

    public List<TestCaseResult> getTestCaseResults(int testCaseID) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        Query query = session.createQuery("FROM TestCaseResult WHERE testcase_id = :testcase_id ORDER BY tc_result_id DESC");
        query.setParameter("testcase_id", testCaseID);
        List<TestCaseResult> tcResultList = query.getResultList();

        session.close();
        return tcResultList;
    }

    public List<TestCaseResult> getLastResult(int testCaseID) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        Query query = session.createQuery("FROM TestCaseResult WHERE testcase_id = :testcase_id ORDER BY tc_result_id DESC");
        query.setMaxResults(1);
        query.setParameter("testcase_id", testCaseID);
        List<TestCaseResult> tcResultList = query.getResultList();

        session.close();
        return tcResultList;
    }

    public void update(TestCaseResult tcResult) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        session.update(tcResult);

        session.getTransaction().commit();
        session.close();
    }

    public void delete(TestCaseResult tcResult) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        session.delete(tcResult);

        session.getTransaction().commit();
        session.close();
    }

//	public static void main(String[] args) {
//		TestCaseResultFactory manager = new TestCaseResultFactory();
//	    manager.setup();
//	    TestCaseResult tcResult = new TestCaseResult();
//	    tcResult.setDescription("As a site member, I can send an email to any member via a form so that we can connect.");
//	    tcResult.setId(1);
//	    tcResult.setStatus("new");
//	    manager.update(tcResult);
//	    manager.exit();
//	}
}
