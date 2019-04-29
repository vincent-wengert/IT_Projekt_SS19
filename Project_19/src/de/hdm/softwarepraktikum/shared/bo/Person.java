package de.hdm.softwarepraktikum.shared.bo;

public class Person extends BusinessObject{
	
	private static long serialVersionUID = 1L;
	private String gmail;
	private ArrayList<Item> favoriteItems = new ArrayList<Item>();
	private String name;	
	public int person_ID; 
	
	/**
	*
	*	Konstruktor mit Superklasse 
	*
	*/
	public Person(String gmail, ArrayList<Item> favoriteItems, String name, int person_ID){
		//Wird später von Datenbank gesetzt?
		this.person_ID = -1;
		
		super(serialVersionUID, creationDate);
		
		this.gmail = gmail;
		this.favoriteItems = favoriteItems;
		this.name = name;
	}
	
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
	*	Gibt den Namen der Person zurueck.
	*
	*/
	public String getName(){
		return this.name;
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
	*	Gibt die person_ID der Person zurueck.
	*
	*/
	public int getPerson_ID(){
		return this.Person_ID;
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
	*	Setzt den Namen der Person.
	*
	*/
	public void setName(String name){
		this.name = name;
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
	*	Setzt die Person_ID der Person.
	*
	*/
	public void setPerson_ID(int person_ID){
		this.person_ID = person_ID;
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
