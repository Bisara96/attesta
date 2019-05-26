package com.fyp.agent.models;

public class ParsedStep {
	
	private String type;
	
	private String xpath;
	
	private String value;
	
	private int keyCode;



	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getXpath() {
		return xpath;
	}

	public void setXpath(String xpath) {
		this.xpath = xpath;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public int getKeyCode() {
		return keyCode;
	}

	public void setKeyCode(int keyCode) {
		this.keyCode = keyCode;
	}

}
