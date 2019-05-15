package de.hdm.softwarepraktikum.server.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import de.hdm.softwarepraktikum.shared.bo.Group;
import de.hdm.softwarepraktikum.shared.bo.Person;
import de.hdm.softwarepraktikum.shared.bo.ShoppingList;

public class ShoppingListMapper {
	
	/* 
	 * Speicherung der Instanz dieser Mapperklasse.
	 */
	
	private static ShoppingListMapper shoppinglistMapper = null;
	
	/*
	 * Konstruktor ist geschÃ¼tzt, um weitere Instanzierung zu verhindern.
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
			
			//Statement ausfüllen und als Query an die DB schicken
			ResultSet rs = stmt.executeQuery("Select id, title FROM shoppinglist" + "WHERE id= " + id);
			
			/*
		     * Da id Primärschlüssel ist, kann max. nur ein Tupel zurückgegeben
		     * werden. Prüfe, ob ein Ergebnis vorliegt.
		     */
			if (rs.next()) {
				//Ergebnis-Tupel in Objekt umwandeln
				ShoppingList sl = new ShoppingList();
				sl.setId(rs.getInt("id"));
				sl.setTitle(rs.getString("title"));
				
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
			
			// Für jeden Eintrag im Suchergebnis wird nun ein ShoppingList-Objekt erstellt.
			while(rs.next()) {
				ShoppingList sl = new ShoppingList();
				sl.setId(rs.getInt("id"));
				sl.setTitle(rs.getString("title"));
				
				
				//Hinzufügen des neuen Objekts zum Ergebnisvektor
				result.add(sl);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		//Ergebnis zurückgeben
		return result;
	}
	
	/*
	 * Methode um eine shoppinglist anhand einer Gruppe zu finden.
	 */
	
	public ShoppingList findByGroup(Group g) {
		
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
			
			/*
			 * Zunächst schauen wir nach, welches der momentan höchste
			 * Primärschlüsselwert ist.
			 */
			ResultSet rs = stmt.executeQuery("SELECT MAX(id) AS id " + "FROM shoppinglist ");
			
			// Wenn wir etwas zurückerhalten, kann dies nur einzeilig sein
			if (rs.next()) {
			/*
			 * sl erhält den bisher maximalen, nun um 1 inkrementierten
			 * Primärschlüssel.
			 */
			sl.setId(rs.getInt("maxid") + 1);
					
			stmt = con.createStatement();
							
			// Jetzt erst erfolgt die tatsächliche Einfügeoperation
			stmt.executeUpdate("INSERT INTO shoppinglists (id, title) " + "VALUES (" + sl.getId() + ",'"
					+ sl.getTitle() + "')");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		/*
		 * Rückgabe, der evtl. korrigierten ShoppingList.
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
			
			stmt.executeUpdate("UPDATE shoppinglists " + "SET title=\"" + sl.getTitle() + "\", "  + "WHERE id=" + sl.getId());
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		// Um Analogie zu insert(ShoppingList sl) zu wahren, wird sl zurückgegeben
				return sl;
	}
		
		/*
		 * Delete Methode, um eine SL aus der Datenbank zu entfernen.
		 */
		
		public void delete(ShoppingList sl) {
			Connection con = DBConnection.connection();
			
			try {
				Statement stmt = con.createStatement();
				
				stmt.executeUpdate("DELETE FROM shoppinglists " + "WHERE id=" + sl.getId());
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
		          + "WHERE member=" + memberID + " ORDER BY id");

		      // Für jeden Eintrag im Suchergebnis wird nun ein Group-Objekt erstellt.
		      while (rs.next()) {
		    	ShoppingList sl = new ShoppingList();
		        sl.setId(rs.getInt("id"));
		        sl.setMemberID(rs.getInt("member"));

		        // Hinzufügen des neuen Objekts zum Ergebnisvektor
		        result.add(sl);
		      }
		    }
		    catch (SQLException e2) {
		      e2.printStackTrace();
		    }

		    // Ergebnisvektor zurückgeben
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
			     * Wir lesen einfach die id (Primärschlüssel) des Person-Objekts
			     * aus und delegieren die weitere Bearbeitung an findByMember(int memberID).
			     */
			    return findByMember(member.getId());
			  }

}
