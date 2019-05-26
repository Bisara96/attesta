package com.fyp.agent.models;

import javax.persistence.*;

@Entity
@Table(name = "sprint")
public class Sprint {

    @Id
    @Column(name = "sprint_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String sprintName;

    @ManyToOne
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;

    public Sprint() {
    }

    public Sprint(String sprintName, Project project) {
        this.sprintName = sprintName;
        this.project = project;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSprintName() {
        return sprintName;
    }

    public void setSprintName(String sprintName) {
        this.sprintName = sprintName;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }
}
