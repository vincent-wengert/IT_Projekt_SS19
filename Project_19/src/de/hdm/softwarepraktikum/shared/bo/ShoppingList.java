package de.hdm.softwarepraktikum.shared.bo;

import java.util.ArrayList;

public class ShoppingList extends BusinessObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	// Name der Einkaufsliste
	private String title;
	
	// Ersteller der Einkaufliste
	private int ownerID;
	
	// Gruppe der Einkaufliste
	private int groupID;

	/**
	   * Fremdschl�sselbeziehung zu einer Gruppe.
	   */
	
	ArrayList<ListItem> shoppinglist = new ArrayList<ListItem>(); //* ersetzen durch shareable objects*/

	public ShoppingList() {
		
	}
	
	// Konstruktor mit Parameterliste
	public ShoppingList(String title) {
		this.title = title;
	}
	
	/**
	 * **************************************************************************************
	 * ABSCHNITT Anfang: Getter und Setter der Attribute
	 * **************************************************************************************
	 */
	
	/*
	 * Auslesen der Name der Einkaufsliste
	 * @return title
	 */

    public String getTitle() {
        return title;
    }
    
    /*
     * Setzen der Einkausliste 
     * @param value
     */
    
    public void setTitle(String value) {
    	this.title = value;
    }

    /* 
     * Auslesen des Inhalts der ShoppingList (ArrayList von ListItems)
     * @return shoppingList
     */
    public ArrayList<ListItem> getShoppinglist() {
		return shoppinglist;
	}
    
    /*
     * Setzen von Inhalten einer shoppingList mit ArrayLists von ListItems
     * @param shoppinglist
     */

	public void setShoppinglist(ArrayList<ListItem> shoppinglist) {
		this.shoppinglist = shoppinglist;
	}


    /**
	 * Auslesen des Fremdschlüssels zu einem Gruppenmitglied
	 * @return groupID
	 */
	public int getGroupID() {
		return groupID;
	}

	/*
	 * Setzen des Fremdschlüssels zu einem Gruppenmitglied
	 * @param groupID
	 */

	public void setGroupID(int groupID) {
		this.groupID = groupID;
	}
	
	/*
	 * Auslesen des Fremdschlüssels zu einem Ersteller
	 * @return ownerID
	 */

	public int getOwnerID() {
		return ownerID;
	}
	
	/*
	 * Setzen eines Fremdschlüssels zu einem Ersteller
	 * @param ownerID
	 */

	public void setOwnerID(int ownerID) {
		this.ownerID = ownerID;

	}
	
	/**
	 * **************************************************************************************
	 * ABSCHNITT Ende: Getter und Setter der Attribute
	 * **************************************************************************************
	 */
	
}
