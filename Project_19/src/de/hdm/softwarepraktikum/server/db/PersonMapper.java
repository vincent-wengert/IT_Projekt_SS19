package de.hdm.softwarepraktikum.server.db;

import java.sql.*;
import java.util.ArrayList;

import de.hdm.softwarepraktikum.shared.bo.Person;


public class PersonMapper{
	
	private static PersonMapper personMapper = null;
	
	protected PersonMapper() {
}
	/**
	 * Sicherstellung der Singleton-Eigenschaft der Mapperklasse.
	 * 
	 * @return Gibt den Person Mapper zurück.
	 */
	public static PersonMapper personMapper() {
		if (personMapper == null) {
			personMapper = new PersonMapper();
		}

		return personMapper;
}
	
	/**
	 * Delete Methode, um Person Datensätze aus der Datenbank zu entfernen.
	 * 
	 * @param Person person wird übergeben.
	 */
	public void delete(Person person) {

		Connection con = DBConnection.connection();

		try {
			//Setzt den AutoCommit auf false, um das sichere Schreiben in die Datenbank zu gewährleisten.
			con.setAutoCommit(false);
			
			Statement stmt = con.createStatement();
			stmt.executeUpdate("DELETE FROM Person WHERE id =" + person.getId());

			Statement stmt2 = con.createStatement();
			stmt2.executeUpdate("DELETE FROM Businessobject WHERE id =" + person.getId());

			//Erst Wenn alle Statements fehlerfrei ausgeführt wurden, wird commited.
			con.commit();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}

}

	public Person update(Person person) {
		Connection con = DBConnection.connection();

		try {
			
			//Setzt den AutoCommit auf false, um das sichere Schreiben in die Datenbank zu gewährleisten.
			con.setAutoCommit(false);
			
			PreparedStatement stmt = con.prepareStatement("UPDATE Businessobject SET Changedate= ?, IsShared= ? WHERE Businessobject.BO_ID = ?");

			stmt.setTimestamp(1, person.getChangedate());
			//stmt.setBoolean(2, person.isShared());
			stmt.setInt(3, person.getId());
			stmt.executeUpdate();
			
			//Wenn alle Statements fehlerfrei ausgeführt wurden, wird commited.
			con.commit();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return person;
}
	/**
	 * Insert Methode, um eine neue <codee>Person</code> der Datenbank hinzuzufügen.
	 * 
	 * @param person Die Person wird übergeben.
	 * @return Die gespeicherte Person wird zurückgegeben.
	 */
	public Person insert(Person person) {
		Connection con = DBConnection.connection();

		try {

			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT MAX(id) AS maxid " + "FROM Businessobject ");

			if (rs.next()) {

				person.setId(rs.getInt("maxid") + 1);
			}
			//Setzt den AutoCommit auf false, um das sichere Schreiben in die Datenbank zu gewährleisten.
			con.setAutoCommit(false);
			
			PreparedStatement stmt2 = con.prepareStatement(
					"INSERT INTO Businessobject (id, Creationdate) VALUES (?, ?)",
					Statement.RETURN_GENERATED_KEYS);
			
			//Welche Attribute kommen alle in die DB? Muessen hier ggf hinzugefuegt werden. 
			stmt2.setInt(1, person.getId());
			stmt2.setTimestamp(2, person.getCreationdate());
			stmt2.executeUpdate();

			PreparedStatement stmt3 = con.prepareStatement("INSERT INTO Person (Person_BO_ID) VALUES (?)",

					Statement.RETURN_GENERATED_KEYS);

			stmt3.setInt(1, person.getId());
			stmt3.executeUpdate();
			
			//Wenn alle Statements fehlerfrei ausgeführt wurden, wird commited.
			con.commit();

		} catch (SQLException e) {
			e.printStackTrace();

		}
		return person;
	}
	
	/**
	 * Suchen einer Person mit einer entsprechenden Id.
	 * 
	 * @param id Die id wird übergeben.
	 * @return Liefert die Person mit der übergebenen Contact_BO_ID zurück.
	 */
	public Person findById(int id) {

		Connection con = DBConnection.connection();

		try {

			Statement stmt = con.createStatement();

			ResultSet rs = stmt.executeQuery(

					"SELECT * FROM Person JOIN Businessobject ON Person.Person_BO_ID = Businessobject.BO_ID"
							+ " WHERE Contact_BO_ID = " + id);

			if (rs.next()) {

				//Welche Attribute kommen alle in die DB? Muessen hier ggf hinzugefuegt werden. 
				Person person = new Person();
				person.setId(rs.getInt("Person_BO_ID"));
				person.setCreationdate(rs.getTimestamp("Creationdate"));

				
				return person;
			}

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return null;
	}

	public ArrayList<Person> findAllPersons() {
		Connection con = DBConnection.connection();

		ArrayList<Person> persons = new ArrayList<Person>();

		try {

			Statement stmt = con.createStatement();

			//Welche Attribute kommen alle in die DB? Muessen hier ggf hinzugefuegt werden. 
			ResultSet rs = stmt.executeQuery(
					"SELECT Person.Person_BO_ID,Businessobject.Owner, Businessobject.Creationdate, Businessobject.Changedate, Businessobject.IsShared"
							+ " FROM Person JOIN Businessobject ON Person.Person_BO_ID = Businessobject.BO_ID"
			);

			while (rs.next()) {

				//Welche Attribute kommen alle in die DB? Muessen hier ggf hinzugefuegt werden. 
				Person person = new Person();
				person.setId(rs.getInt("Contact_BO_Id"));
				person.setCreationdate(rs.getTimestamp("Creationdate"));
				person.setChangedate(rs.getTimestamp("Changedate"));


				persons.add(person);

			}

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return persons;
	}
	
	public ArrayList<Person> findByName(String name){
		Connection con = DBConnection.connection();
		ArrayList<Person> result = new ArrayList<Person>();
		
		try {
			Statement stmt = con.createStatement();
			
			ResultSet rs = stmt.executeQuery("SELECT id, name " + "FROM person " + "WHERE name LIKE '" + name + "'");
			
			//Für jeden Eintrag im Suchergebnis wird ein Person-Objekt erstellt.
			while(rs.next()) {
				Person p = new Person();
				p.setId(rs.getInt("id"));
				p.setName(rs.getString("name"));
				
				//Neues Objekt zur ArrayList hinzufügen
				result.add(p);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		//Ergebnis zurückgeben
		return result;
	}
}
