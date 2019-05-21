package com.fyp.agent.sessionfactory;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class DBFactory {
	protected SessionFactory sessionFactory;

	
	public DBFactory() {
		setup();
	}

	public void setup() {
		final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
		        .configure() // configures settings from hibernate.cfg.xml
		        .build();
		try {
		    sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
		} catch (Exception ex) {
			System.out.println("Problem creating session factory");
		     ex.printStackTrace();
		    StandardServiceRegistryBuilder.destroy(registry);
		}
	}
	
	public void exit() {
		sessionFactory.close();
	}

}
