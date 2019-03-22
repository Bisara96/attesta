package com.fyp.agent.models;

import javax.persistence.*;

@Entity
@Table(name = "userstory")
public class UserStory {
	
	private int id;

	private String description;

	private String status;
	
	private String stepsJson;
	
	private String url;
	
	private String acceptanceCriteria;
	
	private void UserStory() {
	}
	
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStepsJson() {
		return stepsJson;
	}

	public void setStepsJson(String stepsJson) {
		this.stepsJson = stepsJson;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getAcceptanceCriteria() {
		return acceptanceCriteria;
	}

	public void setAcceptanceCriteria(String acceptanceCriteria) {
		this.acceptanceCriteria = acceptanceCriteria;
	}
	
	

}
