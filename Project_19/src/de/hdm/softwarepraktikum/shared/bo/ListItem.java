package de.hdm.softwarepraktikum.shared.bo;

import de.hdm.softwarepraktikum.shared.bo.ListItem.Unit;

public class ListItem extends BusinessObject{

	/**
	 * 
	 */
	private int tempID;
	private String name;
	private static final long serialVersionUID = 1L;
	private boolean isChecked = false;
	private double amount;
	private Item It;
	private Unit unit;
	private int buyerID;
	private int storeID;
	private int slID;
	private int grID;
	
	
	public ListItem(String name, Unit unit, double amount, int id) {
		this.name = name;
		this.unit = unit;
		this.amount = amount;
		this.tempID = id;
	}
	
	public ListItem() {
		// TODO Auto-generated constructor stub
	}
	
	public int getTempID() {
		return this.tempID;
	}
	
	public Unit getItemUnit (String unit) {
		if (unit == "L") {
			return Unit.L;
				}
		else if(unit == "KG") {
			return Unit.KG;
			} else {
		return Unit.ST;
			}
		}

	public enum Unit{
		KG, ST, L;
	}
	
	public Unit getUnit() {
		return unit;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
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
	
	public int getGrID() {
		return grID;
	}
	
	public void setGrID(int grID) {
		this.grID = grID;
	}
}