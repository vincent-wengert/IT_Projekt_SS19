package de.hdm.softwarepraktikum.server.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.ArrayList;

public class ItemMapper {
	
	
	/* 
	 * Speicherung der Instanz dieser Mapperklasse.
	 */
	
	private static ItemMapper articleMapper = null;
	
	/*
	 * Konstruktor ist geschützt, um weitere Instanzierung zu verhindern.
	 */

	protected ItemMapper() {
		
	}
	
	/*
	 * Einhaltung der Singleton Eigenschaft des Mappers.
	 */
	
	public static ItemMapper itemMapper() {
		if (articleMapper == null) {
			articleMapper = new ItemMapper();
		}

		return articleMapper;
	}
	
	/*
	 * Artikel anhand seiner Id zu suchen.
	 */
	
	public Item findById(int id) {
		
	}
	
	/*
	 * Methode zum Auflisten aller Artikel.
	 */
	
	public ArrayList<Item> findAllItems() {
		
	}
	
	/*
	 * Methode um einen Artikel anhand seines Objektes zu suchen.
	 */
	
	public Item findByObject(Item a) {
		
	}
	
	/*
	 * Delete Methode, um einen Artikel aus der Datenbank zu entfernen.
	 */
	
	public void delete(Item a) {
		
	}
	
	/*
	 * Update Methode, um einen Artikel erneut zu schreiben.
	 */
	
	public Item update(Item a) {
		
	}
	
	/*
	 * Insert Methode, um einen neuen Artikel der Datenbank hinzuzufügen.
	 */
	
	public Item insert(Item a) {
		
	}
	
	/*
	 * Eine Methode, um alle Artikel einer Person zu finden.
	 */
	
	public ArrayList<Item> findByPerson () {
		
	}

}
