package de.hdm.softwarepraktikum.shared.bo;

import java.util.ArrayList;

public class Person extends BusinessObject{
	
	private static long serialVersionUID = 1L;
	private String gmail;
	private ArrayList<Item> favoriteItems = new ArrayList<Item>();
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
	*	Gibt die favoriteItems der Person zurueck.
	*
	*/
	public ArrayList<Item> getFavoriteItems(){
		return this.favoriteItems;
	}

	/**
	*
	*	Setzt die favoriteItems der Person.
	*
	*/
	public void setFavoriteItems(ArrayList<Item> favoriteItems){
		this.favoriteItems = favoriteItems;
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
	*
	*	Fuegt einen Artikel der favoriteItems List hinzu.
	*
	*/
	public void addItem(Item a){
		favoriteItems.add(a);
	}
}
