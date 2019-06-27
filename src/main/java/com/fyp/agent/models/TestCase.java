package com.fyp.agent.models;

import javax.persistence.*;

@Entity
@Table(name = "testcase")
public class TestCase {

	@Id
	@Column(name = "testcase_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String no;
	private String title;

	@ManyToOne
	@JoinColumn(name = "userstory_id", nullable = false)
	private UserStory uStory;

	@ManyToOne
	@JoinColumn(name = "ac_id", nullable = false)
	private AcceptanceCriteria acceptanceCriteria;

	private String expectedResult;

	private String dateGenerated;
	private String lastExecutedDate;

	private String screenshot;

	private Boolean active = Boolean.TRUE;

	public TestCase() {
	}

	public TestCase(String no, String title, UserStory uStory, AcceptanceCriteria acceptanceCriteria, String expectedResult, String dateGenerated) {
		this.no = no;
		this.title = title;
		this.uStory = uStory;
		this.acceptanceCriteria = acceptanceCriteria;
		this.dateGenerated = dateGenerated;
		this.expectedResult = expectedResult;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public UserStory getuStory() {
		return uStory;
	}

	public void setuStory(UserStory uStory) {
		this.uStory = uStory;
	}

	public AcceptanceCriteria getAcceptanceCriteria() {
		return acceptanceCriteria;
	}

	public void setAcceptanceCriteria(AcceptanceCriteria acceptanceCriteria) {
		this.acceptanceCriteria = acceptanceCriteria;
	}

	public String getDateGenerated() {
		return dateGenerated;
	}

	public void setDateGenerated(String dateGenerated) {
		this.dateGenerated = dateGenerated;
	}

	public String getLastExecutedDate() {
		return lastExecutedDate;
	}

	public void setLastExecutedDate(String lastExecutedDate) {
		this.lastExecutedDate = lastExecutedDate;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public String getExpectedResult() {
		return expectedResult;
	}

	public void setExpectedResult(String expectedResult) {
		this.expectedResult = expectedResult;
	}

	public String getScreenshot() {
		return screenshot;
	}

	public void setScreenshot(String screenshot) {
		this.screenshot = screenshot;
	}
}
