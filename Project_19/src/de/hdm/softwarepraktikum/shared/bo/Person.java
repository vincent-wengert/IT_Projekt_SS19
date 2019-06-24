package de.hdm.softwarepraktikum.shared.bo;

import java.io.Serializable;

import com.google.gwt.user.client.rpc.IsSerializable;

public class Person extends BusinessObject{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String gmail;
	private String name;	
	private boolean isLoggedIn=false;
	private String loginUrl;
	private String logoutUrl;
	
	/**
	*
	* 	Default Konstruktor
	*
	*/
	public Person(){
	}
	
	
	/**
	*
	*	Gibt die Gmail der Person zurueck.
	*
	*/
	public String getGmail(){
		return this.gmail;
	}
	
	/**
	*
	*	Setzt die Gmail der Person.
	*
	*/
	public void setGmail(String gmail){
		this.gmail = gmail;
	}
	
	/**
	*
	*	Gibt den Namen der Person zurueck.
	*
	*/
	public String getName(){
		return this.name;
	}
	
	/**
	*
	*	Setzt den Namen der Person.
	*
	*/
	public void setName(String name){
		this.name = name;
	}
	
	   /**
     * Statusüberprüfung ob User eingelogt ist
     * 
     * @return false ist User nicht eingelogt, anderfalls ist User eingelogt
     */
	public boolean isLoggedIn() {
		return isLoggedIn;
	}

	
	/**
	 * Status festsetzen ob User eingelogt ist
	 * 
	 * @param isLoggedIn false User als nicht-eingelogt. 
	 */
	public void setLoggedIn(boolean isLoggedIn) {
		this.isLoggedIn = isLoggedIn;
	}
	
	
	 /**
     * Auslesen des LoginURL
     * 
     * @return LoginURL wird zurückgegeben. 
     */
    public String getLoginUrl() {
        return loginUrl;
    }

    
    /**
     * Setzt die loginUrl als String 
     * 
     * @param loginUrl wird zurückgegeben. 
     */
    public void setLoginUrl(String loginUrl) {
       
    	 this.loginUrl = loginUrl;
    }

    
    /**
     * Auslesen des LogoutURL
     * 
     * @return LogoutURL wird zurückgegeben
     */
    public String getLogoutUrl() {
        return logoutUrl;
    }

    
    /**
     * Setzt die LogoutURL
     * 
     * @param logoutUrl Die LogoutUrl wird übergeben. 
     */
    public void setLogoutUrl(String logoutUrl) {
   	 this.logoutUrl = logoutUrl;
    }
    
	

}
