package de.hdm.softwarepraktikum.shared.bo;

import java.util.ArrayList;

public class Group extends BusinessObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	//Name der Gruppe.
	private String name;
	
	//Mitglieder der Gruppe.
	private ArrayList<Person> member;
	
	//Standartartikel der Gruppe
	private ArrayList<Listitem> favoriteitem;

	
	
	/*
	 * Getter und Setter der Attribute
	 */
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ArrayList<Person> getMember() {
		return member;
	}

	public void setMember(ArrayList<Person> member) {
		this.member = member;
	}

	public ArrayList<Listitem> getFavoriteitem() {
		return favoriteitem;
	}

	public void setFavoriteitem(ArrayList<ListItem> favoriteitem) {
		this.favoriteitem = favoriteitem;
	}
	
	
	

}
