package de.hdm.softwarepraktikum.shared.bo;

public class ListItem extends BusinessObject{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean isChecked = false;
	private double amount;
	private Item It;
	private Unit unit;
	private int buyerID;
	private int storeID;
	private int slID;
	
	
	public enum Unit{
		KG, ST, L;
	}
	
	public Unit getUnit() {
		return unit;
	}
	
	public void setUnit(Unit input) {
		unit = input;
	}
	
	public boolean isChecked() {
		return isChecked;
	}
	public void setChecked(boolean isChecked) {
		this.isChecked = isChecked;
	}
	
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	
	public Item getIt() {
		return It;
	}
	public void setIt(Item it) {
		this.It = it;
	}

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