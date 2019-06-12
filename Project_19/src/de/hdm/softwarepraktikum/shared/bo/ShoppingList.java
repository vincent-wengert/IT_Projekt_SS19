package de.hdm.softwarepraktikum.shared.bo;

import java.util.ArrayList;

public class ShoppingList extends BusinessObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String title;
	private int ownerID;
	private int groupID;

	/**
	   * Fremdschl�sselbeziehung zu einer Gruppe.
	   */
	
	ArrayList<ListItem> shoppinglist = new ArrayList<ListItem>(); //* ersetzen durch shareable objects*/

	public ShoppingList() {
		
	}
	
	
	public ShoppingList(String title) {
		this.title = title;
	}

    public String getTitle() {
        return title;
    }
    
    public void setTitle(String value) {
    	this.title = value;
    }


    public ArrayList<ListItem> getShoppinglist() {
		return shoppinglist;
	}

	public void setShoppinglist(ArrayList<ListItem> shoppinglist) {
		this.shoppinglist = shoppinglist;
	}


    /**
	 * Auslesen des Fremdschl�ssels zu einem Gruppenmitglied
	 * @return memberID
	 */
	public int getGroupID() {
		return groupID;
	}


	public void setGroupID(int groupID) {
		this.groupID = groupID;
	}

	public int getOwnerID() {
		return ownerID;
	}

	public void setOwnerID(int ownerID) {
		this.ownerID = ownerID;

	}
	
}
