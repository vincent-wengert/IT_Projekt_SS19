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
	
	
	public Store(String name, Integer postcode, String city, String street, Integer housenumber) {
		this.name = name;
		this.postcode = postcode;
		this.city = city;
		this.street = street;
		this.houseNumber = housenumber;
	}
	
	public int getHouseNumber() {
		return this.houseNumber;
	}
	
	public void setHouseNumber(int i) {
		this.houseNumber = i;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}

	public int getPostcode() {
		return postcode;
	}
	public void setPostcode(int postcode) {
		this.postcode = postcode;
	}
	
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	
	
	
}
