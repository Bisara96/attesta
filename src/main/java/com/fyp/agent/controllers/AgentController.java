package com.fyp.agent.controllers;

import com.fyp.agent.handlers.AgentHandler;
import com.fyp.agent.models.Agent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin(maxAge = 3600)
@RequestMapping("/agent")
public class AgentController {

    @Autowired
    private Environment env;

    @Autowired
    private AgentHandler handler;

    @GetMapping(path="/create")
    public String createAgent(String name, String browser, String platform, String type) throws Exception {
        return handler.createAgent(name,type,browser,platform,env.getProperty("server.address"));
    }

    @GetMapping(path="/get_agent", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<InputStreamResource> getAgent(String zipName) throws Exception {
        return handler.getAgent(zipName);
    }

    @GetMapping(path="/agent_started")
    public void agentStarted(int id, int port) {
        handler.agentStarted(id, port);
    }

    @GetMapping(path="/agent_stopped")
    public void agentStopped(int id) {
        handler.agentStopped(id);
    }

    @GetMapping(path="/assign_agent")
    public String assignAgent(int storyID, int agentID) {
        return handler.assignAgentToStory(storyID, agentID);
    }

    @GetMapping(path="/remove_agent")
    public String removeAgent(int storyID, int agentID) {
        return handler.removeAgentFromStory(storyID, agentID);
    }

    @GetMapping(path="/get_assigned_agents")
    public List<Agent> getAssignedAgents(int storyID) {
        return handler.getAssignedAgents(storyID);
    }

    @GetMapping(path="/get_unassigned_agents")
    public List<Agent> getUnAssignedAgents(int storyID) {
        return handler.getUnAssignedAgents(storyID);
    }

}
