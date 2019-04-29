package de.hdm.softwarepraktikum.shared.bo;

public class Store extends BusinessObject{

	private static final long serialVersionUID = 1L;
	private int Store_ID;
	private String name;
	private String street;
	private int postcode;
	private String city;
	
	
	
	public int getStore_ID() {
		return Store_ID;
	}
	public void setStore_ID(int store_ID) {
		Store_ID = store_ID;
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
