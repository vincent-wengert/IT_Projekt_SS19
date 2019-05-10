package de.hdm.softwarepraktikum.shared.bo;

import java.io.Serializable;
import java.util.Date;


public abstract class BusinessObject implements Serializable{

	private static final long serialVersionUID = 1L;
	private int id = 0;
	private Date creationdate;
	
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	
	public Date getCreationdate() {
		return creationdate;
	}
	public void setCreationdate(Date creationdate) {
		this.creationdate = creationdate;
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
