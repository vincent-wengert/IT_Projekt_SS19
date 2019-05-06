package de.hdm.softwarepraktikum.shared.bo;

import java.util.ArrayList;

public class ShoppingList {

/** extends muss noch hinzugefügt werden*/
	
	private String title;
	
	ArrayList<String> shoppinglist = new ArrayList<String>(); //* ersetzen durch shareable objects*/
	
	
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String value) {
    	this.title = value;
    }
	
}
