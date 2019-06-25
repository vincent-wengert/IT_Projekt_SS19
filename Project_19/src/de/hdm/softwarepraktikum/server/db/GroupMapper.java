package de.hdm.softwarepraktikum.server.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
import java.util.ArrayList;
import java.util.Vector;

import com.gargoylesoftware.htmlunit.javascript.host.Console;

import de.hdm.softwarepraktikum.shared.bo.Group;
import de.hdm.softwarepraktikum.shared.bo.Item;
import de.hdm.softwarepraktikum.shared.bo.Person;
import de.hdm.softwarepraktikum.shared.bo.Responsibility;
import de.hdm.softwarepraktikum.shared.bo.ShoppingList;





public class GroupMapper {
	
	/**
	   * Die Klasse GroupMapper wird nur einmal instantiiert. Man spricht hierbei
	   * von einem sogenannten <b>Singleton</b>.
	   * <p>
	   * Diese Variable ist durch den Bezeichner <code>static</code> nur einmal f�r
	   * s�mtliche eventuellen Instanzen dieser Klasse vorhanden. Sie speichert die
	   * einzige Instanz dieser Klasse.
	   * 
	   * @author Bruno Herceg
	   */
	
	private static GroupMapper groupMapper = null;
	
	/**
	   * Geschuetzter Konstruktor - verhindert die Moeglichkeit, mit <code>new</code>
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
	 * Einkaeufergruppe anhand ihrer Id suchen.
	 */
	
	public Group findById(int id) {
		
		//Herstellung einer Verbindung zur DB-Connection
				Connection con =DBConnection.connection();
				
		try {
			//leeres SQL-Statement (JDBC) anlegen
			Statement stmt = con.createStatement();
			
			//Statement ausfuellen und als Query an die DB schicken
			ResultSet rs = stmt.executeQuery("Select Group_ID, Title, user FROM Group" + "WHERE Group_ID= " + id);
			
			/*
		     * Da id Primaerschluessel ist, kann max. nur ein Tupel zurueckgegeben
		     * werden. Pruefe, ob ein Ergebnis vorliegt.
		     */
			if (rs.next()) {
				//Ergebnis-Tupel in Objekt umwandeln
				Group g = new Group();
				g.setId(rs.getInt("Group_ID"));
				g.setTitle(rs.getString("Title"));
				g.setMember((ArrayList<Person>) rs.getArray("member"));
				
				return g; 		
	}
		}catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	return null;
	}
	
	/**
	 * Methode um ein <code>Group</code> Objekt in der Datenbank zu speichern
	 * @param g
	 * @return das neu gespiecherte Group-Objekt.
	 */
	public Group insert(Group g) {
		
		
		Connection con = DBConnection.connection();
		
		try {
			
			Statement stmt = con.createStatement();
			
		/*
		 * Zunaechst schauen wir nach, welches der momentan hoechste
		 * Primaerschluesselwert ist.
		 */
			
			
		ResultSet rs = stmt.executeQuery("SELECT MIN(Group_ID) AS minid " + "FROM `Group` ");
		
		// Wenn wir etwas zurueckerhalten, kann dies nur einzeilig sein
		if (rs.next()) {
		/*
		 * i erhaelt den bisher maximalen, nun um 1 inkrementierten
		 * Primaerschluessel.
		 */
		g.setId(rs.getInt("minid") - 1);
				
		PreparedStatement stmt2 = con.prepareStatement(
				"INSERT INTO `Group` (Group_ID, Title, Creationdate, Changedate) " + "VALUES (?, ?, ?, ?)",
				Statement.RETURN_GENERATED_KEYS);
		
		stmt2.setInt(1, g.getId());
		stmt2.setString(2, g.getTitle());
		stmt2.setTimestamp(3, g.getCreationdate());
		stmt2.setTimestamp(4, g.getChangedate());
		System.out.println(stmt2);
		stmt2.executeUpdate();
		
		}
	} catch (SQLException e) {
	e.printStackTrace();
		}
			
		/*
		 * Rueckgabe, der evtl. korrigierten Group.
		 */
		return g;
	}
	
	/**
	 * Wiederholtes Schreiben eines Objekts in die Datenbank.
	 * 
	 * @param g
	 * @return das als Parameter uebergebene Objekt
	 */
	
