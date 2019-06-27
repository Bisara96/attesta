package com.fyp.agent.models;

import javax.persistence.*;

@Entity
@Table(name = "acceptancecriteria")
public class AcceptanceCriteria {

	@Id
	@Column(name = "acceptancecriteria_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@ManyToOne
    @JoinColumn(name = "userstory_id", nullable = false)
	private UserStory userStory;
	
	private String criteria;

    public AcceptanceCriteria() {
    }

	public AcceptanceCriteria(UserStory userStory, String criteria) {
		this.userStory = userStory;
		this.criteria = criteria;
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

	public String getAcceptanceCriteria() {
		return criteria;
	}

	public void setAcceptanceCriteria(String acceptanceCriteria) {
		this.criteria = acceptanceCriteria;
	}
	
	
	
}
