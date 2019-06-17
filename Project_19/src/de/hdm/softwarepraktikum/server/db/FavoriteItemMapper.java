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

public class FavoriteItemMapper {
	
	/* 
	 * Speicherung der Instanz dieser Mapperklasse.
	 */
	
	private static FavoriteItemMapper favoriteItemMapper = null;
	
	/*
	 * Konstruktor ist geschützt, um weitere Instanzierung zu verhindern.
	 */

	protected FavoriteItemMapper() {
		
	}
	
	/*
	 * Einhaltung der Singleton Eigenschaft des Mappers.
	 */
	
	public static FavoriteItemMapper favoriteItemMapper() {
		if (favoriteItemMapper == null) {
			favoriteItemMapper = new FavoriteItemMapper();
		}

		return favoriteItemMapper;
	}
	
	/*
	 * Insert Methode, um einen neuen Artikel der Datenbank hinzuzufuegen.
	 */
	
	public void insert(Item i, Group g) {
		
Connection con = DBConnection.connection();
		

		try {

			Statement stmt = con.createStatement();
			stmt.executeUpdate("INSERT INTO FavoriteItems (itemID, personID, groupID) " + "VALUES (" + i.getId() + ",'"
							 + g.getId() + "')");		
			
		}
		catch (SQLException e) {
			e.printStackTrace();

		}
		
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
	
	public void delete(Item i, Group g) {
		Connection con = DBConnection.connection();
		
		try {
			Statement stmt = con.createStatement();
			
			stmt.executeUpdate("DELETE FROM FavoriteItems " + "WHERE item_id=" + i.getId() + "AND group_id="+ g.getId());
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	public ArrayList<Item> findFavItems(Group g) {
		// TODO Auto-generated method stub
		
		Connection con = DBConnection.connection();
		ArrayList<Item> favItems = new ArrayList<Item>();
		
		 try {
		      Statement stmt = con.createStatement();

		      ResultSet rs = stmt.executeQuery("SELECT FavoriteItem.item_id, Item.name FROM FavoriteItem INNER JOIN item ON FavoriteItem.item_id = item.item_id WHERE groupid =" + g.getId());

		      // F�r jeden Eintrag im Suchergebnis wird nun ein Item-Objekt erstellt.
		      while (rs.next()) {
		    	Item i = new Item();
		        i.setId(rs.getInt("id"));
		        i.setName(rs.getString("name"));
		      

		        // Hinzuf�gen des neuen Items zum Ergebnisvektor
		        favItems.add(i);
		      }
		    }
		    catch (SQLException e2) {
		      e2.printStackTrace();
		    }

		    // Ergebnisvektor zur�ckgeben
		    return favItems;
		    
	}

}
