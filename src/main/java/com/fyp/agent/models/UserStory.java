package com.fyp.agent.models;

import javax.persistence.*;

@Entity
@Table(name = "userstory")
public class UserStory {
	
	@Id
	@Column(name = "userstory_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String description;

	private String status;
	
	private String url;

	@ManyToOne
	@JoinColumn(name = "sprint_id", nullable = false)
	private Sprint sprint;

	public UserStory() {
	}

	public UserStory(String description, String status, String url, Sprint sprint) {
		this.description = description;
		this.status = status;
		this.url = url;
		this.sprint = sprint;
	}

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

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Sprint getSprint() {
		return sprint;
	}

	public void setSprint(Sprint sprint) {
		this.sprint = sprint;
	}
}
