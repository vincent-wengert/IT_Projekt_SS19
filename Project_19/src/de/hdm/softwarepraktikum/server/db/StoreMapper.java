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
	   * Diese Variable ist durch den Bezeichner <code>static</code> nur einmal für
	   * sämtliche eventuellen Instanzen dieser Klasse vorhanden. Sie speichert die
	   * einzige Instanz dieser Klasse.
	   * 
	   * @author Niklas �xle
	   */
	
		private static StoreMapper storeMapper = null;

		
		/**
		 * Geschützter Konstruktor - verhindert die Möglichkeit, mit <code>new</code>
		 * neue Instanzen dieser Klasse zu erzeugen.
		 */
		protected StoreMapper() {
		}
		
		
		/*
		 * Einhaltung der Singleton Eigenschaft des Mappers.
		 */
		
		public static StoreMapper storeMapper() {
			if (storeMapper == null) {
				storeMapper = new StoreMapper();
			}
		
			return storeMapper;
		}
	
		
		//Erstellen eines neuen Stores
			
		public Store insert(Store s) {
			Connection con = DBConnection.connection();
			
			try {

				Statement stmt = con.createStatement();
				
				/*
				 * Zunächst schauen wir nach, welches der momentan höchste
				 * Primärschlüsselwert ist.
				 */
				ResultSet rs = stmt.executeQuery("SELECT MAX(Store_ID) AS maxid " + "FROM Store ");

				// Wenn wir etwas zurückerhalten, kann dies nur einzeilig sein
				
				if (rs.next()) {
				/* 
				 * s erhält den bisher maximalen, nun um 1 inkrementierten
				 * Primärschlüssel.
				 */
					s.setId(rs.getInt("maxid") + 1);

					//stmt = con.createStatement();
				}
				
				// Jetzt erst erfolgt die tatsächliche Einfügeoperation
				//stmt.executeUpdate("INSERT INTO Store (Store_ID, Name, Street, Postcode, City, Creationdate, Changedate ) " 
				//+ "VALUES (" + s.getId() + ",'"
				//+ s.getName() + "','" + s.getStreet() + " ','" 
				//+ s.getPostcode()+ " ','" + s.getCity()
				//+ " ','" + s.getCreationdate()+ "','" + s.getChangedate() + "')");
				
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
			
			/*
			 * Rückgabe, der evtl. korrigierten Group.
			 */
			return s;
				
		}
		
		//Eigenschaften eines Stores aendern
		public Store updateStore(Store r) {
			
			Connection con = DBConnection.connection();
			
			try {
//				Statement stmt = con.createStatement();
				
//				stmt.executeUpdate("UPDATE Store " + "SET name=\"" + r.getName() + "\", " + "Street=\""
//					    + r.getStreet()+ "\" " + "Postcode=\""
//						+ r.getPostcode()+ "\" " +"City=\""
//						+ r.getCity()+ "\" " +"Changdate=\""
//						+ r.getChangedate()+ "\" " +"WHERE id=" + r.getId());
				
				
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
			
			// Um Analogie zu insert(item r) zu wahren, wird i zurï¿½ckgegeben
					return r;
		}



 //Store aus Datenbank lÃ¶schen
	
public void deleteStore(Store s) {
	
	Connection con = DBConnection.connection();

	try {
		Statement stmt = con.createStatement();

		stmt.executeUpdate("DELETE FROM Store WHERE Store_ID =" + s.getId());

	} catch (SQLException e) {
		e.printStackTrace();
	}
}
				
		
	
		public Store findByID(int ID) {
		
			Connection con = DBConnection.connection();
			
			
			try {
				//leeres SQL-Statement (JDBC) anlegen
				Statement stmt = con.createStatement();
				
				//Statement ausfüllen und als Query an die DB schicken
				ResultSet rs = stmt.executeQuery("SELECT Store_ID, Name, Street, Postcode, City, Creationdate, Changedate, Housenumber FROM Store "
						+ " WHERE Store_ID = " +ID);
				while (rs.next()) {
				//Ergebnis-Tupel in Objekt umwandeln
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
	
		//Alle Store als Array-Liste ausgeben
		public ArrayList<Store> findAllStore(){
			
			//Ergebnisvektor vorbereiten
			ArrayList<Store> result = new ArrayList<Store>();
			
			Connection con = DBConnection.connection();
			
			
			try {
				Statement stmt = con.createStatement();
				
				ResultSet rs = stmt.executeQuery("SELECT Store_ID, Name, Street, Postcode, City, Creationdate, Changedate, Housenumber FROM Store "+ "ORDER BY Name");
						
				while (rs.next()) {
					
					//Ergebnis-Tupel in Objekt umwandeln
					 Store s = new Store();
					 s.setId(rs.getInt("Store_ID"));
					 s.setName(rs.getString("Name"));
					 s.setStreet(rs.getString("Street"));
					 s.setPostcode(rs.getInt("Postcode"));
					 s.setCity(rs.getString("City"));
					 s.setCreationdate(rs.getTimestamp("Creationdate"));
					 s.setChangedate(rs.getTimestamp("Changedate"));
					 s.setHouseNumber(rs.getInt("Housenumber"));
					
					//Hinzufügen des neuen Objekts zum Ergebnisvektor
					result.add(s);

				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			return result;
		
		}
	
		//public Store findByObject(Store store) {
		//	return store;
		
		
		//}
	
	
	
}
