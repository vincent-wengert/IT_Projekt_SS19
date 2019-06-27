package de.hdm.softwarepraktikum.shared.bo;

public class Store extends BusinessObject{

	private static final long serialVersionUID = 1L;
	private String name;
	private String street;
	private int postcode;
	private String city;
	private int houseNumber;
	
	public Store() {
		
	}
	
	// Konstruktor mit Parameterliste
	public Store(String name, Integer postcode, String city, String street, Integer housenumber) {
		this.name = name;
		this.postcode = postcode;
		this.city = city;
		this.street = street;
		this.houseNumber = housenumber;
	}
	
	/**
	 * **************************************************************************************
	 * ABSCHNITT Anfang: Getter und Setter der Attribute
	 * **************************************************************************************
	 */
	
	/*
	 * Auslesen der Hausnummer eines Ladens
	 * @return houseNumer
	 */
	
	public int getHouseNumber() {
		return this.houseNumber;
	}
	
	/*
	 * Setzen der Hausnummer eines Ladens
	 * @param i
	 */
	
	public void setHouseNumber(int i) {
		this.houseNumber = i;
	}
	
	/*
	 * Auslesen des Namens eines Ladens
	 * @return name
	 */
	
	public String getName() {
		return name;
	}
	
	/*
	 * Setzen eines Namens eines Ladens
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/*
	 * Auslesen einer Straße eines Ladens
	 * @return street
	 */
	
	public String getStreet() {
		return street;
	}
	
	/*
	 * Setzen einer Straße eines Ladens
	 * @param street
	 */
	public void setStreet(String street) {
		this.street = street;
	}
	
	/*
	 * Auslesen der Postleitzahl eines Ladens
	 * @return postcode
	 */

	public int getPostcode() {
		return postcode;
	}
	
	/*
	 * Setzen einer Postleitzahl eines Ladens
	 * @param postcode
	 */
	public void setPostcode(int postcode) {
		this.postcode = postcode;
	}
	
	/*
	 * Auslesen der Stadt eines Ladens
	 * @return city
	 */
	
	public String getCity() {
		return city;
	}
	
	/*
	 * Setzen einer Stadt eines Ladens
	 * @param city
	 */
	public void setCity(String city) {
		this.city = city;
	}
	
	/**
	 * **************************************************************************************
	 * ABSCHNITT Ende: Getter und Setter der Attribute
	 * **************************************************************************************
	 */
	
	
	
}
