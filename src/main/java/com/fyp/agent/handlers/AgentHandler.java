package com.fyp.agent.handlers;

import com.fyp.agent.dbhandlers.AgentDBHandler;
import com.fyp.agent.dbhandlers.UserStoryDBHandler;
import com.fyp.agent.models.Agent;
import com.fyp.agent.models.StoryAgent;
import com.fyp.agent.models.UserStory;
import com.fyp.agent.utilities.CreateAgent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@Component
@Service
public class AgentHandler {

    private AgentDBHandler agentDBH;

    private UserStoryDBHandler storyDBH;

    @Autowired
    private Environment env;

    public AgentHandler(AgentDBHandler agentDBH, UserStoryDBHandler storyDBH) {
        this.agentDBH = agentDBH;
        this.storyDBH = storyDBH;
    }

    public String createAgent(String name, String type, String browser, String platform, String address) throws Exception {
        String hubUrl = "http://"+address+":4444";
        Agent agent = new Agent(name,type,browser,platform);
        agent.setId(agentDBH.addAgent(agent));
        String serverAddress = "http://"+env.getProperty("server.address")+":"+env.getProperty("server.port");
        CreateAgent agentCreator = new CreateAgent(hubUrl, serverAddress, agent);
        agentCreator.updateConfig();
        return agentCreator.bundleAgent();
    }

    public ResponseEntity<InputStreamResource> getAgent(String zipName) throws Exception {
        byte[] test = Files.readAllBytes(Paths.get(getClass().getResource("/generated-agents/"+zipName).toURI()));

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header("Content-Disposition", "attachment; filename="+zipName)
                .body(new InputStreamResource(new ByteArrayInputStream(test)));
    }

    public void agentStarted(int id, int port) {
        Agent agent = agentDBH.getAgent(id);
        agent.setPort(port);
        agent.setAlive(true);
        agentDBH.updateAgent(agent);
    }

    public void agentStopped(int id) {
        Agent agent = agentDBH.getAgent(id);
        agent.setAlive(false);
        agentDBH.updateAgent(agent);
    }

    public String assignAgentToStory(int storyID, int agentID) {
        UserStory story = storyDBH.getUserStory(storyID);
        Agent agent = agentDBH.getAgent(agentID);
        agentDBH.addStoryAgent(new StoryAgent(story, agent));
        return "Agent Assigned";
    }

    public String removeAgentFromStory(int storyID, int agentID) {
        agentDBH.removeStoryAgent(storyID, agentID);
        return "SUCCESSFULLY REMOVED";
    }

    public List<Agent> getAssignedAgents(int storyID) {
        return agentDBH.getAssignedAgents(storyID);
    }

    public List<Agent> getUnAssignedAgents(int storyID) {
        return agentDBH.getUnAssignedAgents(storyID);
    }
}
