package de.hdm.softwarepraktikum.shared.bo;

import java.util.ArrayList;

public class Group extends BusinessObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	//Name der Gruppe.
	private String title;
	
	//Mitglieder der Gruppe.
	private ArrayList<Person> member;
	
	//Standartartikel der Gruppe
	private ArrayList<ListItem> favoriteitem;

	
	
	/*
	 * Getter und Setter der Attribute
	 */
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public ArrayList<Person> getMember() {
		return member;
	}

	public void setMember(ArrayList<Person> member) {
		this.member = member;
	}

	public ArrayList<ListItem> getFavoriteitem() {
		return favoriteitem;
	}

	public void setFavoriteitem(ArrayList<ListItem> favoriteitem) {
		this.favoriteitem = favoriteitem;
	}
	
	
	

}
