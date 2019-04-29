package de.hdm.softwarepraktikum.server.db;

import java.sql.Connection;
import java.util.ArrayList;

import de.hdm.softwarepraktikum.shared.bo.shoppinglist;

public class Shoppinglistmapper {
	

	/* 
	 * Speicherung der Instanz dieser Mapperklasse.
	 */
	
	private static Shoppinglistmapper shoppinglistMapper = null;
	
	/*
	 * Konstruktor ist geschützt, um weitere Instanzierung zu verhindern.
	 */

	protected Shoppinglistmapper() {
		
	}
	
	/*
	 * Einhaltung der Singleton Eigenschaft des Mappers.
	 */
	
	public static Shoppinglistmapper shoppinglistMapper() {
		if (shoppinglistMapper == null) {
			shoppinglistMapper = new Shoppinglistmapper();
		}

		return shoppinglistMapper;
	}
	
	public shoppinglist findById(int id) {
		
	}
	
	/*
	 * Methode zum Auflisten aller Shoppinglists.
	 */
	
	public ArrayList<shoppinglist> findAllShoppinglists() {
		
	}
	
	/*
	 * Methode um eine shoppinglist anhand einer Gruppe zu finden.
	 */
	
	public shoppinglist findByGroup(Group g) {
		
	}
	
	/*
	 * Delete Methode, um eine SL aus der Datenbank zu entfernen.
	 */
	
	public void delete(shoppinglist sl) {
		
	}
	
	/*
	 * Update Methode, um eine SL erneut zu schreiben.
	 */
	
	public shoppinglist update(shoppinglist sl) {
		
	}
	
	/*
	 * Insert Methode, um eine neue sl der Datenbank hinzuzufügen.
	 */
	
	public shoppinglist insert(shoppinglist sl) {
		
	}
	
	/*
	 * Eine Methode, um alle SL einer Person zu finden.
	 */
	
	public ArrayList<shoppinglist> findByPerson (Person p) {
		
	}
}
