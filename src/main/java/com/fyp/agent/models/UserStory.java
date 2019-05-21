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
	
	

}
