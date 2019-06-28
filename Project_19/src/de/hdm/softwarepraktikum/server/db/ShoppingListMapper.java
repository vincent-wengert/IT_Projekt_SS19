package de.hdm.softwarepraktikum.server.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.ArrayList;

import de.hdm.softwarepraktikum.shared.bo.Group;
import de.hdm.softwarepraktikum.shared.bo.ShoppingList;

public class ShoppingListMapper {
	
	/**
	   * Die Klasse ShoppingListMapper wird nur einmal instantiiert. Man spricht hierbei
	   * von einem sogenannten <b>Singleton</b>.
	   * <p>
	   * Diese Variable ist durch den Bezeichner <code>static</code> nur einmal fuer
	   * saemtliche eventuellen Instanzen dieser Klasse vorhanden. Sie speichert die
	   * einzige Instanz dieser Klasse.
	   * 
	   * @author Niklas Öxle
	   */
	
	
	private static ShoppingListMapper shoppinglistMapper = null;
	
	/**
	 * Geschuetzter Konstruktor - verhindert die Moeglichkeit, mit <code>new</code>
	 * neue Instanzen dieser Klasse zu erzeugen.
	 */
	
	
	protected ShoppingListMapper() {
		
	}
	
	/**
	 * Einhaltung der Singleton Eigenschaft des Mappers.
	 * @return: gibt den ShoppingListMapper zurück
	 */ 
	
	public static ShoppingListMapper shoppinglistMapper() {
		if (shoppinglistMapper == null) {
			shoppinglistMapper = new ShoppingListMapper();
		}

		return shoppinglistMapper;
	}
	
