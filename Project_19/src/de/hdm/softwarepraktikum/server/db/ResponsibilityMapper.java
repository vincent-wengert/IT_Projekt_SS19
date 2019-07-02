package de.hdm.softwarepraktikum.server.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import de.hdm.softwarepraktikum.shared.bo.Person;
import de.hdm.softwarepraktikum.shared.bo.Responsibility;
import de.hdm.softwarepraktikum.shared.bo.Store;

public class ResponsibilityMapper {

	/**
	 * Die Klasse ResponsibilityMapper wird nur einmal instantiiert. Man spricht
	 * hierbei von einem sogenannten <b>Singleton</b>.
	 * <p>
	 * Diese Variable ist durch den Bezeichner <code>static</code> nur einmal fuer
	 * saemtliche eventuellen Instanzen dieser Klasse vorhanden. Sie speichert die
	 * einzige Instanz dieser Klasse.
	 * 
	 * @author Niklas Öxle
	 */

	private static ResponsibilityMapper responsibilityMapper = null;

	/**
	 * Geschuetzter Konstruktor - verhindert die Moeglichkeit, mit <code>new</code>
	 * neue Instanzen dieser Klasse zu erzeugen.
	 */

	protected ResponsibilityMapper() {

	}

	/**
	 * Einhaltung der Singleton Eigenschaft des Mappers.
	 * 
	 * @return: gibt den responsibilityMapper zurueck
	 */

	public static ResponsibilityMapper responsibilityMapper() {
		if (responsibilityMapper == null) {
			responsibilityMapper = new ResponsibilityMapper();
		}

		return responsibilityMapper;
	}

	/**
	 * Methode um ein einzelnes <code>Responsibilty</code> Objekt anhand einer ID zu
	 * suchen.
	 * 
	 * @param id: ID der zu findenden Responsibilty wird �bergeben.
	 * @return Die anhand der id gefundene Responsibility wird zur�ckgegeben.
	 */

	public Responsibility findById(int id) {

		Connection con = DBConnection.connection();

		Responsibility rl = new Responsibility();

		try {
			Statement stmt = con.createStatement();

			ResultSet rs = stmt.executeQuery("SELECT * FROM Responsibility" + " WHERE Responsibility_ID = " + id);

			if (rs.next()) {

				rl.setId(rs.getInt("Responsibility_ID"));
				rl.setCreationdate(rs.getTimestamp("Creationdate"));
				rl.setChangedate(rs.getTimestamp("Changedate"));
				rl.setBuyerID(rs.getInt("Person_ID"));
				rl.setSlID(rs.getInt("Shoppinglist_ID"));
				rl.setStoreID(rs.getInt("Store_ID"));
			}
		} catch (

		SQLException e) {
			e.printStackTrace();
			return null;
		}

		return rl;
	}

	/**
	 * Methode um alle in der Datenbank vorhandenen Responsibilty-Datensätze welchen
	 * ein bestimmter Store zugeordnet ist auszugeben. Diese werden als einzelne
	 * <code>Responsibility</code> Objekte innerhalb einer ArrayList zurückgegeben.
	 * 
	 * @param store : Store, welcher der Responsibility zugeordnet ist.
	 * @return ArrayList aller Responsibilites welchen ein bestimmter Store
	 *         zugeordnet ist wird zurückgegeben.
	 */

