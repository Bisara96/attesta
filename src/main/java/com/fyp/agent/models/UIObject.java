package com.fyp.agent.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "uiobject")
public class UIObject {
	
	@Id
	@Column(name = "uiobject_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String name;

	private String tagName;
	
	private String label;
	
	private String xpath;
	
	private String innerText;
	
	private String placeholder;
	
	private String elementID;
	
	

	public UIObject(String name, String tagName, String label, String xpath, String innerText, String placeholder,
			String elementID) {
		super();
		this.name = name;
		this.tagName = tagName;
		this.label = label;
		this.xpath = xpath;
		this.innerText = innerText;
		this.placeholder = placeholder;
		this.elementID = elementID;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTagName() {
		return tagName;
	}

	public void setTagName(String tagName) {
		this.tagName = tagName;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getXpath() {
		return xpath;
	}

	public void setXpath(String xpath) {
		this.xpath = xpath;
	}

	public String getInnerText() {
		return innerText;
	}

	public void setInnerText(String innerText) {
		this.innerText = innerText;
	}

	public String getPlaceholder() {
		return placeholder;
	}

	public void setPlaceholder(String placeholder) {
		this.placeholder = placeholder;
	}

	public String getElementID() {
		return elementID;
	}

	public void setElementID(String elementID) {
		this.elementID = elementID;
	}
	
	

}
