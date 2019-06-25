package de.hdm.softwarepraktikum.shared.bo;

import java.util.Objects;

import org.eclipse.jdt.core.compiler.IScanner;

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
	private String unit;
	private int buyerID;
	private int storeID;
	private int slID;
	private int grID;
	private int ResID;
	
	
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
	
	public int getTempID() {
		return this.tempID;
	}
	
//	public Unit getItemUnit (String unit) {
//		if (Objects.equals(unit.trim(), "L")) {
//			return Unit.L;
//			}
//		else if(Objects.equals(unit.trim(), "KG")) {
//			return Unit.KG;
//			} 
//		else if (Objects.equals(unit.trim(), "ST")){
//			return Unit.ST;
//			} 
//		else if (Objects.equals(unit.trim(), "ML")){
//			return Unit.ML;	
//			}
//		else if (unit.trim() == null){
//			return null;
//		}
//		else {
//			System.out.println(unit);
//			return Unit.KG;
//		}
//	}

	public void setUnit(String unit){
		this.unit = unit;
	}
	
	public String  getUnit() {
		return unit;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
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