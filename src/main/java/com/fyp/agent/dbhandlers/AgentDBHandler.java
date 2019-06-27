package com.fyp.agent.dbhandlers;

import com.fyp.agent.models.Agent;
import com.fyp.agent.models.StoryAgent;
import com.fyp.agent.models.TestCaseResult;
import com.fyp.agent.models.TestStepResult;
import com.fyp.agent.sessionfactory.AgentFactory;
import com.fyp.agent.sessionfactory.StoryAgentFactory;
import com.fyp.agent.sessionfactory.TestCaseResultFactory;
import com.fyp.agent.sessionfactory.TestStepResultFactory;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

@Component
@Repository
public class AgentDBHandler {

    public int addAgent(Agent agent) {
        AgentFactory agentFactory = new AgentFactory();
        int id = agentFactory.create(agent);
        agentFactory.exit();
        return id;
    }

    public Agent getAgent(int id) {
        AgentFactory agentFactory = new AgentFactory();
        Agent agent = agentFactory.read(id);
        agentFactory.exit();
        return agent;
    }

    public void updateAgent(Agent agent) {
        AgentFactory agentFactory = new AgentFactory();
        agentFactory.update(agent);
        agentFactory.exit();
    }

    public void addStoryAgent(StoryAgent storyAgent) {
        StoryAgentFactory storyAgentFactory = new StoryAgentFactory();
        storyAgentFactory.create(storyAgent);
        storyAgentFactory.exit();
    }

    public void removeStoryAgent(StoryAgent storyAgent) {
        StoryAgentFactory storyAgentFactory = new StoryAgentFactory();
        storyAgentFactory.delete(storyAgent);
        storyAgentFactory.exit();
    }

    public void removeStoryAgent(int storyID, int agentID) {
        StoryAgentFactory storyAgentFactory = new StoryAgentFactory();
        storyAgentFactory.removeAgent(storyID, agentID);
        storyAgentFactory.exit();
    }

    public List<Agent> getAssignedAgents(int storyID) {
        StoryAgentFactory storyAgentFactory = new StoryAgentFactory();
        List<Agent> agents = storyAgentFactory.getAssignedAgents(storyID);
        storyAgentFactory.exit();
        return agents;
    }

    public List<Agent> getUnAssignedAgents(int storyID) {
        StoryAgentFactory storyAgentFactory = new StoryAgentFactory();
        List<Agent> agents = storyAgentFactory.getUnAssignedAgents(storyID);
        storyAgentFactory.exit();
        return agents;
    }
}
