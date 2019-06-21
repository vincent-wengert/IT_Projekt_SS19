package de.hdm.softwarepraktikum.server.db;

import java.sql.*;
import java.util.ArrayList;

import de.hdm.softwarepraktikum.shared.bo.Group;
import de.hdm.softwarepraktikum.shared.bo.Item;
import de.hdm.softwarepraktikum.shared.bo.ListItem;
import de.hdm.softwarepraktikum.shared.bo.Person;
import de.hdm.softwarepraktikum.shared.bo.ShoppingList;
import de.hdm.softwarepraktikum.shared.bo.Store;
import de.hdm.softwarepraktikum.server.db.GroupMapper;

public class PersonMapper {

	private static PersonMapper personMapper = null;

	protected PersonMapper() {
	}

	/**
	 * Sicherstellung der Singleton-Eigenschaft der Mapperklasse.
	 * 
	 * @return Gibt den Person Mapper zur�ck.
	 */
	public static PersonMapper personMapper() {
		if (personMapper == null) {
			personMapper = new PersonMapper();
		}

		return personMapper;
	}

	/**
	 * Delete Methode, um ein<code>Person</code>-Objekt aus der Datenbank zu
	 * l�schen.
	 * 
	 * @param person, das aus der Datenbank zu l�schende Objekt.
	 */
	public void delete(Person person) {

		Connection con = DBConnection.connection();

		try {

			Statement stmt = con.createStatement();
			stmt.executeUpdate("DELETE FROM Person WHERE PersonID =" + person.getId());

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Wiederholtes Schreiben eines Objekts in die Datenbank.
	 * 
	 * @param person, das Objekt das in die Datenbank geschrieben werden soll.
	 * @return das als Parameter �bergebene Objekt
	 */
	public Person update(Person person) {
		Connection con = DBConnection.connection();

		try {

			//Statement stmt = con.createStatement();

			//stmt.executeUpdate("UPDATE Person " + "SET Name=\"" + person.getName() + "\", " + "Changedate=\""
				//	+ person.getChangedate() + "\" " + "WHERE PersonID=" + person.getId());
			
			
			PreparedStatement st = con.prepareStatement("UPDATE Person SET Gmail = ?, Name = ?,"
					+ " Changedate = ? WHERE PersonID = ?");
			
			st.setString(1, person.getGmail());
			st.setString(2, person.getName());
			st.setTimestamp(3, person.getChangedate());
			st.setInt(4, person.getId());
			st.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		// um Analogie zu insert(Person person) zu wahren, geben wir person zur�ck.
		return person;
	}

	/**
	 * Insert Methode, um eine neue <codee>Person</code> der Datenbank hinzuzuf�gen.
	 * 
	 * @param person Die Person wird �bergeben.
	 * @return Die gespeicherte Person wird zur�ckgegeben.
	 */
	public Person insert(Person person) {
		Connection con = DBConnection.connection();

		try {

			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT MAX(PersonID) AS maxid " + "FROM Person ");

			if (rs.next()) {

				person.setId(rs.getInt("maxid") + 1);

//			//Setzt den AutoCommit auf false, um das sichere Schreiben in die Datenbank zu gew�hrleisten.
//			con.setAutoCommit(false);

				PreparedStatement stmt2 = con
						.prepareStatement("INSERT INTO Person (PersonID, Creationdate, Changedate, Name, Gmail) "
								+ "VALUES (?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);

				// Welche Attribute kommen alle in die DB? Muessen hier ggf hinzugefuegt werden.
				stmt2.setInt(1, person.getId());
				stmt2.setTimestamp(2, person.getCreationdate());
				stmt2.setTimestamp(3, person.getChangedate());
				stmt2.setString(4, person.getName());
				stmt2.setString(5, person.getGmail());
				System.out.println(stmt2);
				stmt2.executeUpdate();

			}
		} catch (SQLException e) {
			e.printStackTrace();

		}
		return person;
	}

	/**
	 * Suchen einer Person mit einer entsprechenden Id.
	 * 
	 * @param id Die id wird �bergeben.
	 * @return Liefert die Person mit der �bergebenen Contact_BO_ID zur�ck.
	 */
	public Person findById(int id) {

		// Herstellung einer Verbindung zur DB-Connection
		Connection con = DBConnection.connection();

		try {

			//leeres SQL-Statement (JDBC) anlegen
			Statement stmt = con.createStatement();
			
			//Statement ausfüllen und als Query an die DB schicken
			ResultSet rs = stmt.executeQuery("SELECT PersonID, Name, Gmail, Creationdate, Changedate FROM Person "
					+ " WHERE PersonID = " +id);
			while (rs.next()) {
			//Ergebnis-Tupel in Objekt umwandeln
			 Person p = new Person();
			 p.setId(rs.getInt("PersonID"));
			 p.setName(rs.getString("Name"));
			 p.setGmail(rs.getString("Gmail"));
			 p.setCreationdate(rs.getTimestamp("Creationdate"));
			 p.setChangedate(rs.getTimestamp("Changedate"));
			 
			 return p;
				
			 
			}
		} catch (SQLException e) {
			e.printStackTrace();}
		return null;
		}

		
		

	public ArrayList<Person> findAll() {
		Connection con = DBConnection.connection();

		ArrayList<Person> persons = new ArrayList<Person>();

		try {

			Statement stmt = con.createStatement();

			// Welche Attribute kommen alle in die DB? Muessen hier ggf hinzugefuegt werden.
			ResultSet rs = stmt.executeQuery("SELECT * FROM Person ORDER BY PersonID ASC ");

			while (rs.next()) {

				// Welche Attribute kommen alle in die DB? Muessen hier ggf hinzugefuegt werden.
				Person person = new Person();
				person.setId(rs.getInt("PersonID"));
				person.setGmail(rs.getString("Gmail"));
				person.setName(rs.getString("Name"));
				person.setCreationdate(rs.getTimestamp("Creationdate"));
				person.setChangedate(rs.getTimestamp("Changedate"));

				persons.add(person);

			}

		} catch (SQLException e) {
			e.printStackTrace();

		}
		return persons;
	}

	/**
	 * Member mittels der GMail Adresse finden .
	 *
	 * @param gmail Die gmail wird übergeben des Users.
	 * @return Der User der anhand der gmail gefunden wurde, wird zurückgegeben.
	 */
	public Person findByGmail(String gmail) {

		Connection con = DBConnection.connection();

		try {

			Statement stmt = con.createStatement();

			ResultSet rs = stmt
					.executeQuery("SELECT PersonID, Gmail, Name" + " FROM Person" + " WHERE Gmail ='" + gmail + "'");

			if (rs.next()) {

				Person person = new Person();
				person.setId(rs.getInt("PersonID"));
				person.setGmail(rs.getString("Gmail"));
				person.setName(rs.getString("Name"));

				return person;
			}

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return null;
	}

	/**
	 * Finden einer Person anhand von ihrem Namen.
	 * 
	 * @param name
	 * @return eine Arraylist mit Personen, deren Name der Eingabe �hnelt
	 */
	public ArrayList<Person> findByName(String name) {
		Connection con = DBConnection.connection();
		ArrayList<Person> result = new ArrayList<Person>();

		try {
			Statement stmt = con.createStatement();

			ResultSet rs = stmt.executeQuery("SELECT PersonID, Name " + "FROM Person " + "WHERE Name = '" + name + "'");

			// F�r jeden Eintrag im Suchergebnis wird ein Person-Objekt erstellt.
			while (rs.next()) {
				Person p = new Person();
				p.setId(rs.getInt("id"));
				p.setName(rs.getString("name"));

				// Neues Objekt zur ArrayList hinzuf�gen
				result.add(p);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		// Ergebnis zur�ckgeben
		return result;
	}

	/**
	 * Auslesen der zugeh�rigen <code>Group</code>-Objekte zu einer gegebenen
	 * Person.
	 * 
	 * @param p
	 * @return
	 */
	public ArrayList<Group> getGroupsOf(Person p) {

		/*
		 * Hier wird auf den GroupMapper zugegriffen, welchem der im Person-Objekt
		 * enthaltende Prim�rschl�ssel �bergeben wird. Der PersonMapper l�st die ID in
		 * eine Reihe von Group-Objekten auf.
		 */

		return GroupMapper.groupMapper().findByMember(p);
	}

	/**
	 * Auslesen der zugeh�rigen <code>ShoppingList</code>-Objekte zu einer gegebenen
	 * Person.
	 * 
	 * @param p
	 * @return
	 */
	public ArrayList<ShoppingList> getShoppingListsOf(Person p) {

		/*
		 * Hier wird auf den ShoppingListMapper zugegriffen, welchem der im
		 * Person-Objekt enthaltende Prim�rschl�ssel �bergeben wird. Der PersonMapper
		 * l�st die ID in eine Reihe von ShoppingList-Objekten auf.
		 */
		return ShoppingListMapper.shoppinglistMapper().findByMember(p);
	}
	
	
	/**
	 * Auslesen der zugeh�rigen <code>Person</code>-Objekte zu einer gegebenen
	 * Group Id.
	 * 
	 * @param g
	 * @return
	 */
	public ArrayList<Person> findAllGroupMembers(Group g) {
		Connection con = DBConnection.connection();

		ArrayList<Person> groupMembers = new ArrayList<Person>();

		String st = "SELECT * from `Group` JOIN Participant ON `Group`.Group_ID = Participant.Group_Group_ID WHERE `Group`.Group_ID="
				+ g.getId();

		try {

			Statement stmt = con.createStatement();

			ResultSet rs = stmt.executeQuery(st);

			while (rs.next()) {
				Person p = new Person();
				p.setId(rs.getInt("Person_PersonID"));

				groupMembers.add(p);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return groupMembers;

	}
}
