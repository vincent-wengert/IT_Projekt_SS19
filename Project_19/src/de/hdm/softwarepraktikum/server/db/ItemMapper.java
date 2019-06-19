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
import de.hdm.softwarepraktikum.shared.bo.Store;

import java.util.ArrayList;

public class ItemMapper {
	
	
	/* 
	 * Speicherung der Instanz dieser Mapperklasse.
	 */
	
	private static ItemMapper itemMapper = null;
	
	/*
	 * Konstruktor ist geschützt, um weitere Instanzierung zu verhindern.
	 */

	protected ItemMapper() {
		
	}
	
	/*
	 * Einhaltung der Singleton Eigenschaft des Mappers.
	 */
	
	public static ItemMapper itemMapper() {
		if (itemMapper == null) {
			itemMapper = new ItemMapper();
		}

		return itemMapper;
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
			
			
		ResultSet rs = stmt.executeQuery("SELECT MAX(Item_ID) AS maxid " + "FROM Item");
		
		// Wenn wir etwas zur�ckerhalten, kann dies nur einzeilig sein
		if (rs.next()) {
		/*
		 * i erh�lt den bisher maximalen, nun um 1 inkrementierten
		 * Prim�rschl�ssel.
		 */
		i.setId(rs.getInt("maxid") + 1);
				
		PreparedStatement stmt2 = con.prepareStatement(
				"INSERT INTO Item (Item_ID, Name, Creationdate, Changedate, Owner_ID, IsGlobal) " + "VALUES (?, ?, ?, ?, ?, ?)",
				Statement.RETURN_GENERATED_KEYS);
		
		stmt2.setInt(1, i.getId());
		stmt2.setString(2, i.getName());
		stmt2.setTimestamp(3, i.getCreationdate());
		stmt2.setTimestamp(4, i.getChangedate());
		//Group ID hinzufugen
		stmt2.setInt(5, 1);
		stmt2.setBoolean(6, i.getIsGlobal());
		System.out.println(stmt2);
		stmt2.executeUpdate();
		
		}
	} catch (SQLException e) {
		e.printStackTrace();
		
	}
		
		/*
		 * R�ckgabe des evtl. korrigierten Items.
		 */
		return i;
	}
	
	
	//Eigenschaften eines Stores aendern
			public Item update(Item i) {
				
				Connection con = DBConnection.connection();
				try {
					PreparedStatement st = con.prepareStatement("UPDATE Item SET Name = ?, Creationdate = ?, Changedate = ?, IsGlobal = ? WHERE Item_ID = ?");
					
					st.setString(1, i.getName());
					st.setTimestamp(2, i.getCreationdate());
					st.setTimestamp(3, i.getChangedate());
					st.setBoolean(4,  i.getIsGlobal());
					st.setInt(5, i.getId());
					st.executeUpdate();
					
				} catch (SQLException e) {
					e.printStackTrace();
				}
				
				// Um Analogie zu insert(item i) zu wahren, wird i zurï¿½ckgegeben
						return i;
			}
	
	
	/*
	 * Delete Methode, um einen Artikel aus der Datenbank zu entfernen.
	 */
	
	public void delete(Item i) {
		Connection con = DBConnection.connection();
		
		try {
			Statement stmt = con.createStatement();
			
			stmt.executeUpdate("DELETE FROM Item " + "WHERE Item_ID=" + i.getId());
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
	
	/**
	 * Auslesen aller Items
	 * @return Eine Arraylist mit Item-Objekten, die alle Produkte darstellt.
	 */
	
	public ArrayList<Item> findAll() {
		//Ergebnisvektor vorbereiten
		ArrayList<Item> result = new ArrayList<Item>();
		
		// Hier noch Person_ID einfügen
		int id = 1;
		
		Connection con = DBConnection.connection();
		try {
			
			Statement stmt = con.createStatement();
			
			ResultSet rs = stmt
					.executeQuery("SELECT * FROM Item WHERE Item.IsGlobal = true  OR Item.IsGlobal = false AND Item.Owner_ID=" + id);
			
			// F�r jeden Eintrag im Suchergebnis wird nun ein Item-Objekt erstellt.
			while(rs.next()) {
				Item i = new Item();
				i.setId(rs.getInt("Item_ID"));
				i.setName(rs.getString("Name"));
				i.setIsGlobal(rs.getBoolean("isGlobal"));
				
				//Hinzuf�gen des neuen Objekts zum Ergebnisvektor
				result.add(i);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		//Ergebnis zur�ckgeben
		return result;
	}
	
	
}
