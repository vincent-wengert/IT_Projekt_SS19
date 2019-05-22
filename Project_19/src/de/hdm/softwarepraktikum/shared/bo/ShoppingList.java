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
	   * Fremdschlüsselbeziehung zu einer Gruppe.
	   */

	
	
	ArrayList<String> shoppinglist = new ArrayList<String>(); //* ersetzen durch shareable objects*/
	
	
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String value) {
    	this.title = value;
    }

    /**
	 * Auslesen des Fremdschlüssels zu einem Gruppenmitglied
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
