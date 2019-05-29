package de.hdm.softwarepraktikum.server.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import de.hdm.softwarepraktikum.shared.bo.Item;
import de.hdm.softwarepraktikum.shared.bo.ListItem;
import de.hdm.softwarepraktikum.shared.bo.ShoppingList;

public class FavoriteItemMapper {
	
	/* 
	 * Speicherung der Instanz dieser Mapperklasse.
	 */
	
	private static FavoriteItemMapper favoriteItemMapper = null;
	
	/*
	 * Konstruktor ist geschÃ¼tzt, um weitere Instanzierung zu verhindern.
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
	
	public FavoriteItem insert(FavoriteItem fi) {
		
		Connection con = DBConnection.connection();
		
		
		try {
			
			Statement stmt = con.createStatement();
			
		/*
		 * Zunï¿½chst schauen wir nach, welches der momentan hï¿½chste
		 * Primï¿½rschlï¿½sselwert ist.
		 */
		ResultSet rs = stmt.executeQuery("SELECT MAX(FavoriteItem_ID) AS maxid " + "FROM FavoriteItem ");
		
		// Wenn wir etwas zurï¿½ckerhalten, kann dies nur einzeilig sein
		if (rs.next()) {
		/*
		 * i erhï¿½lt den bisher maximalen, nun um 1 inkrementierten
		 * Primï¿½rschlï¿½ssel.
		 */
		fi.setId(rs.getInt("maxid") + 1);
				
		stmt = con.createStatement();
						
		// Jetzt erst erfolgt die tatsï¿½chliche Einfï¿½geoperation
		stmt.executeUpdate("INSERT INTO favoriteItem (FavoriteItem_ID) " + "VALUES (" +fi.getId() + ",'");
		}
	} catch (SQLException e) {
		e.printStackTrace();
		System.out.println("connection");
	}
		
		/*
		 * Rï¿½ckgabe des evtl. korrigierten Items.
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
			
			stmt.executeUpdate("DELETE FROM groups " + "WHERE id=" + i.getId());
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	public ArrayList<Item> findFavItems(ShoppingList sl) {
		// TODO Auto-generated method stub
		
		Connection con = DBConnection.connection();
		ArrayList<Item> favItems = new ArrayList<Item>();
		
		 try {
		      Statement stmt = con.createStatement();

		      ResultSet rs = stmt.executeQuery("SELECT item FROM group "
		          + "WHERE groupid =" + sl.getGroupID());

		      // Für jeden Eintrag im Suchergebnis wird nun ein Item-Objekt erstellt.
		      while (rs.next()) {
		    	Item i = new Item();
		        i.setId(rs.getInt("id"));
		      

		        // Hinzufügen des neuen Items zum Ergebnisvektor
		        favItems.add(i);
		      }
		    }
		    catch (SQLException e2) {
		      e2.printStackTrace();
		    }

		    // Ergebnisvektor zurückgeben
		    return favItems;
		    
	}

}
