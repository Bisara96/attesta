package com.fyp.agent.models;

import javax.persistence.*;

@Entity
@Table(name = "agent")
public class Agent {

    @Id
    @Column(name = "agent_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    private String type;

    private String browser;

    private String platform;

    private Boolean isAlive;

    private int port;

    public Agent() {
    }

    public Agent(String name, String type, String browser, String platform) {
        this.name = name;
        this.type = type.toLowerCase();
        this.browser = browser.toLowerCase();
        this.platform = platform.toLowerCase();
        this.isAlive = false;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBrowser() {
        return browser;
    }

    public void setBrowser(String browser) {
        this.browser = browser;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public Boolean getAlive() {
        return isAlive;
    }

    public void setAlive(Boolean alive) {
        isAlive = alive;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
