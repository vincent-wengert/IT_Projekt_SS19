package de.hdm.softwarepraktikum.server.db;

import java.sql.*;
import java.util.ArrayList;

import de.hdm.softwarepraktikum.shared.bo.Group;
import de.hdm.softwarepraktikum.shared.bo.Person;
import de.hdm.softwarepraktikum.server.db.GroupMapper;

public class PersonMapper {

	/**
	 * Die Klasse PersonMapper wird nur einmal instantiiert. Man spricht hierbei von
	 * einem sogenannten <b>Singleton</b>.
	 * <p>
	 * Diese Variable ist durch den Bezeichner <code>static</code> nur einmal fuer
	 * saemtliche eventuellen Instanzen dieser Klasse vorhanden. Sie speichert die
	 * einzige Instanz dieser Klasse.
	 * 
	 * @author Niklas Öxle
	 */

	private static PersonMapper personMapper = null;

	/**
	 * Geschuetzter Konstruktor - verhindert die Moeglichkeit, mit <code>new</code>
	 * neue Instanzen dieser Klasse zu erzeugen.
	 */

	protected PersonMapper() {
	}

	/**
	 * Einhaltung der Singleton Eigenschaft des Mappers.
	 * 
	 * @return: gibt den personMapper zurueck
	 */

	public static PersonMapper personMapper() {
		if (personMapper == null) {
			personMapper = new PersonMapper();
		}

		return personMapper;
	}

	/**
	 * Methode um ein Person-Datensatz in der Datenbank zu löschen.
	 * 
	 * @param person : Die zu loeschende Person wird uebergeben
	 */

	public void delete(Person person) {

		Connection con = DBConnection.connection();

		try {

			Statement stmt = con.createStatement();
			stmt.executeUpdate("DELETE FROM Person WHERE PersonID = " + person.getId());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Wiederholtes Schreiben eines <code>Person</code> Objekts in die Datenbank.
	 * 
	 * @param person : Die zu aktualisierende Person wird übergeben
	 * @return: die aktualisierte Person wird zurückgegeben
	 * 
	 */

	public Person update(Person person) {
		Connection con = DBConnection.connection();

		try {

			PreparedStatement st = con.prepareStatement("UPDATE Person SET Gmail= ?, Name= ? WHERE PersonID = ?");

			st.setString(1, person.getGmail());
			st.setString(2, person.getName());
			st.setInt(3, person.getId());
			st.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return person;
	}

	/**
	 * Methode um ein Person Objekt in der Datenbank zu speichern
	 * 
	 * @param person : eine neue zu speichernde Person in der Datenbank wird
	 *               uebergeben
	 * @return: die neu gespeicherte Person wird zurückgegeben
	 * 
	 */

	public Person insert(Person person) {
		Connection con = DBConnection.connection();

		try {
			if (person.getName() == null) {
				person.setName("Gastnutzer");
			}

			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT MAX(PersonID) AS maxid " + "FROM Person ");

			if (rs.next()) {

				person.setId(rs.getInt("maxid") + 1);

				PreparedStatement stmt2 = con
						.prepareStatement("INSERT INTO Person (PersonID, Creationdate, Changedate, Name, Gmail) "
								+ "VALUES (?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);

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
	 * Methode um ein einzelnes <code>Person</code> Objekt anhand einer ID zu
	 * suchen.
	 * 
	 * @param id : ID der zu findenden Person wird übergeben.
	 * @return Die anhand der id gefundene Person wird zurückgegeben.
	 */

	public Person findById(int id) {

		Connection con = DBConnection.connection();

		try {

			Statement stmt = con.createStatement();

			ResultSet rs = stmt.executeQuery(
					"SELECT PersonID, Name, Gmail, Creationdate, Changedate FROM Person " + " WHERE PersonID = " + id);
			while (rs.next()) {

				Person p = new Person();
				p.setId(rs.getInt("PersonID"));
				p.setName(rs.getString("Name"));
				p.setGmail(rs.getString("Gmail"));
				p.setCreationdate(rs.getTimestamp("Creationdate"));
				p.setChangedate(rs.getTimestamp("Changedate"));

				return p;

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Methode um alle in der Datenbank vorhandenen Person-Datensätze abzurufen.
	 * Diese werden als einzelne <code>Person</code> Objekte innerhalb einer
	 * ArrayList zur�ckgegeben.
	 * 
	 * @return ArrayList aller Persons wird zurückgegeben.
	 */

	public ArrayList<Person> findAll() {
		Connection con = DBConnection.connection();

		ArrayList<Person> persons = new ArrayList<Person>();

		try {

			Statement stmt = con.createStatement();

			ResultSet rs = stmt.executeQuery("SELECT * FROM Person ORDER BY PersonID ASC ");

			while (rs.next()) {

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
	 * Methode um eine Person mittels dem Attribut Gmail zu finden. Diese werden als
	 * einzelne <code>Person</code> Objekt zurückgegeben
	 * 
	 * @param gmail: gmail der gesuchten Person
	 * @return person : Das Person- Objekt mit der übergebenen gmail
	 */

	public Person findByGmail(String gmail) {

		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();

			ResultSet rs = stmt.executeQuery("SELECT PersonID, Gmail, Name FROM Person WHERE Gmail ='" + gmail + "'");

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
	 * Methode um eine Person anhand Ihres Namens zu finden.
	 * 
	 * @param name
	 * @return eine Arraylist mit Personen, deren Name der Eingabe gleicht
	 */

	/**
	 * Auslesen der zugehörigen <code>Group</code>-Objekte zu einer gegebenen
	 * Person.
	 * 
	 * @param p
	 * @return
	 */
	public ArrayList<Group> getGroupsOf(Person p) {

		/*
		 * Hier wird auf den GroupMapper zugegriffen, welchem der im Person-Objekt
		 * enthaltende Primaerschl�ssel uebergeben wird. Der PersonMapper liest die ID
		 * in eine Reihe von Group-Objekten auf.
		 */

		return GroupMapper.groupMapper().findByMember(p);
	}

	/**
	 * Methode um alle Mitglieder einer bestimmten Person zu finden.
	 * 
	 * @param g : Gruppe der Participants
	 * @return ArrayList mit allen Gruppenmitgliedern
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

	public void deleteparticipationByPersonID(Person p) {
		// TODO Auto-generated method stub

		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();
			stmt.executeUpdate("DELETE FROM Participant WHERE Person_PersonID = " + p.getId());

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
}