	/**
	 * Methode um ein einzelnes <code>ShoppingList</code> Objekt anhand einer ID zu suchen.
     * @param id:  ID der zu findenden ShoppingList wird übergeben.
     * @return Die anhand der id gefundene ShoppingList wird zurückgegeben.
	 */
	
	
	public ShoppingList findById(int id) {
		
		
		Connection con =DBConnection.connection();
		
		try {
			
			Statement stmt = con.createStatement();
			
			
			ResultSet rs = stmt.executeQuery("Select ShoppingList_ID, Title, ChangeDate, CreationDate FROM ShoppingList WHERE ShoppingList_ID=" + id);
			
			
			if (rs.next()) {
				
				ShoppingList sl = new ShoppingList();
				sl.setId(rs.getInt("ShoppingList_ID"));
				sl.setTitle(rs.getString("Title"));
				sl.setChangedate(rs.getTimestamp("ChangeDate"));
				sl.setCreationdate(rs.getTimestamp("CreationDate"));
				
				return sl; 		
		}
		}catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return null;
			}
	
	
	/** 
     * Methode um alle in der Datenbank vorhandenen ShopingList-Datensätze abzurufen.
     * Diese werden als einzelne <code>ShoppingList</code> Objekte innerhalb einer ArrayList zurückgegeben.
     * 
     * @return ArrayList aller ShoppingLists wird zurückgegeben.
     */
	
	
	public ArrayList<ShoppingList> findAllShoppingLists() {
		
		Connection con = DBConnection.connection();
		
		
		ArrayList<ShoppingList> result = new ArrayList<ShoppingList>();
		
		try {
			
			Statement stmt = con.createStatement();
			
			ResultSet rs = stmt.executeQuery("SELECT ShoppingList_ID, Title, " + "FROM ShoppingList " + "ORDER BY Title");
			
			
			
			while(rs.next()) {
				ShoppingList sl = new ShoppingList();
				sl.setId(rs.getInt("ShoppingList_ID"));
				sl.setTitle(rs.getString("Title"));
				
				
				
				result.add(sl);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		return result;
	}
	
	/** 
     * Methode um alle in der Datenbank vorhandenen ShopingList-Datensätze einer bestimmten Gruppe abzurufen.
     * Diese werden als einzelne <code>ShoppingList</code> Objekte innerhalb einer ArrayList zurückgegeben.
     * 
     * @param groupID : ID der Gruppe, welcher die ShoppingLists zugeordnet sind.
     * @return ArrayList aller ShoppingLists einer Gruppe wird zurückgegeben.
     */
	
	public ArrayList<ShoppingList> findByGroup(int groupID) {
		
		Connection con = DBConnection.connection();
	    ArrayList<ShoppingList> result = new ArrayList<ShoppingList>();

	    try {
	      Statement stmt = con.createStatement();

	      ResultSet rs = stmt.executeQuery("SELECT ShoppingList_ID, Title, CreationDate, ChangeDate FROM ShoppingList WHERE Group_ID = " + groupID);

	      
	      while (rs.next()) {
	    	ShoppingList sl = new ShoppingList();
	        sl.setId(rs.getInt("ShoppingList_ID"));
	        sl.setTitle(rs.getString("Title"));
	        sl.setCreationdate(rs.getTimestamp("CreationDate"));
	        sl.setChangedate(rs.getTimestamp("ChangeDate"));

	        
	        result.add(sl);
	      }
	    }
	    catch (SQLException e2) {
	      e2.printStackTrace();
	    }

	    
	    return result;
	}
	
	/** 
     * Methode um alle in der Datenbank vorhandenen ShopingList-Datensätze einer bestimmten Gruppe abzurufen.
     * Diese werden als einzelne <code>ShoppingList</code> Objekte innerhalb einer ArrayList zurückgegeben.
     * 
     * @param g :Gruppe, welcher die ShoppingLists zugeordnet sind.
     * @return findByGroup(int groupID)
     */
	
	public ArrayList<ShoppingList> findByGroup(Group g) {
		
		/*
	     * Wir lesen die id (Primaerschluessel) des Group-Objekts
	     * aus und delegieren die weitere Bearbeitung an findByGroup(int groupID).
	     */
		return findByGroup(g.getId());
	}
	
	
	

	/**
	 * Methode um ein ShoppingList Objekt in der Datenbank zu speichern
	 * @param sl : eine neue zu speichernde ShoppingList in der Datenbank wird uebergeben
	 * @return: die neu gespeicherte ShoppingList wird zurückgegeben
	 * 
	 */
	
	public ShoppingList insert(ShoppingList sl) {
		
Connection con = DBConnection.connection();
		
		try {
			 
			Statement stmt = con.createStatement();

			ResultSet rs = stmt.executeQuery("SELECT MAX(ShoppingList_ID) AS maxid " + "FROM ShoppingList");


		      if (rs.next()) {
		      
		      sl.setId(rs.getInt("maxid") + 1);

		      }
		      
		      PreparedStatement stmt2 = con.prepareStatement(
			"INSERT INTO ShoppingList (ShoppingList_ID,Creationdate,Changedate,Title,Group_ID) VALUES (?, ?, ?, ?, ?)",
						Statement.RETURN_GENERATED_KEYS);

				stmt2.setInt(1, sl.getId());
				stmt2.setTimestamp(2, sl.getCreationdate());
				stmt2.setTimestamp(3, sl.getChangedate());
				stmt2.setString(4, sl.getTitle());
				stmt2.setInt(5, sl.getGroupID());
				
				stmt2.executeUpdate();

    	  }
    	  catch(SQLException e) {
        		e.printStackTrace();
        	}	
				
        return sl;
	}
	
	  /**
	 * Wiederholtes Schreiben eines <code>ShppingList</code> Objekts in die Datenbank.
	 * @param sl : Die zu aktualisierende ShoppingList wird übergeben
	 * @return: die aktualisierte ShoppingList wird zurückgegeben
	 * 
	 */
	
		public ShoppingList update(ShoppingList sl) {
		
			Connection con = DBConnection.connection();
			
			try {
				
				PreparedStatement st = con.prepareStatement("UPDATE ShoppingList SET Title = ?, ChangeDate = ? WHERE ShoppingList_ID= ?");
				
				st.setString(1, sl.getTitle());
				st.setTimestamp(2, sl.getChangedate());
				st.setInt(3, sl.getId());
				
				System.out.println(st);
				st.executeUpdate();
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			
					return sl;
		        
		}
	
		
		/**
		 * Methode um ein ShoppingList-Datensatz in der Datenbank zu löschen.
		 * @param sl : Die zu löschende ShoppingList wird uebergeben
		 */
		
		public void delete(ShoppingList sl) {
			Connection con = DBConnection.connection();
			
			try {
				Statement stmt = con.createStatement();
				
				stmt.executeUpdate("DELETE FROM ShoppingList " + "WHERE ShoppingList_ID=" + sl.getId());

			} catch(SQLException e) {
				e.printStackTrace();
			}
	}
		
		
		}
