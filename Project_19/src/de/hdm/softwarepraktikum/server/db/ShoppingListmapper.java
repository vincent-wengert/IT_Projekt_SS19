package de.hdm.softwarepraktikum.server.db;

import java.sql.Connection;
import java.util.ArrayList;

import de.hdm.softwarepraktikum.shared.bo.shoppinglist;

public class ShoppingListmapper {
	

	/* 
	 * Speicherung der Instanz dieser Mapperklasse.
	 */
	
	private static ShoppingListmapper shoppinglistMapper = null;
	
	/*
	 * Konstruktor ist geschützt, um weitere Instanzierung zu verhindern.
	 */

	protected ShoppingListmapper() {
		
	}
	
	/*
	 * Einhaltung der Singleton Eigenschaft des Mappers.
	 */
	
	public static ShoppingListmapper shoppinglistMapper() {
		if (shoppinglistMapper == null) {
			shoppinglistMapper = new ShoppingListmapper();
		}

		return shoppinglistMapper;
	}
	
	public ShoppingList findById(int id) {
		
	}
	
	/*
	 * Methode zum Auflisten aller Shoppinglists.
	 */
	
	public ArrayList<ShoppingList> findAllShoppingLists() {
		
	}
	
	/*
	 * Methode um eine shoppinglist anhand einer Gruppe zu finden.
	 */
	
	public ShoppingList findByGroup(Group g) {
		
	}
	
	/*
	 * Delete Methode, um eine SL aus der Datenbank zu entfernen.
	 */
	
	public void delete(ShoppingList sl) {
		
	}
	
	/*
	 * Update Methode, um eine SL erneut zu schreiben.
	 */
	
	public ShoppingList update(ShoppingList sl) {
		
	}
	
	/*
	 * Insert Methode, um eine neue sl der Datenbank hinzuzufügen.
	 */
	
	public ShoppingList insert(ShoppingList sl) {
		
	}
	
	/*
	 * Eine Methode, um alle SL einer Person zu finden.
	 */
	
	public ArrayList<ShoppingList> findByPerson (Person p) {
		
	}
}
