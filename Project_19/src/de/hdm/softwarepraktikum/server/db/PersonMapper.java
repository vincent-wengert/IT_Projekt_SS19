package de.hdm.softwarepraktikum.server.db;

import java.sql.*;

public class PersonMapper{
	
	private static personMapper personMapper = null;
	
	protected personMapper() {
}
	/**
	 * Sicherstellung der Singleton-Eigenschaft der Mapperklasse.
	 * 
	 * @return Gibt den Person Mapper zurück.
	 */
	public static personMapper personMapper() {
		if (personMapper == null) {
			personMapper = new personMapper();
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
			stmt.executeUpdate("DELETE FROM Person WHERE person_BO_ID =" + person.getBO_ID());

			Statement stmt2 = con.createStatement();
			stmt2.executeUpdate("DELETE FROM Businessobject WHERE BO_ID =" + person.getBO_ID());

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

			stmt.setTimestamp(1, person.getChangeDate());
			//stmt.setBoolean(2, person.isShared());
			stmt.setInt(3, person.getBO_ID());
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
			ResultSet rs = stmt.executeQuery("SELECT MAX(BO_ID) AS maxid " + "FROM Businessobject ");

			if (rs.next()) {

				person.setBO_ID(rs.getInt("maxid") + 1);
			}
			//Setzt den AutoCommit auf false, um das sichere Schreiben in die Datenbank zu gewährleisten.
			con.setAutoCommit(false);
			
			PreparedStatement stmt2 = con.prepareStatement(
					"INSERT INTO Businessobject (BO_ID, Creationdate) VALUES (?, ?)",
					Statement.RETURN_GENERATED_KEYS);
			
			//Welche Attribute kommen alle in die DB? Muessen hier ggf hinzugefuegt werden. 
			stmt2.setInt(1, person.getBO_ID());
			stmt2.setTimestamp(2, person.getCreationDate());
			stmt2.executeUpdate();

			PreparedStatement stmt3 = con.prepareStatement("INSERT INTO Person (Person_BO_ID) VALUES (?)",

					Statement.RETURN_GENERATED_KEYS);

			stmt3.setInt(1, person.getBO_ID());
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
				person.setBO_ID(rs.getInt("Person_BO_ID"));
				person.setCreationDate(rs.getTimestamp("Creationdate"));

				
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
				person.setBO_ID(rs.getInt("Contact_BO_Id"));
				person.setCreationDate(rs.getTimestamp("Creationdate"));
				person.setChangeDate(rs.getTimestamp("Changedate"));


				persons.add(person);

			}

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return persons;
}
}
