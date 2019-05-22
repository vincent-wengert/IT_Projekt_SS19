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

	/* 
	 * Speicherung der Instanz dieser Mapperklasse.
	 */
	
	private static ResponsibilityMapper responsibilityMapper = null;
	
	/*
	 * Konstruktor ist geschützt, um weitere Instanzierung zu verhindern.
	 */

	protected ResponsibilityMapper() {
		
	}
	
	/*
	 * Einhaltung der Singleton Eigenschaft des Mappers.
	 */
	
	public static ResponsibilityMapper responsibilityMapper() {
		if (responsibilityMapper == null) {
			responsibilityMapper = new ResponsibilityMapper();
		}

		return responsibilityMapper;
	}
	
	/*
	 * Artikel anhand seiner Id zu suchen.
	 */
	
	public Responsibility findById(int id) {
		
		Connection con = DBConnection.connection();

		Responsibility rl = new Responsibility();	
		
		
		
		try {
			Statement stmt = con.createStatement();

			ResultSet rs = stmt.executeQuery("SELECT * FROM Responsibility"
							+ " WHERE Responsibility_ID = " + id);

			if (rs.next()) {

				rl.setId(rs.getInt("Responsibility_ID"));
				rl.setCreationdate(rs.getTimestamp("Creationdate"));
				rl.setChangedate(rs.getTimestamp("Changedate"));
				//rl.setBuyer(new Person(rs.getInt("Person_ID"));//???
				//rl.setSl(rs.getInt("Shoppinglist_ID"));
				//contactList.setIsShared(rs.getBoolean("IsShared"));
			}
		} catch (

		SQLException e) {
			e.printStackTrace();
			return null;
		}

		return rl;
	}
	
	/*
	 * Methode zum Auflisten aller Artikel.
	 */
	
	public Responsibility findbystore(Store s) {
		return null;
		
	}
	
	/*
	 * Methode um einen Artikel anhand seines Objektes zu suchen.
	 */
	
	public Responsibility findByPerson(Person p) {
		return null;
		
	}
	
	/*
	 * Delete Methode, um einen Artikel aus der Datenbank zu entfernen.
	 */
	
	public void delete(Responsibility rs) {
		
		Connection con = DBConnection.connection();
    	
  	  try {
  		Statement stmt = con.createStatement();
		stmt.executeUpdate("DELETE FROM Responsibility "+ "WHERE Responsibility_ID = " + rs.getId());
			
  	  	}
  	  catch(SQLException e) {
      		e.printStackTrace();
      	}
		
	}
	
	/*
	 * Update Methode, um einen Artikel erneut zu schreiben.
	 */
	
	public Responsibility update(Responsibility rs) {
		

    	Connection con = DBConnection.connection();
    	
  	  try {
    	PreparedStatement stmt = con.prepareStatement("UPDATE Responsibility SET Changedate = ?, Shoppinglist_ID = ?, Store_ID = ?, Person_ID = ? WHERE Responsibility_ID ="+ rs.getId());
    	
    	//stmt.setTimestamp(1, rs.getChangedate());
		stmt.setInt(2, rs.getSl().getId());
		stmt.setInt(3, rs.getSt().getId());
		stmt.setInt(4, rs.getBuyer().getId());
		stmt.executeUpdate();
    	
  	  		}
  	  catch(SQLException e) {
      		e.printStackTrace();
      	}
        return rs;
		
	}
	
	/*
	 * Insert Methode, um einen neuen Artikel der Datenbank hinzuzufügen.
	 */
	
	public Responsibility insert(Responsibility rl) {
		
		Connection con = DBConnection.connection();
		
		try {
			 
			Statement stmt = con.createStatement();

			ResultSet rs = stmt.executeQuery("SELECT MAX(Responisbility_ID) AS maxid " + "FROM Responsibility");


		      if (rs.next()) {
		      
		      rl.setId(rs.getInt("maxid") + 1);

		      }
		      
		      PreparedStatement stmt2 = con.prepareStatement(
			"INSERT INTO Responsibility (Responsibility_ID,Creationdate,Changedate,Shoppinglist_ID,Store_ID,Person_ID) VALUES (?, ?, ?,?, ?, ?)",
						Statement.RETURN_GENERATED_KEYS);

				stmt2.setInt(1, rl.getId());
				//stmt2.setTimestamp(2,rl.getCreationdate());
				//stmt2.setTimestamp(3, rl.getChangedate());
				stmt2.setInt(4, rl.getSl().getId());
				stmt2.setInt(5,rl.getSt().getId());
				stmt2.setInt(6,rl.getBuyer().getId());
				
				stmt2.executeUpdate();

    	  }
    	  catch(SQLException e) {
        		e.printStackTrace();
        	}	
				
        return rl;
    }
		
	}

	

