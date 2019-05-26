package com.fyp.agent.models;

import javax.persistence.*;

@Entity
@Table(name = "userstorysteps")
public class UserStorySteps {

    @Id
    @Column(name = "userstorystep_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "userstory_id", nullable = false)
    private UserStory userStory;

    @OneToOne
    @JoinColumn(name = "teststep_id", nullable = false)
    private TestStep testStep;

    public UserStorySteps() {
    }

    public UserStorySteps(UserStory userStory, TestStep testStep) {
        this.userStory = userStory;
        this.testStep = testStep;
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

    public TestStep getTestStep() {
        return testStep;
    }

    public void setTestStep(TestStep testStep) {
        this.testStep = testStep;
    }
}
