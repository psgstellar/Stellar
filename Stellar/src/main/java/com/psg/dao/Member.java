package com.psg.dao;

public class Member {
	private String slack_id;
	private String slack_username;
	private String name;
	
	public Member(String id, String username, String name) {
		super();
		this.slack_id = id;
		this.slack_username = username;
		this.name = name;
	}
	
	public String getId() {
		return slack_id;
	}
	
	public void setId(String id) {
		this.slack_id = id;
	}
	
	public String getUName() {
		return slack_username;
	}
	
	public void setUName(String username) {
		this.slack_username = username;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	

}
