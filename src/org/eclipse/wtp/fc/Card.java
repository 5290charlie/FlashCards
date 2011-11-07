package org.eclipse.wtp.fc;

import java.io.*;

public class Card implements Serializable {
	private static final long serialVersionUID = 1;
	
	private int id;
	private String title;
	private String description;
	
	public Card(String title, String description) {
		this.title = title;
		this.description = description;
	}
	
	public int getID() {
		return id;
	}
	
	public void setID(int id) {
		this.id = id;
	}
	
	public String getTitleShort() {
		if (title.length() > 9) {
			return title.substring(0, 8) + "..";
		} else {
			return title;
		}
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}	
}
