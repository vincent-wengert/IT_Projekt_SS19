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
		      PreparedStatement stmt2 = con.prepareStatement(
			"INSERT INTO Favorites (Group_Group_ID, Item_Item_ID) VALUES (?, ?)",
						Statement.RETURN_GENERATED_KEYS);

				stmt2.setInt(1, g.getId());
				stmt2.setInt(2,i.getId());		
				stmt2.executeUpdate();
				System.out.println(stmt2);
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
			
			stmt.executeUpdate("DELETE FROM Favorites WHERE Item_Item_ID=" + i.getId() + " AND Group_Group_ID="+ g.getId());
			System.out.println(stmt);
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	public ArrayList<Item> findFavItems(Group g) {
		// TODO Auto-generated method stub
		
		ArrayList<Item> favItems = new ArrayList<Item>();
		
		Connection con = DBConnection.connection();
		
		
		 try {
		      Statement stmt = con.createStatement();

		      ResultSet rs = stmt.executeQuery("SELECT Favorites.Item_Item_ID, Item.Name FROM Favorites INNER JOIN Item ON Favorites.Item_Item_ID = Item.Item_ID WHERE Group_Group_ID =" + g.getId());

		      // F�r jeden Eintrag im Suchergebnis wird nun ein Item-Objekt erstellt.
		      while (rs.next()) {
		    	Item i = new Item();
		        i.setId(rs.getInt("Item_Item_ID"));
		        i.setName(rs.getString("Name"));
		      

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
	
	//Ueberpruefen ob Eintrag in Favoritentabelle
	
	public Boolean checkFav(Item i, Group g) {
		
		Connection con = DBConnection.connection();
		boolean favorite = false;
		
		 try {
		      Statement stmt = con.createStatement();
		      ResultSet rs = stmt.executeQuery("SELECT * FROM Favorites WHERE Group_Group_ID =" + g.getId() + " AND Item_Item_ID =" + i.getId());
		      
		      
		      if (rs.next()) {
		    	  favorite = true;
		      } 
		      else {
		    	  favorite = false;
		      }
		      
		    }
		    catch (SQLException e2) {
		      e2.printStackTrace();
		    }
		return favorite;
	
	}
	
	
	public boolean checkById(int item_id) {
		
		Connection con = DBConnection.connection();
		boolean favorite = false;
		
		 try {
		      Statement stmt = con.createStatement();
		      ResultSet rs = stmt.executeQuery("SELECT * FROM Favorites WHERE itemid =" + item_id);
		      
		      
		      if (rs.next()) {
		    	  favorite = true;
		      } 
		      else {
		    	  favorite = false;
		      }
		      
		    }
		    catch (SQLException e2) {
		      e2.printStackTrace();
		    }
		return favorite;
		
		
		
	}
	
	

}
