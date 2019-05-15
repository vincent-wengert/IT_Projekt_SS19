package de.hdm.softwarepraktikum.server.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import de.hdm.softwarepraktikum.shared.bo.Item;

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
		
Connection con = DBConnection.connection();
    	
    	try {
			 
			Statement stmt = con.createStatement();

			ResultSet rs = stmt.executeQuery("SELECT MAX(Item_ID) AS maxid " + "FROM Item");


		      if (rs.next()) {
		      
		      a.setBO_ID(rs.getInt("maxid") + 1);

		      }

				
			    PreparedStatement stmt2 = con.prepareStatement(

							"INSERT INTO Property (Property_ID, Term, Deletable) VALUES (?, ?, ?)",

							Statement.RETURN_GENERATED_KEYS);

					stmt2.setInt(1, p.getBO_ID());
					stmt2.setString(2, p.getTerm());
					stmt2.setBoolean(3, p.isDeletable());
					stmt2.executeUpdate();
			
    	  }
    	  catch(SQLException e) {
        		e.printStackTrace();
        	}	
				
        return p;
    }
		
	
	
	/*
	 * Eine Methode, um alle Artikel einer Person zu finden.
	 */
	
	public ArrayList<Item> findByPerson () {
		
	}

}
