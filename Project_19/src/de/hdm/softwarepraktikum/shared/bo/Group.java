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

	/**
	   * Fremdschl�sselbeziehung zu einem Mitglied der Gruppe.
	   */
	private int memberID;
	
	
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

	/**
	 * Auslesen des Fremdschl�ssels zu einem Gruppenmitglied
	 * @return memberID
	 */
	public int getMemberID() {
		return memberID;
	}

	/**
	 * Setzen des Fremdschl�ssels zu einem Gruppenmitglied
	 * @param memberID
	 */
	public void setMemberID(int memberID) {
		this.memberID = memberID;
	}
	
	
	

}
