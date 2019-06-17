package de.hdm.softwarepraktikum.server.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import de.hdm.softwarepraktikum.shared.bo.Item;
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
	
	/*
	 * Methode zum Auflisten aller Artikel.
	 */
	
	public ArrayList<Responsibility> findbystore(Store s) {
		
		ArrayList<Responsibility> result = new ArrayList<Responsibility>();
		
		//Herstellung einer Verbindung zur DB-Connection
		Connection con =DBConnection.connection();
		try {
			//leeres SQL-Statement (JDBC) anlegen
			Statement stmt = con.createStatement();
			
			//Statement ausf�llen und als Query an die DB schicken
			ResultSet rs = stmt.executeQuery("Select Responsibilty_ID,Store_ID FROM Responsibility" + "WHERE Store_ID= " + s.getId());
			
			/*
		     * Da id Prim�rschl�ssel ist, kann max. nur ein Tupel zur�ckgegeben
		     * werden. Pr�fe, ob ein Ergebnis vorliegt.
		     */
			while(rs.next()) {
				Responsibility r = new Responsibility();
				r.setId(rs.getInt("Responsibility_ID"));
				r.setStoreID(rs.getInt("Store_ID"));
				
				//Hinzuf�gen des neuen Objekts zum Ergebnisvektor
				result.add(r);
	}
		}catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	return result;
		
		
	}
	
	/*
	 * Methode um einen Artikel anhand seines Objektes zu suchen.
	 */
	
	public ArrayList<Responsibility> findByPerson(Person p) {
          
		ArrayList<Responsibility> result = new ArrayList<Responsibility>();
		
		//Herstellung einer Verbindung zur DB-Connection
		Connection con =DBConnection.connection();
		try {
			//leeres SQL-Statement (JDBC) anlegen
			Statement stmt = con.createStatement();
			
			//Statement ausf�llen und als Query an die DB schicken
			ResultSet rs = stmt.executeQuery("Select Responsibilty_ID,Store_ID FROM Responsibility" + "WHERE Person_ID= " + p.getId());
			
			/*
		     * Da id Prim�rschl�ssel ist, kann max. nur ein Tupel zur�ckgegeben
		     * werden. Pr�fe, ob ein Ergebnis vorliegt.
		     */
			while(rs.next()) {
				Responsibility r = new Responsibility();
				r.setId(rs.getInt("Responsibility_id"));
				r.setBuyerID(rs.getInt("Person_id"));
				
				//Hinzuf�gen des neuen Objekts zum Ergebnisvektor
				result.add(r);
	}
		}catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	return result;
		
		
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
      		e.printStackTrace();}
      	}
		
  	  
public void deletebyID(int id) {
		
		Connection con = DBConnection.connection();
    	
  	  try {
  		Statement stmt = con.createStatement();
		stmt.executeUpdate("DELETE FROM Responsibility "+ "WHERE Responsibility_ID = " + id);
			
  	  	}
  	  catch(SQLException e) {
      		e.printStackTrace();
      	}
	}
	
	/*
	 * Update Methode, um einen Artikel erneut zu schreiben.
	 */
	
	public Responsibility updateResponsibility(Responsibility rs) {
		

    	Connection con = DBConnection.connection();
    	
  	  try {
    	PreparedStatement stmt = con.prepareStatement("UPDATE Responsibility SET Changedate = ?, Shoppinglist_ID = ?, Store_ID = ?, Person_ID = ? WHERE Responsibility_ID ="+ rs.getId());
    	
    	stmt.setTimestamp(1, rs.getChangedate());
		stmt.setInt(2, rs.getSlID());
		stmt.setInt(3, rs.getStoreID());
		stmt.setInt(4, rs.getBuyerID());
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

			ResultSet rs = stmt.executeQuery("SELECT MAX(Responsibility_ID) AS maxid " + "FROM Responsibility");


		      if (rs.next()) {
		      
		      rl.setId(rs.getInt("maxid") + 1);

		      }
		      
		      PreparedStatement stmt2 = con.prepareStatement(
			"INSERT INTO Responsibility (Responsibility_ID, Creationdate, Changedate, Shoppinglist_ID, Store_ID, Person_ID) VALUES (?, ?, ?, ?, ?, ?)",
						Statement.RETURN_GENERATED_KEYS);

				stmt2.setInt(1, rl.getId());
				stmt2.setTimestamp(2,rl.getCreationdate());
				stmt2.setTimestamp(3, rl.getChangedate());
				stmt2.setInt(4, rl.getSlID());
				stmt2.setInt(5,rl.getStoreID());
				stmt2.setInt(6,rl.getBuyerID());
				
				stmt2.executeUpdate();

    	  }
    	  catch(SQLException e) {
        		e.printStackTrace();
        	}	
				
        return rl;
    }
		
	}

	

