package com.fyp.agent.sessionfactory;

import com.fyp.agent.models.TestStepResult;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class TestStepResultFactory extends DBFactory {
    public TestStepResultFactory() {
        super();
    }

    public TestStepResult create(TestStepResult tsResult) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.save(tsResult);
        session.getTransaction().commit();
        session.close();
        return tsResult;
    }

    public TestStepResult read(int ustoryId) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        TestStepResult tsResult = session.get(TestStepResult.class, ustoryId);

        session.getTransaction().commit();
        session.close();
        return tsResult;
    }

    @SuppressWarnings({ "deprecation", "unchecked" })
    public List<TestStepResult> read() {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        List<TestStepResult> ustoryList = session.createCriteria(TestStepResult.class).list();

        session.close();
        return ustoryList;
    }

    public List<TestStepResult> getTestStepResults(int tcResultID) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        Query query = session.createQuery("FROM TestStepResult WHERE testcase_result_id = :testcase_result_id");
        query.setParameter("testcase_result_id", tcResultID);
        List<TestStepResult> tsResultList = query.getResultList();

        session.close();
        return tsResultList;
    }

    public void update(TestStepResult tsResult) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        session.update(tsResult);

        session.getTransaction().commit();
        session.close();
    }

    public void delete(TestStepResult tsResult) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        session.delete(tsResult);

        session.getTransaction().commit();
        session.close();
    }

//	public static void main(String[] args) {
//		TestStepResultFactory manager = new TestStepResultFactory();
//	    manager.setup();
//	    TestStepResult tsResult = new TestStepResult();
//	    tsResult.setDescription("As a site member, I can send an email to any member via a form so that we can connect.");
//	    tsResult.setId(1);
//	    tsResult.setStatus("new");
//	    manager.update(tsResult);
//	    manager.exit();
//	}
}
