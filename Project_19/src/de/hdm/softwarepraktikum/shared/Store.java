package de.hdm.softwarepraktikum.shared;

public class Retailer extends BusinessObject{

	private static final long serialVersionUID = 1L;
	private int Retailer_ID;
	private String name;
	private String street;
	private int postcode;
	private String city;
	
	
	
	public int getRetailer_ID() {
		return Retailer_ID;
	}
	public void setRetailer_ID(int retailer_ID) {
		Retailer_ID = retailer_ID;
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
