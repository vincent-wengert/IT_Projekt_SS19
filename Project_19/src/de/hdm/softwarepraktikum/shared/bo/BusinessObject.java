package de.hdm.softwarepraktikum.shared.bo;

import java.sql.Date;
import java.sql.Timestamp;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.rpc.IsSerializable;


/*
 * Die Kalsse stellt die Basisklasse dar, für alle in diesem Projekt
 * relevanten Klassen zur Umsetzung der fachlichen Vorgaben.
 */


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
    
    /**
    * **************************************************************************************
    * ABSCHNITT Anfang: Getter und Setter der Attribute
    * **************************************************************************************
    */

    	
    /*
     * Setzen des Erstelldatums
     */
        this.setCreationdate(new Timestamp(System.currentTimeMillis()));
        
        this.changedate=creationdate;
    	
    }
    
  
    /*
     * Auslesen der ID
     * @return Die ID wird zurückgegeben
     */
	
	public int getId() {
		return id;
	}
	
	/*
	 * Setzen der ID
	 */
	
	public void setId(int id) {
		this.id = id;
	}
	
	/*
	 * Auslesen des Erstelldatums
	 * @return Das Creationdate wird zurückgegeben
	 */
	
	
	 
	public Timestamp getCreationdate() {
		return this.creationdate;
	}
	
	
	
	/*
	 * Setzen des Erstelldatums
	 */
	
	
	public void setCreationdate(Timestamp creationdate) {
		this.creationdate = creationdate;
	}
	
	
	/*
	 * Auslesen des Änderungsdatums
	 * @return Das Changedate wird zurückgegeben
	 */
	
	public Timestamp getChangedate() {
		return this.changedate;
	}
	
	/*
	 * Setzen des Änderungsdatums
	 */
	
	public void setChangedate(Timestamp changedate) {
		this.changedate = changedate;
	}
	
	
	
	/*
	 * Rückgabe Name + ID als String
	 */
	public String toString() {
		return this.getClass().getName() + "#" + this.id;
	}
	
    /**
     * Variante der Methode getCreationDate, dabei wird das Datum allerdings verkürzt als String zurückgegeben.
     * 
     * @return Das Creationdate wird zurückgegeben
     */
    public String getCreationDateString() {
    	
//    	String creationDate = this.creationdate.toString().split("\\.")[0];
    	
    	Date date=new Date(creationdate.getTime());
    	DateTimeFormat fmt = DateTimeFormat.getFormat("dd.MM.yyyy, HH:mm 'Uhr'");
    	String test = fmt.format(date);
  
    	return test;
	}
    
    /**
     * Variante der Methode getChangeDate, dabei wird das Datum  verkürzt als String zurückgegeben.
     * 
     * @return Das Changedate wird zurückgegeben
     */
    public String getChangeDateString() {

//    	String changeDate = this.changedate.toString().split("\\.")[0];
    	
    	Date date=new Date(changedate.getTime());
    	DateTimeFormat fmt = DateTimeFormat.getFormat("dd.MM.yyyy, HH:mm 'Uhr'");
    	String test = fmt.format(date);
  
    	return test;
	}
    
	
	//Prüfen ob gleiches Objekt anhand der ID
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
	     * Wenn bislang keine Gleichheit bestimmt werden konnte, dann müssen
	     * schließlich false zurückgeben.
	     */
	    return false;
	}
	
	/**
	 * **************************************************************************************
	 * ABSCHNITT Ende: Getter und Setter der Attribute
	 * **************************************************************************************
	 */
	
}
