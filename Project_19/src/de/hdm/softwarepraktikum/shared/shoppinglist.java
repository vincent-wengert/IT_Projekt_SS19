package de.hdm.softwarepraktikum.shared;

import java.util.ArrayList;

public class shoppinglist {

/** extends muss noch hinzugefügt werden*/
	
	private String title;
	private int ID;
	
	ArrayList<String> shoppinglist = new ArrayList<String>(); //* ersetzen durch shareable objects*/
	
	
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String value) {
    	this.title = value;
    }
	
}