	public ArrayList<Responsibility> findbystore(Store s) {

		ArrayList<Responsibility> result = new ArrayList<Responsibility>();

		Connection con = DBConnection.connection();
		try {

			Statement stmt = con.createStatement();

			ResultSet rs = stmt.executeQuery(
					"Select Responsibilty_ID,Store_ID FROM Responsibility" + "WHERE Store_ID= " + s.getId());

			while (rs.next()) {
				Responsibility r = new Responsibility();
				r.setId(rs.getInt("Responsibility_ID"));
				r.setStoreID(rs.getInt("Store_ID"));

				result.add(r);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return result;

	}

	/**
	 * Methode um alle in der Datenbank vorhandenen Responsibilty-Datensätze welchen
	 * eine bestimmte Person zugeordnet ist auszugeben. Diese werden als einzelne
	 * <code>Responsibility</code> Objekte innerhalb einer ArrayList zurückgegeben.
	 * 
	 * @param p : Person , welche der Responsibility zugeordnet ist.
	 * @return ArrayList aller Responsibilites welchen eine bestimmte Person
	 *         zugeordnet ist wird zurückgegeben.
	 */

	public ArrayList<Responsibility> findByPerson(Person p) {

		ArrayList<Responsibility> result = new ArrayList<Responsibility>();

		Connection con = DBConnection.connection();
		try {

			Statement stmt = con.createStatement();

			ResultSet rs = stmt.executeQuery(
					"Select Responsibilty_ID,Store_ID FROM Responsibility" + "WHERE Person_ID= " + p.getId());

			while (rs.next()) {
				Responsibility r = new Responsibility();
				r.setId(rs.getInt("Responsibility_id"));
				r.setBuyerID(rs.getInt("Person_id"));

				result.add(r);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return result;

	}

	/**
	 * Methode um ein Responsibility-Datensatz in der Datenbank zu löschen.
	 * 
	 * @param rs : Die zu loeschende Responsibility wird uebergeben
	 */

	public void delete(Responsibility rs) {

		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();
			stmt.executeUpdate("DELETE FROM Responsibility " + "WHERE Responsibility_ID = " + rs.getId());

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Methode um ein Responsibility-Datensatz in der Datenbank zu löschen.
	 * 
	 * @param id : Die ID der zu löschenden Responsibility wird uebergeben
	 */

	public void deletebyID(int id) {

		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();
			stmt.executeUpdate("DELETE FROM Responsibility " + "WHERE Responsibility_ID = " + id);

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Methode um ein Responsibility-Datensatz in der Datenbank zu löschen.
	 * 
	 * @param id : Die ID der Shoppinglist welche mit der Responsibilty verbunden
	 *           wird uebergeben.
	 */

	public void deletebySLID(int id) {

		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();
			stmt.executeUpdate("DELETE FROM Responsibility " + "WHERE Shoppinglist_ID = " + id);

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Wiederholtes Schreiben eines <code>Responsibility</code> Objekts in die
	 * Datenbank.
	 * 
	 * @param rs : Die zu aktualisierende Responsibilty wird übergeben
	 * @return: die aktualisierte Responsibility wird zurückgegeben
	 * 
	 */

	public Responsibility updateResponsibility(Responsibility rs) {

		Connection con = DBConnection.connection();

		try {
			PreparedStatement stmt = con.prepareStatement(
					"UPDATE Responsibility SET Changedate = ?, Shoppinglist_ID = ?, Store_ID = ?, Person_ID = ? WHERE Responsibility_ID ="
							+ rs.getId());

			stmt.setTimestamp(1, rs.getChangedate());
			stmt.setInt(2, rs.getSlID());
			stmt.setInt(3, rs.getStoreID());
			stmt.setInt(4, rs.getBuyerID());
			stmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;

	}

	/**
	 * Methode um ein Responsibilty Objekt in der Datenbank zu speichern
	 * 
	 * @param rl : eine neue zu speichernde Responsibilty in der Datenbank wird
	 *           uebergeben
	 * @return: die neu gespeicherte Responsibilty wird zurückgegeben
	 * 
	 */

	public Responsibility insert(Responsibility rl) {

		Connection con = DBConnection.connection();

		try {

			Statement stmt = con.createStatement();

			ResultSet rs = stmt.executeQuery("SELECT MAX(Responsibility_ID) AS maxid " + "FROM Responsibility");

			if (rs.next()) {

				rl.setId(rs.getInt("maxid") + 1);

			}

			PreparedStatement stmt2 = con.prepareStatement(
					"INSERT INTO Responsibility (Responsibility_ID, Creationdate, Changedate, Shoppinglist_ID, Store_ID, Person_ID) VALUES (?, ?, ?, ?, ?, ?)",
					Statement.RETURN_GENERATED_KEYS);

			stmt2.setInt(1, rl.getId());
			stmt2.setTimestamp(2, rl.getCreationdate());
			stmt2.setTimestamp(3, rl.getChangedate());
			stmt2.setInt(4, rl.getSlID());
			stmt2.setInt(5, rl.getStoreID());
			stmt2.setInt(6, rl.getBuyerID());

			stmt2.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return rl;
	}

	public void deleteByPersonID(Person p) {
		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();
			stmt.executeUpdate("DELETE FROM Responsibility WHERE Person_ID = " + p.getId());
			System.out.println(stmt);

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
