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

public class GroupMapper {

	/**
	   * Die Klasse GroupMapper wird nur einmal instantiiert. Man spricht hierbei
	   * von einem sogenannten <b>Singleton</b>.
	   * <p>
	   * Diese Variable ist durch den Bezeichner <code>static</code> nur einmal f�r
	   * s�mtliche eventuellen Instanzen dieser Klasse vorhanden. Sie speichert die
	   * einzige Instanz dieser Klasse.
	   * 
	   * @author Peter Thies
	   */
	
	private static GroupMapper groupMapper = null;
	
	/**
	   * Gesch�tzter Konstruktor - verhindert die M�glichkeit, mit <code>new</code>
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
	 * Eink�ufergruppe anhand ihrer Id suchen.
	 */
	
	public Group findById(int bo_id) {
		
		//Herstellung einer Verbindung zur DB-Connection
				Connection con =DBConnection.connection();
				
		try {
			//leeres SQL-Statement (JDBC) anlegen
			Statement stmt = con.createStatement();
			
			//Statement ausf�llen und als Query an die DB schicken
			ResultSet rs = stmt.executeQuery("Select bo_id, name, user FROM person" + "WHERE bo_id= " + bo_id);
			
			/*
		     * Da id Prim�rschl�ssel ist, kann max. nur ein Tupel zur�ckgegeben
		     * werden. Pr�fe, ob ein Ergebnis vorliegt.
		     */
			if (rs.next()) {
				//Ergebnis-Tupel in Objekt umwandeln
				Group g = new Group();
				g.setBO_ID(rs.getInt("bo_id"));
				g.setName(rs.getString("name"));
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
			 * Zun�chst schauen wir nach, welches der momentan h�chste
			 * Prim�rschl�sselwert ist.
			 */
			ResultSet rs = stmt.executeQuery("SELECT MAX(group_id) AS maxgroup_id " + "FROM group ");
			
			// Wenn wir etwas zur�ckerhalten, kann dies nur einzeilig sein
			if (rs.next()) {
			/*
			 * g erh�lt den bisher maximalen, nun um 1 inkrementierten
			 * Prim�rschl�ssel.
			 */
			g.setBO_ID(rs.getInt("maxgroup_id") + 1);
					
			stmt = con.createStatement();
							
			// Jetzt erst erfolgt die tats�chliche Einf�geoperation
			stmt.executeUpdate("INSERT INTO groups (bo_id, name, user) " + "VALUES (" + g.getBO_ID() + ",'"
					+ g.getName() + "','" + g.getMember() + "')");
		}
	} catch (SQLException e) {
		e.printStackTrace();
	}
		
		/*
		 * R�ckgabe, der evtl. korrigierten Group.
		 */
		return g;
	}
	
	/**
	 * Wiederholtes Schreiben eines Objekts in die Datenbank.
	 * 
	 * @param g
	 *            das Objekt, das in die DB geschrieben werden soll
	 * @return das als Parameter �bergebene Objekt
	 */
	
	public Group update(Group g) {
		Connection con = DBConnection.connection();
		
		try {
			Statement stmt = con.createStatement();
			
			stmt.executeUpdate("UPDATE groups " + "SET name=\"" + g.getName() + "\", " + "user=\""
					+ g.getMember() + "\" " + "WHERE bo_id=" + g.getBO_ID());
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		// Um Analogie zu insert(Group g) zu wahren, wird g zur�ckgegeben
				return g;
	}
	
	//L�schung einer Gruppe
	public void delete(Group g) {
		Connection con = DBConnection.connection();
		
		try {
			Statement stmt = con.createStatement();
			
			stmt.executeUpdate("DELETE FROM groups " + "WHERE bo_id=" + g.getBO_ID());
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * auslesen aller Kunden
	 * @return Eine ArrayList mit Group-Objekten, die alle Gruppen im System darstellen.
	 */
	public ArrayList<Group> findAllGroups() {
		//Ergebnisvektor vorbereiten
		ArrayList<Group> result = new ArrayList<Group>();
		
		Connection con = DBConnection.connection();
		try {
			
			Statement stmt = con.createStatement();
			
			ResultSet rs = stmt
					.executeQuery("SELECT bo_id, name, isglobal, favoriteitem " + "FROM items " + "ORDER BY name");
			
			// F�r jeden Eintrag im Suchergebnis wird nun ein Item-Objekt erstellt.
			while(rs.next()) {
				Group g = new Group();
				g.setBO_ID(rs.getInt("bo_id"));
				g.setName(rs.getString("name"));
				g.setMember((ArrayList<Person>)rs.getArray("member"));
				g.setFavoriteitem((ArrayList<ListItem>)rs.getArray("favoriteitem"));
				
				//Hinzuf�gen des neuen Objekts zum Ergebnisvektor
				result.add(g);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		//Ergebnis zur�ckgeben
		return result;
	}
	
}

