package de.hdm.softwarepraktikum.server.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;

import de.hdm.softwarepraktikum.shared.bo.Group;
import de.hdm.softwarepraktikum.shared.bo.Item;
import de.hdm.softwarepraktikum.shared.bo.Person;
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
			shoppinglistMapper = new ShoppingListMapper();
		}

		return shoppinglistMapper;
	}
	
	/**
	 * Finden einer ShoppingList anhand ihrer id.
	 * @param id
	 * @return ShoppingList-Objekt
	 */
	public ShoppingList findById(int id) {
		
		//Herstellung einer Verbindung zur DB-Connection
		Connection con =DBConnection.connection();
		
		try {
			//leeres SQL-Statement (JDBC) anlegen
			Statement stmt = con.createStatement();
			
			//Statement ausf�llen und als Query an die DB schicken
			ResultSet rs = stmt.executeQuery("Select Shopinglist_id, Title FROM Shoppinglist" + "WHERE Shoppinglist_id= " + id);
			
			/*
		     * Da id Prim�rschl�ssel ist, kann max. nur ein Tupel zur�ckgegeben
		     * werden. Pr�fe, ob ein Ergebnis vorliegt.
		     */
			if (rs.next()) {
				//Ergebnis-Tupel in Objekt umwandeln
				ShoppingList sl = new ShoppingList();
				sl.setId(rs.getInt("Shoppinglist_id"));
				sl.setTitle(rs.getString("Title"));
				
				return sl; 		
		}
		}catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return null;
			}
	
	
	/**
	 * Methode, um alle ShoppingLists auszugeben.
	 * @return ShoppingList-Objekte
	 */
	public ArrayList<ShoppingList> findAllShoppingLists() {
		
		Connection con = DBConnection.connection();
		
		//Ergebnisvektor vorbereiten
		ArrayList<ShoppingList> result = new ArrayList<ShoppingList>();
		
		try {
			
			Statement stmt = con.createStatement();
			
			ResultSet rs = stmt
					.executeQuery("SELECT id, title, " + "FROM shoppinglist " + "ORDER BY title");
			
			// F�r jeden Eintrag im Suchergebnis wird nun ein ShoppingList-Objekt erstellt.
			while(rs.next()) {
				ShoppingList sl = new ShoppingList();
				sl.setId(rs.getInt("id"));
				sl.setTitle(rs.getString("title"));
				
				
				//Hinzuf�gen des neuen Objekts zum Ergebnisvektor
				result.add(sl);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		//Ergebnis zur�ckgeben
		return result;
	}
	
	/*
	 * Methode, um alle Shoppinglists einer Gruppe zu finden.
	 */
	
	public ArrayList<ShoppingList> findByGroup(int groupID) {
		Connection con = DBConnection.connection();
	    ArrayList<ShoppingList> result = new ArrayList<ShoppingList>();

	    try {
	      Statement stmt = con.createStatement();

	      ResultSet rs = stmt.executeQuery("SELECT id, FROM group "
	          + "WHERE GroupID=" + groupID);

	      // F�r jeden Eintrag im Suchergebnis wird nun ein Group-Objekt erstellt.
	      while (rs.next()) {
	    	ShoppingList sl = new ShoppingList();
	        sl.setId(rs.getInt("id"));

	        // Hinzuf�gen des neuen Objekts zum Ergebnisvektor
	        result.add(sl);
	      }
	    }
	    catch (SQLException e2) {
	      e2.printStackTrace();
	    }

	    // Ergebnisvektor zur�ckgeben
	    return result;
	}
	
	/**
	 * Auslesen aller SL einer Group (durch <code>Group</code>-Objekt 
	 * gegeben).
	 * @param g Groupobjekt, dessen SL ausgelesen werden sollen.
	 * @return alle SL der Person
	 */
	
	public ArrayList<ShoppingList> findByGroup(Group g) {
		
		/*
	     * Wir lesen die id (Prim�rschl�ssel) des Group-Objekts
	     * aus und delegieren die weitere Bearbeitung an findByGroup(int groupID).
	     */
		return findByGroup(g.getId());
	}
	
	
	

	/**
	 * InsertMethode, um ein SL Objekt in der DB anzulegen.
	 * @param sl
	 * @return
	 */
	
	public ShoppingList insert(ShoppingList sl) {
		
Connection con = DBConnection.connection();
		
		try {
			 
			Statement stmt = con.createStatement();

			ResultSet rs = stmt.executeQuery("SELECT MAX(Shoppinglist_ID) AS maxid " + "FROM Shoppinglist");


		      if (rs.next()) {
		      
		      sl.setId(rs.getInt("maxid") + 1);

		      }
		      
		      PreparedStatement stmt2 = con.prepareStatement(
			"INSERT INTO Shoppinglist (Shoppinglist_ID,Creationdate,Changedate,Title,Group_ID) VALUES (?, ?, ?, ?, ?)",
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
	
	/*
	 * Update Methode, um eine SL erneut zu schreiben.
	 */
		public ShoppingList update(ShoppingList sl) {
		
			Connection con = DBConnection.connection();
			
			try {
				Statement stmt = con.createStatement();
				
				stmt.executeUpdate("UPDATE Shoppinglist " + "SET Title=\"" +sl.getTitle() + "\", " + "Group_ID=\""
						+ sl.getGroupID() + "\", " +"SET Changedate=\"" +sl.getChangedate() + "\" "+ "WHERE Shoppinglist_id=" + sl.getId());
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			// Um Analogie zu insert(Shoppinglist sl) zu wahren, wirdsl zur�ckgegeben
					return sl;
		        
		}
	
		
		/*
		 * Delete Methode, um eine SL aus der Datenbank zu entfernen.
		 */
		
		public void delete(ShoppingList sl) {
			Connection con = DBConnection.connection();
			
			try {
				Statement stmt = con.createStatement();
				
				stmt.executeUpdate("DELETE * FROM Shoppinglist " + "WHERE Shoppinglist_id=" + sl.getId());
			} catch(SQLException e) {
				e.printStackTrace();
			}
	}
		
		
	
	
	/*
	 * Eine Methode, um alle SL einer Person zu finden.
	 */
	
		public ArrayList<ShoppingList> findByMember(int memberID) {
		    Connection con = DBConnection.connection();
		    ArrayList<ShoppingList> result = new ArrayList<ShoppingList>();

		    try {
		      Statement stmt = con.createStatement();

		      ResultSet rs = stmt.executeQuery("SELECT id, member FROM group "
		          + "WHERE member=" + memberID);

		      // F�r jeden Eintrag im Suchergebnis wird nun ein Group-Objekt erstellt.
		      while (rs.next()) {
		    	ShoppingList sl = new ShoppingList();
		        sl.setId(rs.getInt("id"));
		      

		        // Hinzuf�gen des neuen Objekts zum Ergebnisvektor
		        result.add(sl);
		      }
		    }
		    catch (SQLException e2) {
		      e2.printStackTrace();
		    }

		    // Ergebnisvektor zur�ckgeben
		    return result;
		  }
		
		/**
		 * Auslesen aller SL einer Person (durch <code> Person</code>-Objekt 
		 * gegeben).
		 * @param member Personobjekt, dessen SL ausgelesen werden sollen.
		 * @return alle SL der Person
		 */
		 public ArrayList<ShoppingList> findByMember(Person member) {
    
			    /*
			     * Wir lesen einfach die id (Prim�rschl�ssel) des Person-Objekts
			     * aus und delegieren die weitere Bearbeitung an findByMember(int memberID).
			     */
			    return findByMember(member.getId());
			  }

		
			
		}
