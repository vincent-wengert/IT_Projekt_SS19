package de.hdm.softwarepraktikum.shared.bo;

public class ListItem extends BusinessObject{

	private boolean isChecked = false;
	private double amount;
	private Item It;
	private enum unit {KG, ST, L};
	
	
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
	
	
	
}