	public Group update(Group g) {
		Connection con = DBConnection.connection();
		
		try {
			
			PreparedStatement st = con.prepareStatement("UPDATE Group SET Title = ?, Changedate = ? WHERE Group_ID = ?");
			
			st.setString(1, g.getTitle());
			st.setTimestamp(2, g.getChangedate());
			st.setInt(3, g.getId());
			st.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		// Um Analogie zu insert(Group g) zu wahren, wird g zurueckgegeben
				return g;
	}
	
	//Loeschung einer Gruppe
	public void delete(Group g) {
		Connection con = DBConnection.connection();
		
		try {
			Statement stmt = con.createStatement();
			
			stmt.executeUpdate("DELETE FROM `Group` " + "WHERE Group_ID=" + g.getId());
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
					.executeQuery("SELECT Group_ID, Title, member " + "FROM `Group` " + "ORDER BY Title");
			
			// Fuer jeden Eintrag im Suchergebnis wird nun ein Group-Objekt erstellt.
			while(rs.next()) {
				Group g = new Group();
				g.setId(rs.getInt("Group_ID"));
				g.setTitle(rs.getString("Title"));
				
				
				//Hinzufuegen des neuen Objekts zum Ergebnisvektor
				result.add(g);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		//Ergebnis zurueckgeben
		return result;
	}
	
	/**
	 * Auslesen aller Gruppen eines durch Fremdschluessel (id) gegebenen
	 * Kunden.
	 * @param memberID
	 * @return Eine ArrayList mit <code>Group</code>-Objekten, in welchen ein Nutzer Mitglied ist.
	 */
	public ArrayList<Group> findByMember(int memberID) {
	    Connection con = DBConnection.connection();
	    ArrayList<Group> result = new ArrayList<Group>();

	    try {
	      Statement stmt = con.createStatement();

	      ResultSet rs = stmt.executeQuery("SELECT * FROM Participant"
	    		  + " JOIN `Group` ON Participant.Group_Group_ID = Group_ID"
	    		  + " WHERE Participant.Person_PersonID =" + memberID);
	    		  
	      // Fuer jeden Eintrag im Suchergebnis wird nun ein Group-Objekt erstellt.
	      while (rs.next()) {
	        Group g = new Group();
	        g.setId(rs.getInt("Group_ID"));
	        g.setTitle(rs.getString("Title"));

	        // Hinzufuegen des neuen Objekts zum Ergebnisvektor
	        result.add(g);
	      }
	    }
	    catch (SQLException e2) {
	      e2.printStackTrace();
	    }

	    // Ergebnisvektor zurueckgeben
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
		     * Wir lesen einfach die id (Primaerschluessel) des Person-Objekts
		     * aus und delegieren die weitere Bearbeitung an findByMember(int memberID).
		     */
		    return findByMember(member.getId());
		  }
	 
	 /**
	  * Mitgliedschaft einer Person zu einer Gruppe hinzufuegen
	  * @param p
	  * @param g
	  */
	 public void addMembership(Person p, Group g) {
	 
		 Connection con = DBConnection.connection();
			
			
			try {
			Statement stmt = con.createStatement();
			
			stmt.executeUpdate("INSERT INTO Participant (Group_Group_ID, Person_PersonID) " + "VALUES (" + g.getId() + ","
					+ p.getId() +");");
			}
			catch(SQLException e2) {
				e2.printStackTrace();
			}
	 }
	 
	/**
	 * Loeschen einer Mitgliedschaft zu einer Gruppe 
	 * @param p
	 * @param g
	 */
	public void deleteMembership(Person p, Group g) {
		
		Connection con = DBConnection.connection();
		
		
		try {
		Statement stmt = con.createStatement();
		
		stmt.executeUpdate("DELETE FROM Participant WHERE Group_Group_ID=" + g.getId() + " AND Person_PersonID=" + p.getId());
		System.out.println(stmt);
		}
		catch(SQLException e2) {
			e2.printStackTrace();
		}	
	}
	
	
	
	
	/**
	 * Methode, um alle Einkaufslisten einer Gruppe auszulesen.
	 * @param currentPerson
	 * @return ShoppingLists
	 */
	 
	public ArrayList<ShoppingList> getShoppingListsPerGroup(Group g) {
		
		ArrayList<ShoppingList> result = new ArrayList<ShoppingList>();
		
		Connection con = DBConnection.connection();
		
		
		try {
			Statement stmt = con.createStatement();
			
			//Statement ausf�llen und als Query an die DB schicken
			ResultSet rs = stmt.executeQuery("SELECT * FROM ShoppingList WHERE Group_ID=" + g.getId());
			
			while(rs.next()) {
				ShoppingList sl = new ShoppingList();
				sl.setId(rs.getInt("ShoppingList_ID"));
				sl.setGroupID(rs.getInt("Group_ID"));
				
				//Hinzuf�gen des neuen Objekts zum Ergebnisvektor
				result.add(sl);
			}
		}catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	return result;
		
		
	}
	
	
	
}

