package de.hdm.softwarepraktikum.shared.bo;

import java.sql.Timestamp;
import java.util.Date;

import com.google.gwt.user.client.rpc.IsSerializable;


public abstract class BusinessObject implements IsSerializable{

	private static final long serialVersionUID = 1L;
	private int id;
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
		return this.creationdate;
	}
	public void setCreationdate(Timestamp creationdate) {
		this.creationdate = creationdate;
	}
	
	
	public Timestamp getChangedate() {
		return this.changedate;
	}
	public void setChangedate(Timestamp changedate) {
		this.changedate = changedate;
	}
	// R�ckgabe Name + ID als String
	public String toString() {
		return this.getClass().getName() + "#" + this.id;
	}
	
    /**
     * Variante der Methode getCreationDate, dabei wird das Datum allerdings verkürzt als String zurückgegeben.
     * 
     * @return Das Creationdate wird zurückgegeben
     */
    public String getCreationDateString() {
    	String creationDate = this.creationdate.toString().split("\\.")[0];
    	return creationDate;
	}
    
    /**
     * Variante der Methode getChangeDate, dabei wird das Datum  verkürzt als String zurückgegeben.
     * 
     * @return Das Changedate wird zurückgegeben
     */
    public String getChangeDateString() {
    	String changeDate = this.changedate.toString().split("\\.")[0];
    	return changeDate;
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
