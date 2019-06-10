package de.hdm.softwarepraktikum.shared.bo;

import java.util.ArrayList;

public class Group extends BusinessObject {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static int nextId = 1;
	private int tempID;
	//Name der Gruppe.
	private String title;
	
	//Mitglieder der Gruppe.
	private ArrayList<Person> member;
	
	//Standartartikel der Gruppe
	private ArrayList<ListItem> favoriteitem;
	
	private ArrayList<ShoppingList> shoppingLists;

	
	
	public Group() {
		this.tempID = nextId;
		nextId++;
	}
	/*
	 * Getter und Setter der Attribute
	 */
	
	public int getTempID() {
		return this.tempID;
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	public ArrayList<ShoppingList> getShoppingLists() {
		return shoppingLists;
	}

	public void setShoppingLists(ArrayList<ShoppingList> shoppingLists) {
		this.shoppingLists = shoppingLists;
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
