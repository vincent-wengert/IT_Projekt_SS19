package de.hdm.softwarepraktikum.server.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

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
		
	}
	
	/*
	 * Methode zum Auflisten aller Artikel.
	 */
	
	public Responsibility findbystore(Store s) {
		
	}
	
	/*
	 * Methode um einen Artikel anhand seines Objektes zu suchen.
	 */
	
	public Responsibility findByPerson(Person p) {
		
	}
	
	/*
	 * Delete Methode, um einen Artikel aus der Datenbank zu entfernen.
	 */
	
	public void delete(Responsibility rs) {
		
		Connection con = DBConnection.connection();
    	
  	  try {
    	    Statement stmt = con.createStatement();
  	    stmt.executeUpdate("DELETE FROM Responsibilty WHERE Responsibility_ID = "+rs.getBO_ID());			
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
    	Statement stmt = con.createStatement();


    	stmt.executeUpdate("UPDATE Store SET = ?, Name= ? WHERE BO_ID = ?");
    	
  	  		}
  	  catch(SQLException e) {
      		e.printStackTrace();
      	}
        return p;
		
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
		      
		      rl.setBO_ID(rs.getInt("maxid") + 1);

		      }

				
			    PreparedStatement stmt2 = con.prepareStatement(

							"INSERT INTO Responsibility (Responsibility_ID, buyer, st,sl) VALUES (?, ?, ?,?)",

							Statement.RETURN_GENERATED_KEYS);

					stmt2.setInt(1, rl.getBO_ID());
					stmt2.setString(2, rl.getBuyer().getName());
					stmt2.setString(3, rl.getSt().getName());
					stmt2.setString(4, rl.getSl().getName());
					
					
					stmt2.executeUpdate();
			
    	  }
    	  catch(SQLException e) {
        		e.printStackTrace();
        	}	
				
        return rl;
    }
		
	}

	

