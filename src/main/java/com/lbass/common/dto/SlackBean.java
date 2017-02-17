package com.lbass.common.dto;

public class SlackBean {

	private String link;
	private String name;
	
	public SlackBean(String name, String link) {
		this.name = name;
		this.link = link;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
}
