package de.hdm.softwarepraktikum.server.db;

import java.sql.*;
import java.util.ArrayList;

import de.hdm.softwarepraktikum.shared.bo.Group;
import de.hdm.softwarepraktikum.shared.bo.Item;
import de.hdm.softwarepraktikum.shared.bo.Person;
import de.hdm.softwarepraktikum.shared.bo.ShoppingList;
import de.hdm.softwarepraktikum.server.db.GroupMapper;


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
			
			PreparedStatement stmt = con.prepareStatement("DELETE FROM Person " + "WHERE Person_ID= ? ");
			stmt.executeUpdate("DELETE FROM Person WHERE Person_ID =" + person.getId());
			
			stmt.setInt(1, person.getId());
			stmt.executeUpdate();

			//Erst Wenn alle Statements fehlerfrei ausgeführt wurden, wird commited.
			con.commit();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}

}

	public Person update(Person person) {
		Connection con = DBConnection.connection();
		String sql = "UPDATE Person SET Changedate= ?, Name= ? WHERE Person_ID = ? ";
		java.sql.Timestamp sqlDateChange = new java.sql.Timestamp(person.getChangedate().getTime());
		try {
			
			//Setzt den AutoCommit auf false, um das sichere Schreiben in die Datenbank zu gewährleisten.
			con.setAutoCommit(false);
			
			PreparedStatement stmt = con.prepareStatement(sql);
			
			stmt.setTimestamp(1, sqlDateChange);
			stmt.setString(2, person.getName());
			stmt.setInt(3, person.getId());
			stmt.executeUpdate();
			
			System.out.println("Update successfully executed.");
			
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
		java.sql.Timestamp sqlDateCreation = new java.sql.Timestamp(person.getCreationdate().getTime());
		java.sql.Timestamp sqlDateChange = new java.sql.Timestamp(person.getChangedate().getTime());

		try {

			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT MAX(Person_ID) AS maxid " + "FROM Person ");

			if (rs.next()) {

				person.setId(rs.getInt("maxid") + 1);
			
			//Setzt den AutoCommit auf false, um das sichere Schreiben in die Datenbank zu gewährleisten.
			con.setAutoCommit(false);
			
			PreparedStatement stmt2 = con.prepareStatement(
					"INSERT INTO Person (Person_ID, Creationdate, Changedate, Name, Gmail) "+ "VALUES (?, ?, ?, ?, ?)",
					Statement.RETURN_GENERATED_KEYS);
			
			//Welche Attribute kommen alle in die DB? Muessen hier ggf hinzugefuegt werden. 
			stmt2.setInt(1, person.getId());
			stmt2.setTimestamp(2, sqlDateCreation);
			stmt2.setTimestamp(3, sqlDateChange);
			stmt2.setString(4, person.getName());
			stmt2.setString(5, person.getGmail());
			System.out.println(stmt2);
			stmt2.executeUpdate();

			}
		}
		catch (SQLException e) {
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

		//Herstellung einer Verbindung zur DB-Connection
		Connection con = DBConnection.connection();
		
		Person p = new Person();

		try {

			PreparedStatement stmt = con.prepareStatement("SELECT * FROM Person WHERE Person_ID = ?");
			
			stmt.setInt(1, id);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {

				//Welche Attribute kommen alle in die DB? Muessen hier ggf hinzugefuegt werden. 
				Person person = new Person();
				person.setId(rs.getInt("Person_ID"));
				person.setName(rs.getString("Name"));
				person.setGmail(rs.getString("Gmail"));
				person.setCreationdate(rs.getTimestamp("Creationdate"));
				person.setChangedate(rs.getTimestamp("Changedate"));
				p = person;
				
				return person;
			}

		} catch (SQLException e) {
			e.printStackTrace();
			
		}
		return p;
	}

	public ArrayList<Person> findAll() {
		Connection con = DBConnection.connection();

		ArrayList<Person> persons = new ArrayList<Person>();

		try {

			Statement stmt = con.createStatement();

			//Welche Attribute kommen alle in die DB? Muessen hier ggf hinzugefuegt werden. 
			ResultSet rs = stmt.executeQuery(
					"SELECT * FROM person ORDER BY Name ASC ");

			while (rs.next()) {

				//Welche Attribute kommen alle in die DB? Muessen hier ggf hinzugefuegt werden. 
				Person person = new Person();
				person.setId(rs.getInt("Person_ID"));
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
	 * Finden einer Person anhand von ihrem Namen.
	 * @param name
	 * @return eine Arraylist mit Personen, deren Name der Eingabe ähnelt
	 */
	public ArrayList<Person> findByName(String name){
		Connection con = DBConnection.connection();
		ArrayList<Person> result = new ArrayList<Person>();
		
		try {
			Statement stmt = con.createStatement();
			
			ResultSet rs = stmt.executeQuery("SELECT Person_ID, Name " + "FROM Person " + "WHERE Name = '" + name + "'");
			
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
	
	/**
	 * Auslesen der zugehörigen <code>Group</code>-Objekte zu einer gegebenen Person.
	 * @param p
	 * @return
	 */
	public ArrayList<Group> getGroupsOf(Person p){
		
		/*
		 * Hier wird auf den GroupMapper zugegriffen, welchem der im Person-Objekt
		 * enthaltende Primärschlüssel übergeben wird. Der PersonMapper löst die ID
		 * in eine Reihe von Group-Objekten auf.
		 */
		
		return GroupMapper.groupMapper().findByMember(p);
		}
	
	/**
	 * Auslesen der zugehörigen <code>ShoppingList</code>-Objekte zu einer gegebenen Person.
	 * @param p
	 * @return
	 */
	public ArrayList<ShoppingList> getShoppingListsOf(Person p){
		
		/*
		 * Hier wird auf den ShoppingListMapper zugegriffen, welchem der im Person-Objekt
		 * enthaltende Primärschlüssel übergeben wird. Der PersonMapper löst die ID
		 * in eine Reihe von ShoppingList-Objekten auf.
		 */
		return ShoppingListMapper.shoppinglistMapper().findByMember(p);
	}
	
	public ArrayList<Person> findAllGroupMembers(){
		return null;
		
	}
	public ArrayList<Item> allFavoriteItems() {
		// TODO Auto-generated method stub
		return null;
	}
	
	//RemovefavoriteItem machen, Item aus ArrayList entfernen

	
	
	}

