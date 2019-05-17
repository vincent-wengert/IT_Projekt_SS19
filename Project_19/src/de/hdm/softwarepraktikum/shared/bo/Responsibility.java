package de.hdm.softwarepraktikum.shared.bo;

public class Responsibility extends BusinessObject {
	
	private static final long serialVersionUID = 1L;
	private int buyerID;
	private int storeID;
	private int slID;
	
	
	public int getBuyerID() {
		return buyerID;
	}
	public void setBuyerID(int buyerID) {
		this.buyerID = buyerID;
	}
	public int getStoreID() {
		return storeID;
	}
	public void setStoreID(int storeID) {
		this.storeID = storeID;
	}
	public int getSlID() {
		return slID;
	}
	public void setSlID(int slID) {
		this.slID = slID;
	}
	
	

}
