package de.hdm.softwarepraktikum.shared.bo;

/*
 * Neben get und set Name/Unit wurden keine weiteren Methoden deklariert, da die Articleklasse von ihrer
 * Superklasse BusinessObject erbt, in der die nötigen Methoden bereits implementiert sind.
 */

public class Item extends BusinessObject {
	
	private static final long serialVersionUID = 1L;
	
	// Name des Items
	private String name;
	
	// Globale Sichtbarkeit des Items
	private boolean isGlobal;
	
	// Id des Erstellers des Items
	private int ownerID;
	
	// Favorite Status des
	private boolean isFavorite;
	
	/**
	 * **************************************************************************************
	 * ABSCHNITT Anfang: Getter und Setter der Attribute
	 * **************************************************************************************
	 */
	
	/*
	 * Auslesen des Namens des Items
	 * @return name wird zurückgegeben
	 */
	
	public String getName() {
		return name;
	}
	
	/*
	 * Setzen des Namens des Items
	 * @param value
	 */
	
	public void setName(String value) {
		this.name = value;
	}
	
	/*
	 * Auslesen des FavoritenStatus des Items
	 * @return isFavorite;
	 */
	
	public Boolean getIsFavorite() {
		return isFavorite;
	}
	
	/*
	 * Setzen des FavoriteStatus des Items
	 * @param isFavorite
	 */
	
	public void setFavorite(Boolean isFavorite) {
		this.isFavorite = isFavorite;
	}
	
	/*
	 * Auslesen der globalen Sichtbarkeit des ITems
	 * @return isGlobal
	 */
	
	public boolean getIsGlobal() {
		return isGlobal;
	}
	
	/*
	 * Setzen der globalen Sichtbarkeit des Items
	 * @param value
	 */
	
	public void setIsGlobal(boolean value) {
		this.isGlobal = value;
	}
	
	/*
	 * Auslesen der ID des Erstellers
	 * @return ownerID
	 */
	
	public int getOwnerID() {
		return ownerID;
	}
	
	/*
	 * Setzen der ID des Erstellers
	 * @üaram ownerID
	 */
	
	public void setOwnerID(int ownerID) {
		this.ownerID = ownerID;
	}
	
	/**
	 * **************************************************************************************
	 * ABSCHNITT Ende: Getter und Setter der Attribute
	 * **************************************************************************************
	 */
}
