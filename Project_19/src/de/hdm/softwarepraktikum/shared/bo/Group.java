package de.hdm.softwarepraktikum.shared.bo;

import java.util.ArrayList;

public class Group extends BusinessObject {

	private static final long serialVersionUID = 1L;
	
	private static int nextId = 1;
	private int tempID;
	
	//Name der Gruppe.
	private String title;
	
	//Mitglieder der Gruppe.
	private ArrayList<Person> member;
	
	//Standartartikel der Gruppe
	private ArrayList<ListItem> favoriteitem;
	
	//ShoppingLists der Gruppe
	private ArrayList<ShoppingList> shoppingLists;

	
	
	public Group() {
		this.tempID = nextId;
		nextId++;
	}
	
	/**
	    * **************************************************************************************
	    * ABSCHNITT Anfang: Getter und Setter der Attribute
	    * **************************************************************************************
	    */
	
	/*
	 * Auslesen der temporären ID
	 * @return Rückgabe der tempID
	 */
	
	public int getTempID() {
		return this.tempID;
	}
	
	/*
	 * Auslesen des Namens der Gruppe
	 * @return title wird zurückgegeben
	 */
	
	public String getTitle() {
		return title;
	}
	
	/*
	 * Setzen des Namens der Gruppe
	 * @param title
	 */

	public void setTitle(String title) {
		this.title = title;
	}
	
	/*
	 * Auslesen der Shoppinglists als Array
	 * @return shoppingLists wird zurückgegeben
	 */
	
	public ArrayList<ShoppingList> getShoppingLists() {
		return shoppingLists;
	}
	
	/*
	 * Setzen der ShoppingLists als Array
	 * @param shoppingLists
	 */

	public void setShoppingLists(ArrayList<ShoppingList> shoppingLists) {
		this.shoppingLists = shoppingLists;
	}
	
	/*
	 * Auslesen der Gruppen-Teilnehmer
	 * @return Rückgaber member als ArrayList
	 */

	public ArrayList<Person> getMember() {
		return member;
	}
	
	/*
	 * Setzen der Teilnehmer einer Gruppe als ArrayList
	 * @param member
	 */

	public void setMember(ArrayList<Person> member) {
		this.member = member;
	}
	
	/*
	 * Auslesen der favorisierten Items als ArrayList
	 * @return favoriteItem
	 */

	public ArrayList<ListItem> getFavoriteitem() {
		return favoriteitem;
	}
	
	/*
	 * Setzen der favorisierten Items als ArrayList
	 * @param favoriteitem
	 */

	public void setFavoriteitem(ArrayList<ListItem> favoriteitem) {
		this.favoriteitem = favoriteitem;
	}
	
	/**
	 * **************************************************************************************
	 * ABSCHNITT Ende: Getter und Setter der Attribute
	 * **************************************************************************************
	 */


	
	
	

}
