package de.hdm.softwarepraktikum.shared.bo;

/*
 * Neben get und set Name/Unit wurden keine weiteren Methoden deklariert, da die Articleklasse von ihrer
 * Superklasse BusinessObject erbt, in der die nötigen Methoden bereits implementiert sind.
 */

public class Item extends BusinessObject {
	
	private static final long serialVersionUID = 1L;
	private String name;
	private boolean isGlobal;
	private boolean isFavorite;
	
	public String getName() {
		return name;
	}
	
	public void setName(String value) {
		this.name = value;
	}
	
	public Boolean getIsFavorite() {
		return isFavorite;
	}
	
	public void setFavorite(Boolean isFavorite) {
		this.isFavorite = isFavorite;
	}
	
	public boolean getIsGlobal() {
		return isGlobal;
	}
	
	public void setIsGlobal(boolean value) {
		this.isGlobal = value;
	}
}
