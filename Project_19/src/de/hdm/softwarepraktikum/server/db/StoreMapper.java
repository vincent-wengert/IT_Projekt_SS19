package de.hdm.softwarepraktikum.server.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import java.util.ArrayList;

import de.hdm.softwarepraktikum.shared.bo.Store;

public class StoreMapper {

	/**
	   * Die Klasse StoreMapper wird nur einmal instantiiert. Man spricht hierbei
	   * von einem sogenannten <b>Singleton</b>.
	   * <p>
	   * Diese Variable ist durch den Bezeichner <code>static</code> nur einmal fuer
	   * saemtliche eventuellen Instanzen dieser Klasse vorhanden. Sie speichert die
	   * einzige Instanz dieser Klasse.
	   * 
	   * @author Niklas �xle
	   */
	
		private static StoreMapper storeMapper = null;

		
		/**
		 * Geschuetzter Konstruktor - verhindert die Moeglichkeit, mit <code>new</code>
		 * neue Instanzen dieser Klasse zu erzeugen.
		 */
		protected StoreMapper() {
		}
		
		
		
		/**
		 * Einhaltung der Singleton Eigenschaft des Mappers.
		 * @return: gibt den StoreMapper zurueck
		 */ 
		
		public static StoreMapper storeMapper() {
			if (storeMapper == null) {
				storeMapper = new StoreMapper();
			}
		
			return storeMapper;
		}
	
		
		/**
		 * Methode um ein Store Objekt in der Datenbank zu speichern
		 * @param s : ein neuer zu speichernder Store in der Datenbank wird uebergeben
		 * @return: der neu gespeicherte Store wird zur�ckgegeben
		 * 
		 */
	      public Store insert(Store s) {
			Connection con = DBConnection.connection();
			
			try {

				Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery("SELECT MAX(Store_ID) AS maxid " + "FROM Store ");

				
				if (rs.next()) {
				
					s.setId(rs.getInt("maxid") + 1);

				}
				
				
				PreparedStatement stmt2 = con.prepareStatement(
				"INSERT INTO Store (Store_ID, Name, Street, Postcode, City, Creationdate, Changedate, Housenumber) VALUES (?, ?, ?, ?, ?, ?, ?, ?)",
				Statement.RETURN_GENERATED_KEYS);

			    stmt2.setInt(1, s.getId());
			    stmt2.setString(2, s.getName());
			    stmt2.setString(3, s.getStreet());
			    stmt2.setInt(4, s.getPostcode());
			    stmt2.setString(5, s.getCity());
				stmt2.setTimestamp(6, s.getCreationdate());
				stmt2.setTimestamp(7, s.getChangedate());
				stmt2.setInt(8, s.getHouseNumber());
			
								
				stmt2.executeUpdate();
			

			} catch (SQLException e) {
				e.printStackTrace();
			}
			return s;
				}
		
	      /**
			 * Wiederholtes Schreiben eines <code>Store</code> Objekts in die Datenbank.
			 * @param r : Der zu aktualisierende Store wird �bergeben
			 * @return: der aktualisierte Store wird zur�ckgegeben
			 * 
			 */
	      
		public Store updateStore(Store r) {
			
			Connection con = DBConnection.connection();
			
			try {
				PreparedStatement st = con.prepareStatement("UPDATE Store SET Name = ?, Street = ?, Postcode = ?, City = ?,"
						+ " Changedate = ?, Housenumber = ?  WHERE Store_ID = ?");
				
				st.setString(1, r.getName());
				st.setString(2, r.getStreet());
				st.setInt(3, r.getPostcode());
				st.setString(4, r.getCity());
				st.setTimestamp(5, r.getChangedate());
				st.setInt(6, r.getHouseNumber());
				st.setInt(7, r.getId());
				st.executeUpdate();
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
					return r;
		}



		/**
		 * Methode um ein Store-Datensatz in der Datenbank zu l�schen.
		 * @param s : Der zu loeschende Store wird uebergeben
		 */
		
public void deleteStore(Store s) {
	
	Connection con = DBConnection.connection();

	try {
		Statement stmt = con.createStatement();

		stmt.executeUpdate("DELETE FROM Store WHERE Store_ID =" + s.getId());

	} catch (SQLException e) {
		e.printStackTrace();
	}
}
				
		
/**
 * Methode um ein einzelnes <code>Store</code> Objekt anhand einer ID zu suchen.
 * 
 * @param ID :  ID des zu findenden Stores wird �bergeben.
 * @return Der anhand der id gefundene Store wird zur�ckgegeben.
 */

	public Store findByID(int ID) {
		
	Connection con = DBConnection.connection();
			
			
	try {
				
	Statement stmt = con.createStatement();
				
				
	ResultSet rs = stmt.executeQuery("SELECT Store_ID, Name, Street, Postcode, City, Creationdate, Changedate, Housenumber FROM Store "
			+ " WHERE Store_ID = " +ID);
	while (rs.next()) {
				
				 Store s = new Store();
				 s.setId(rs.getInt("Store_ID"));
				 s.setName(rs.getString("Name"));
				 s.setPostcode(rs.getInt("Postcode"));
				 s.setCity(rs.getString("City"));
				 s.setCreationdate(rs.getTimestamp("Creationdate"));
				 s.setChangedate(rs.getTimestamp("Changedate"));
				 s.setHouseNumber(rs.getInt("Housenumber"));
					
				 return s;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		
			return null;
		}
	
	 /** 
     * Methode um alle in der Datenbank vorhandenen Stores-Datens�tze abzurufen.
     * Diese werden als einzelne <code>Store</code> Objekte innerhalb einer ArrayList zur�ckgegeben.
     * 
     * @return ArrayList aller Stores wird zur�ckgegeben.
     */
		public ArrayList<Store> findAllStore(){
			
			ArrayList<Store> result = new ArrayList<Store>();
			Connection con = DBConnection.connection();
			
			
			try {
				Statement stmt = con.createStatement();
				
				ResultSet rs = stmt.executeQuery("SELECT Store_ID, Name, Street, Postcode, City, Creationdate, Changedate, Housenumber FROM Store "+ "ORDER BY Name");
						
				while (rs.next()) {
					
					 Store s = new Store();
					 s.setId(rs.getInt("Store_ID"));
					 s.setName(rs.getString("Name"));
					 s.setStreet(rs.getString("Street"));
					 s.setPostcode(rs.getInt("Postcode"));
					 s.setCity(rs.getString("City"));
					 s.setCreationdate(rs.getTimestamp("Creationdate"));
					 s.setChangedate(rs.getTimestamp("Changedate"));
					 s.setHouseNumber(rs.getInt("Housenumber"));
					
				
					result.add(s);

				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			return result;
		
		}
	
		//Methode prueft ob Listitems aus einer Gruppe mit bestimmtem Haendler in DB vorhanden
		
				public boolean checkforExisitingStores(Store s) {
					
					boolean available = false;
					Connection con = DBConnection.connection();
					
					try {
						
						Statement stmt = con.createStatement();
				
						ResultSet rs = stmt.executeQuery("SELECT Responsibility_ID FROM Responsibility WHERE Store_ID = "+s.getId());
					
							if (rs.next()) {

								available = true;

							} 
						
					
						}catch (SQLException e) {
						e.printStackTrace();
						
					}
					return available; 
					
				}

	
}
