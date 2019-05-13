package de.hdm.softwarepraktikum.server.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Vector;

import de.hdm.softwarepraktikum.shared.bo.Group;
import de.hdm.softwarepraktikum.shared.bo.Item;
import de.hdm.softwarepraktikum.shared.bo.Person;
import de.hdm.softwarepraktikum.shared.bo.ShoppingList;
import de.hdm.thies.bankProjekt.server.db.DBConnection;
import de.hdm.thies.bankProjekt.shared.bo.Account;
import de.hdm.thies.bankProjekt.shared.bo.Customer;

import java.util.ArrayList;

public class GroupMapper {

	/**
	   * Die Klasse GroupMapper wird nur einmal instantiiert. Man spricht hierbei
	   * von einem sogenannten <b>Singleton</b>.
	   * <p>
	   * Diese Variable ist durch den Bezeichner <code>static</code> nur einmal für
	   * sämtliche eventuellen Instanzen dieser Klasse vorhanden. Sie speichert die
	   * einzige Instanz dieser Klasse.
	   * 
	   * @author Peter Thies
	   */
	
	private static GroupMapper groupMapper = null;
	
	/**
	   * Geschützter Konstruktor - verhindert die Möglichkeit, mit <code>new</code>
	   * neue Instanzen dieser Klasse zu erzeugen.
	   */
	
	protected GroupMapper() {
		
	}
	
	/*
	 * Einhaltung der Singleton Eigenschaft des Mappers.
	 */
	
	public static GroupMapper groupMapper() {
		
		if(groupMapper == null) {
			groupMapper = new GroupMapper();
		}
		
		return groupMapper;
	}
	
	
	/*
	 * Einkäufergruppe anhand ihrer Id suchen.
	 */
	
	public Group findById(int id) {
		
		//Herstellung einer Verbindung zur DB-Connection
				Connection con =DBConnection.connection();
				
		try {
			//leeres SQL-Statement (JDBC) anlegen
			Statement stmt = con.createStatement();
			
			//Statement ausfüllen und als Query an die DB schicken
			ResultSet rs = stmt.executeQuery("Select id, name, user FROM person" + "WHERE id= " + id);
			
			/*
		     * Da id Primärschlüssel ist, kann max. nur ein Tupel zurückgegeben
		     * werden. Prüfe, ob ein Ergebnis vorliegt.
		     */
			if (rs.next()) {
				//Ergebnis-Tupel in Objekt umwandeln
				Group g = new Group();
				g.setId(rs.getInt("id"));
				g.setTitle(rs.getString("title"));
				g.setMember((ArrayList<Person>) rs.getArray("member"));
				
				return g; 		
	}
		}catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	return null;
	}
	
	public Group insert(Group g) {
		Connection con = DBConnection.connection();
		
		try {
			Statement stmt = con.createStatement();
			
			/*
			 * Zunächst schauen wir nach, welches der momentan höchste
			 * Primärschlüsselwert ist.
			 */
			ResultSet rs = stmt.executeQuery("SELECT MAX(id) AS maxid " + "FROM group ");
			
			// Wenn wir etwas zurückerhalten, kann dies nur einzeilig sein
			if (rs.next()) {
			/*
			 * g erhält den bisher maximalen, nun um 1 inkrementierten
			 * Primärschlüssel.
			 */
			g.setId(rs.getInt("maxid") + 1);
					
			stmt = con.createStatement();
							
			// Jetzt erst erfolgt die tatsächliche Einfügeoperation
			stmt.executeUpdate("INSERT INTO groups (bo_id, name, user) " + "VALUES (" + g.getId() + ",'"
					+ g.getTitle() + "','" + g.getMember() + "')");
		}
	} catch (SQLException e) {
		e.printStackTrace();
	}
		
		/*
		 * Rückgabe, der evtl. korrigierten Group.
		 */
		return g;
	}
	
	/**
	 * Wiederholtes Schreiben eines Objekts in die Datenbank.
	 * 
	 * @param g
	 *            das Objekt, das in die DB geschrieben werden soll
	 * @return das als Parameter übergebene Objekt
	 */
	
