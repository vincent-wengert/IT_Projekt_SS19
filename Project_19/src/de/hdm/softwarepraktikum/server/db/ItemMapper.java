package de.hdm.softwarepraktikum.server.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import de.hdm.softwarepraktikum.shared.bo.Group;
import de.hdm.softwarepraktikum.shared.bo.Item;
import de.hdm.softwarepraktikum.shared.bo.Person;

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
	 * Insert Methode, um einen neuen Artikel der Datenbank hinzuzufuegen.
	 */
	
	public Item insert(Item i) {
		
		Connection con = DBConnection.connection();
		
		try {
			Statement stmt = con.createStatement();
			
		/*
		 * Zun�chst schauen wir nach, welches der momentan h�chste
		 * Prim�rschl�sselwert ist.
		 */
		ResultSet rs = stmt.executeQuery("SELECT MAX(id) AS maxid " + "FROM item ");
		
		// Wenn wir etwas zur�ckerhalten, kann dies nur einzeilig sein
		if (rs.next()) {
		/*
		 * i erh�lt den bisher maximalen, nun um 1 inkrementierten
		 * Prim�rschl�ssel.
		 */
		i.setId(rs.getInt("maxid") + 1);
				
		stmt = con.createStatement();
						
		// Jetzt erst erfolgt die tats�chliche Einf�geoperation
		stmt.executeUpdate("INSERT INTO item (id, name, isglobal) " + "VALUES (" + i.getId() + ",'"
				+ i.getName() + "','" + i.getIsGlobal() + "')");
		}
	} catch (SQLException e) {
		e.printStackTrace();
	}
		
		/*
		 * R�ckgabe des evtl. korrigierten Items.
		 */
		return i;
	}
	
	
	/*
	 * Update Methode, um einen Artikel erneut zu schreiben.
	 */
	
	public Item update(Item i) {
		
		Connection con = DBConnection.connection();
		
		try {
			Statement stmt = con.createStatement();
			
			stmt.executeUpdate("UPDATE groups " + "SET name=\"" + i.getName() + "\", " + "globalid=\""
					+ i.getIsGlobal() + "\" " + "WHERE id=" + i.getId());
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		// Um Analogie zu insert(item i) zu wahren, wird i zur�ckgegeben
				return i;
	}
	
	
	/*
	 * Delete Methode, um einen Artikel aus der Datenbank zu entfernen.
	 */
	
	public void delete(Item i) {
		Connection con = DBConnection.connection();
		
		try {
			Statement stmt = con.createStatement();
			
			stmt.executeUpdate("DELETE FROM groups " + "WHERE id=" + i.getId());
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * Methode, um Artikel anhand ihrer ID zu suchen.
	 */
	
	public Item findById(int id) {
		
		//Herstellung einer Verbindung zur DB-Connection
				Connection con =DBConnection.connection();
				
		try {
			//leeres SQL-Statement (JDBC) anlegen
			Statement stmt = con.createStatement();
			
			//Statement ausf�llen und als Query an die DB schicken
			ResultSet rs = stmt.executeQuery("Select id, name, isglobal FROM item" + "WHERE id= " + id);
			
			/*
		     * Da id Prim�rschl�ssel ist, kann max. nur ein Tupel zur�ckgegeben
		     * werden. Pr�fe, ob ein Ergebnis vorliegt.
		     */
			if (rs.next()) {
				//Ergebnis-Tupel in Objekt umwandeln
				Item i = new Item();
				i.setId(rs.getInt("id"));
				i.setName(rs.getString("name"));
				i.setIsGlobal(rs.getBoolean("isglobal"));
				
				return i;		
	}
		}catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	return null;
	}
	
	/*
	 * Methode zum Auflisten aller Artikel.
	 */
	
	public ArrayList<Item> findAllItems() {
		//Ergebnisvektor vorbereiten
		ArrayList<Item> result = new ArrayList<Item>();
		
		Connection con = DBConnection.connection();
		try {
			
			Statement stmt = con.createStatement();
			
			ResultSet rs = stmt
					.executeQuery("SELECT id, name, isglobal " + "FROM item " + "ORDER BY name");
			
			// F�r jeden Eintrag im Suchergebnis wird nun ein Item-Objekt erstellt.
			while(rs.next()) {
				Item i = new Item();
				i.setId(rs.getInt("id"));
				i.setName(rs.getString("name"));
				i.setIsGlobal(rs.getBoolean("isglobal"));
				
				//Hinzuf�gen des neuen Objekts zum Ergebnisvektor
				result.add(i);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		//Ergebnis zur�ckgeben
		return result;
	}
	
	/*
	 * Methode um einen Artikel anhand seines Objektes zu suchen.
	 */
	
	public Item findByObject(Item i) {
		
	}
	
	
	
	
	
	
	
	/*
	 * Eine Methode, um alle Artikel einer Person zu finden.
	 */
	
	public ArrayList<Item> findByPerson () {
		
	}

}
