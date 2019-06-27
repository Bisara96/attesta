package com.fyp.agent.models;

import javax.persistence.*;

@Entity
@Table(name = "story_agents")
public class StoryAgent {

    @Id
    @Column(name = "story_agent_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "userstory_id", nullable = false)
    private UserStory userStory;

    @ManyToOne
    @JoinColumn(name = "agent_id", nullable = false)
    private Agent agent;

    public StoryAgent() {
    }

    public StoryAgent(UserStory userStory, Agent agent) {
        this.userStory = userStory;
        this.agent = agent;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public UserStory getUserStory() {
        return userStory;
    }

    public void setUserStory(UserStory userStory) {
        this.userStory = userStory;
    }

    public Agent getAgent() {
        return agent;
    }

    public void setAgent(Agent agent) {
        this.agent = agent;
    }
}
