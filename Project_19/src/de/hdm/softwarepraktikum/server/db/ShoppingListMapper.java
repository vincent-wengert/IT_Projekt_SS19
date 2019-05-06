package de.hdm.softwarepraktikum.server.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import de.hdm.softwarepraktikum.shared.bo.ShoppingList;

public class ShoppingListMapper {
	
	/* 
	 * Speicherung der Instanz dieser Mapperklasse.
	 */
	
	private static ShoppingListMapper shoppinglistMapper = null;
	
	/*
	 * Konstruktor ist geschützt, um weitere Instanzierung zu verhindern.
	 */

	protected ShoppingListMapper() {
		
	}
	
	/*
	 * Einhaltung der Singleton Eigenschaft des Mappers.
	 */
	
	public static ShoppingListMapper shoppinglistMapper() {
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
	 * Insert Methode, um eine neue sl der Datenbank hinzuzufuegen.
	 */
	
	public ShoppingList insert(ShoppingList sl) {
		
			Connection con = DBConnection.connection();

			try {
			Statement stmt = con.createStatement();
			
			/*
			 * Zun�chst schauen wir nach, welches der momentan h�chste
			 * Prim�rschl�sselwert ist.
			 */
			ResultSet rs = stmt.executeQuery("SELECT MAX(bo_id) AS maxbo_id " + "FROM shoppinglist ");
			
			// Wenn wir etwas zur�ckerhalten, kann dies nur einzeilig sein
			if (rs.next()) {
			/*
			 * sl erh�lt den bisher maximalen, nun um 1 inkrementierten
			 * Prim�rschl�ssel.
			 */
			g.setBO_ID(rs.getInt("maxbo_id") + 1);
					
			stmt = con.createStatement();
							
			// Jetzt erst erfolgt die tats�chliche Einf�geoperation
			stmt.executeUpdate("INSERT INTO shoppinglists (bo_id, title) " + "VALUES (" + sl.getBo_Id() + ",'"
					+ sl.getTitle() + "')");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		/*
		 * R�ckgabe, der evtl. korrigierten ShoppingList.
		 */
		return sl;
	}
	
	/*
	 * Update Methode, um eine SL erneut zu schreiben.
	 */
		public ShoppingList update(ShoppingList sl) {
		
		Connection con = DBConnection.connection();
		
		try {
			Statement stmt = con.createStatement();
			
			stmt.executeUpdate("UPDATE shoppinglists " + "SET title=\"" + sl.getTitle() + "\", "  + "WHERE bo_id=" + sl.getBO_ID());
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		// Um Analogie zu insert(ShoppingList sl) zu wahren, wird sl zur�ckgegeben
				return sl;
	}
		
		/*
		 * Delete Methode, um eine SL aus der Datenbank zu entfernen.
		 */
		
		public void delete(ShoppingList sl) {
			Connection con = DBConnection.connection();
			
			try {
				Statement stmt = con.createStatement();
				
				stmt.executeUpdate("DELETE FROM shoppinglists " + "WHERE bo_id=" + sl.getBO_ID());
			} catch(SQLException e) {
				e.printStackTrace();
			}
	}
		
		
	
	
	/*
	 * Eine Methode, um alle SL einer Person zu finden.
	 */
	
	public ArrayList<ShoppingList> findByPerson (Person p) {
		
	}

}
