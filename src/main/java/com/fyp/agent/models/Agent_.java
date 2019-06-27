package com.fyp.agent.models;

import com.fyp.agent.models.Agent;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Agent.class)
public class Agent_ {

    public static volatile SingularAttribute<Agent, Integer> id;

    public static volatile SingularAttribute<Agent, String> name;

    public static volatile SingularAttribute<Agent, String> type;

    public static volatile SingularAttribute<Agent, String> browser;

    public static volatile SingularAttribute<Agent, String> platform;

    public static volatile SingularAttribute<Agent, Boolean> isAlive;

    public static volatile SingularAttribute<Agent, Integer> port;
}
