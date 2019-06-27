package de.hdm.softwarepraktikum.shared.bo;

public class Responsibility extends BusinessObject {
	
	private static final long serialVersionUID = 1L;
	
	// Einkäufer ID
	private int buyerID;
	
	// Einkaufsgeschäft ID
	private int storeID;
	
	// ShoppingList ID
	private int slID;
	
	/**
	 * **************************************************************************************
	 * ABSCHNITT Anfang: Getter und Setter der Attribute
	 * **************************************************************************************
	 */
	
	
	/*
	 * Auslesen der EinkäuferID
	 * @return buyerID
	 */
	public int getBuyerID() {
		return buyerID;
	}
	
	/*
	 * Setzen der EinkäuferID
	 * @param buyerID
	 */
	public void setBuyerID(int buyerID) {
		this.buyerID = buyerID;
	}
	
	/*
	 * Auslesen der StoreID
	 * @return storeID
	 */
	public int getStoreID() {
		return storeID;
	}
	
	/*
	 * Setzen der StoreID
	 * @param StoreID
	 */
	public void setStoreID(int storeID) {
		this.storeID = storeID;
	}
	
	/*
	 * Auslesen der ShoppigListID
	 * @return slID
	 */
	public int getSlID() {
		return slID;
	}
	
	/*
	 * Setzen der ShoppingListID
	 * @param ShoppingListID
	 */
	public void setSlID(int slID) {
		this.slID = slID;
	}
	
	/**
	 * **************************************************************************************
	 * ABSCHNITT Ende: Getter und Setter der Attribute
	 * **************************************************************************************
	 */

}
