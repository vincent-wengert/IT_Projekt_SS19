package de.hdm.softwarepraktikum.shared.bo;

public class Responsibility {
	
	private static final long serialVersionUID = 1L;
	private Person buyer;
	private Store st;
	private ShoppingList sl;
	
	public Person getBuyer() {
		return buyer;
	}
	
	public void setBuyer(Person value) {
		this.buyer = value;
	}
	
	public Store getSt() {
		return st;
	}
	
	public void setSt(Store value) {
		this.st = value;
	}
	
	public Shoppinglist getSl() {
		return sl;
	}
	
	public void setSl(ShoppingList value) {
		this.sl = value;
	}

}
