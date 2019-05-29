package de.hdm.softwarepraktikum.shared.bo;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;


public abstract class BusinessObject implements Serializable{

	private static final long serialVersionUID = 1L;
	private int id = 0;
	private Timestamp creationdate;
	private Timestamp changedate;
	
    /**
     * Konstruktor des SharedBusinessObjects
     * Wird bei der Erzeugung eines neuen SharedBusinessObjects Objekts standardmäßig 
     * aufgerufen. Dabei wird ein Creationdate erzeugt und dem Objekt zugewiesen.
     */
    public BusinessObject () {
   
    this.setCreationdate(new Timestamp(System.currentTimeMillis()));
    
    this.changedate=creationdate;
    	
    }
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	
	public Timestamp getCreationdate() {
		return creationdate;
	}
	public void setCreationdate(Timestamp creationdate) {
		this.creationdate = creationdate;
	}
	
	
	public Timestamp getChangedate() {
		return changedate;
	}
	public void setChangedate(Timestamp changedate) {
		this.changedate = changedate;
	}
	// R�ckgabe Name + ID als String
	public String toString() {
		return this.getClass().getName() + "#" + this.id;
	}
	

	
	//Pr�fen ob gleiches Objekt anhand der ID
	public boolean equals(Object obj) {
		if (obj != null && obj instanceof BusinessObject) {
		      BusinessObject bo = (BusinessObject) obj;
		      try {
		        if (bo.getId() == this.id)
		          return true;
		      }
		      catch (IllegalArgumentException e) {
		       
		        return false;
		      }
		}
		/*
	     * Wenn bislang keine Gleichheit bestimmt werden konnte, dann m�ssen
	     * schlie�lich false zur�ckgeben.
	     */
	    return false;
	}
	
}