	public Group update(Group g) {
		Connection con = DBConnection.connection();
		
		try {
			Statement stmt = con.createStatement();
			
			stmt.executeUpdate("UPDATE group " + "SET title=\"" + g.getTitle() + "\", " + "user=\""
					+ g.getMember() + "\" " + "WHERE id=" + g.getId());
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		// Um Analogie zu insert(Group g) zu wahren, wird g zurückgegeben
				return g;
	}
	
	//Löschung einer Gruppe
	public void delete(Group g) {
		Connection con = DBConnection.connection();
		
		try {
			Statement stmt = con.createStatement();
			
			stmt.executeUpdate("DELETE FROM group " + "WHERE id=" + g.getId());
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * auslesen aller Kunden
	 * @return Eine ArrayList mit Group-Objekten, die alle Gruppen im System darstellen.
	 */
	public ArrayList<Group> findAll() {
		
		Connection con = DBConnection.connection();
		
		//Ergebnisvektor vorbereiten
		ArrayList<Group> result = new ArrayList<Group>();
		
		try {
			
			Statement stmt = con.createStatement();
			
			ResultSet rs = stmt
					.executeQuery("SELECT id, title, member " + "FROM group " + "ORDER BY name");
			
			// Für jeden Eintrag im Suchergebnis wird nun ein Group-Objekt erstellt.
			while(rs.next()) {
				Group g = new Group();
				g.setId(rs.getInt("id"));
				g.setTitle(rs.getString("title"));
				g.setMember((ArrayList<Person>)rs.getArray("member"));
				
				
				//Hinzufügen des neuen Objekts zum Ergebnisvektor
				result.add(g);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		//Ergebnis zurückgeben
		return result;
	}
	
	/**
	 * Auslesen aller Gruppen eines durch Fremdschlüssel (id) gegebenen
	 * Kunden.
	 * @param memberID
	 * @return
	 */
	public ArrayList<Group> findByMember(int memberID) {
	    Connection con = DBConnection.connection();
	    ArrayList<Group> result = new ArrayList<Group>();

	    try {
	      Statement stmt = con.createStatement();

	      ResultSet rs = stmt.executeQuery("SELECT id, member FROM group "
	          + "WHERE member=" + memberID + " ORDER BY id");

	      // Für jeden Eintrag im Suchergebnis wird nun ein Group-Objekt erstellt.
	      while (rs.next()) {
	        Group g = new Group();
	        g.setId(rs.getInt("id"));
	        g.setMemberID(rs.getInt("member"));

	        // Hinzufügen des neuen Objekts zum Ergebnisvektor
	        result.add(g);
	      }
	    }
	    catch (SQLException e2) {
	      e2.printStackTrace();
	    }

	    // Ergebnisvektor zurückgeben
	    return result;
	  }
	
	/**
	 * Auslesen aller Gruppen einer Person (durch <code> Person</code>-Objekt 
	 * gegeben).
	 * @param member Personobjekt, dessen Gruppen ausgelesen werden sollen.
	 * @return alle Gruppen der Person
	 */
	 public ArrayList<Group> findByMember(Person member) {

		    /*
		     * Wir lesen einfach die id (Primärschlüssel) des Person-Objekts
		     * aus und delegieren die weitere Bearbeitung an findByMember(int memberID).
		     */
		    return findByMember(member.getId());
		  }
	
	
	
	
	/**
	 * Methode, um alle Einkaufslisten einer Gruppe auszulesen.
	 * @param p
	 * @return ShoppingLists
	 
	public ArrayList<ShoppingList> getShoppingListsPerGroup(Group p) {
		
		Connection con = DBConnection.connection();
		
		ArrayList<ShoppingList> result1 = new ArrayList<ShoppingList>();
		
		try {
			Statement stmt = con.createStatement();
			
			ResultSet rs = stmt
					.executeQuery("SELECT id, title " + "FROM shoppinglist " + )
		}
		
		
		
	}
	
	*/
	
}

