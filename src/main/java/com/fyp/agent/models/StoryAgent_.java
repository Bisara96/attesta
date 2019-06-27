package com.fyp.agent.models;

import com.fyp.agent.models.Agent;
import com.fyp.agent.models.StoryAgent;
import com.fyp.agent.models.UserStory;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(StoryAgent.class)
public class StoryAgent_ {

    public static volatile SingularAttribute<StoryAgent, Integer> id;

    public static volatile SingularAttribute<StoryAgent, UserStory> userStory;

    public static volatile SingularAttribute<StoryAgent, Agent> agent;
}
