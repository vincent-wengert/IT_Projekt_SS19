package de.hdm.softwarepraktikum.shared.bo;

import java.util.Objects;

import org.eclipse.jdt.core.compiler.IScanner;

public class ListItem extends BusinessObject{

	// Zähler für Favorite Items
	private static int count=0;
	
	// Temporäre ID
	private int tempID;
	
	// Name des ListItems
	private String name;
	
	private static final long serialVersionUID = 1L;
	
	// Eingekauft Status des ListItems
	private boolean isChecked = false;
	
	// Einzukaufende Menge des ListItems
	private double amount;
	
	// ID des ListItems
	private int itemId;
	
	// Einheit der einzukaufenden Menge des ListItems
	private String unit;
	
	// ID des verantwortlichen Einkäufers
	private int buyerID;
	
	// ID des Einkaufgeschäfts
	private int storeID;
	
	// ID der entsprechenden ShoppingList
	private int slID;
	
	// ID der entsprechenden Gruppe
	private int grID;
	
	// ID der Verantwortlichkeit
	private int ResID;
	
	
	// Konstruktur mit Parameterliste
	public ListItem(String name, String unit, double amount, Boolean isChecked) {
		this.name = name;
		this.unit = unit;
		this.amount = amount;
		this.tempID = count;
		this.isChecked = isChecked;
		count++;
	}

	public ListItem() {
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * **************************************************************************************
	 * ABSCHNITT Anfang: Getter und Setter der Attribute
	 * **************************************************************************************
	 */
	
	/*
	 * Auslesen der temporären ID
	 * @retur tempID
	 */
	public int getTempID() {
		return this.tempID;
	}
	
	/*
	 * Setzen der Einheit
	 * @param unit
	 */

	public void setUnit(String unit){
		this.unit = unit;
	}
	
	/**
	 * Auelsen der Einheit
	 * @return unit
	 */
	
	public String  getUnit() {
		return unit;
	}
	
	/*
	 * Auslesen des Namens
	 * @return name
	 */
	
	public String getName() {
		return this.name;
	}
	
	/*
	 * Setzen des Namens
	 * @param name
	 */
	
	public void setName(String name) {
		this.name = name;
	}
	
	/*
	 * Auslesen des Einkaufstatus
	 * @return isChecked
	 */
	
	public boolean getChecked() {
		return isChecked;
	}
	
	/*
	 * Setzen des Einkaufstatus
	 * @param isChecked
	 */
	public void setChecked(boolean isChecked) {
		this.isChecked = isChecked;
	}
	
	/*
	 * Auslesen der einzukaufenden Menge
	 * @return amount
	 */
	public double getAmount() {
		return amount;
	}
	
	/*
	 * Setzen der einzukaufenden Menge
	 * @param amount
	 */
	public void setAmount(double amount) {
		this.amount = amount;
	}
	
	/*
	 * Auslesen der ItemID
	 * @return itemID
	 */
	public int getItemId() {
		return itemId;
	}
	
	/*
	 * Setzen der ItemID
	 * @param itemID
	 */
	public void setItemId(int itemId) {
		this.itemId = itemId;
	}
	
	/*
	 * Auslesen der EinkäuferID
	 * @return buyerID
	 */

	public int getBuyerID() {
		return buyerID;
	}
	
	/*
	 * Setzen der BuyerID
	 * @param buyerID
	 */

	public void setBuyerID(int buyerID) {
		this.buyerID = buyerID;
	}
	
	/*
	 * Auslesen der storeID
	 * @return storeID
	 */

	public int getStoreID() {
		return storeID;
	}
	
	/*
	 * Setzen der StoreID
	 * @param storeID
	 */

	public void setStoreID(int storeID) {
		this.storeID = storeID;
	}
	
	/*
	 * Auslesen der ShoppingListID
	 * @return slID
	 */

	public int getSlID() {
		return slID;
	}
	
	/*
	 * Setzen der SlID
	 * @param slID
	 */

	public void setSlID(int slID) {
		this.slID = slID;
	}
	
	/*
	 * Auslesen der ID der entsprechenden Gruppe
	 * @return grID
	 */
	
	public int getGrID() {
		return grID;
	}
	
	/*
	 * Setzen der entsprechenden Gruppe
	 * @param grID
	 */
	
	public void setGrID(int grID) {
		this.grID = grID;
	}
	
	/*
	 * Setzen der VerantwortlichkeitsID
	 * @return ResID
	 */
	
	public int getResID() {
		return ResID;
	}
	
	/*
	 * Auslesen der VerantwortlichkeitsID
	 * @param ResID
	 */
	
	public void setResID(int ResID) {
		this.ResID = ResID;
	}
	
	/**
	 * **************************************************************************************
	 * ABSCHNITT Ende: Getter und Setter der Attribute
	 * **************************************************************************************
	 */
}