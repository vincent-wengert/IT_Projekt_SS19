package de.hdm.softwarepraktikum.shared.bo;

import java.util.ArrayList;

public class Person extends BusinessObject{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String gmail;
	private String name;	
	
	/**
	*
	* 	Leerer Konstruktor
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
	

}
