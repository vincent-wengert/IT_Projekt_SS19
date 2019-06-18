package de.hdm.softwarepraktikum.shared.bo;

import org.eclipse.jdt.core.compiler.IScanner;

import de.hdm.softwarepraktikum.shared.bo.ListItem.Unit;

public class ListItem extends BusinessObject{

	/**
	 * 
	 */
	private static int count=0;
	private int tempID;
	private String name;
	private static final long serialVersionUID = 1L;
	private boolean isChecked = false;
	private double amount;
	private int itemId;
	private Unit unit;
	private int buyerID;
	private int storeID;
	private int slID;
	private int grID;
	private int ResID;
	
	
	public ListItem(String name, Unit unit, double amount, Boolean isChecked) {
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
	
	public boolean getChecked() {
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
	
	public int getItemId() {
		return itemId;
	}
	public void setItemId(int itemId) {
		this.itemId = itemId;
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
	
	public int getResID() {
		return ResID;
	}
	
	public void setResID(int ResID) {
		this.ResID = ResID;
	}
}