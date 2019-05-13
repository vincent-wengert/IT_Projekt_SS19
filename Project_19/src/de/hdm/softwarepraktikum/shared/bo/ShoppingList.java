package de.hdm.softwarepraktikum.shared.bo;

import java.util.ArrayList;

public class ShoppingList extends BusinessObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String title;
	
	/**
	   * Fremdschlüsselbeziehung zu einem Mitglied der Gruppe.
	   */
	private int memberID;
	
	
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
	public int getMemberID() {
		return memberID;
	}
    
	public void setMemberID(int memberID) {
		this.memberID = memberID;
		
	}
	
}
