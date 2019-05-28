package de.hdm.softwarepraktikum.server.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import java.util.ArrayList;

import de.hdm.softwarepraktikum.shared.bo.Item;
import de.hdm.softwarepraktikum.shared.bo.Store;

public class StoreMapper {

	/**
	   * Die Klasse StoreMapper wird nur einmal instantiiert. Man spricht hierbei
	   * von einem sogenannten <b>Singleton</b>.
	   * <p>
	   * Diese Variable ist durch den Bezeichner <code>static</code> nur einmal f�r
	   * s�mtliche eventuellen Instanzen dieser Klasse vorhanden. Sie speichert die
	   * einzige Instanz dieser Klasse.
	   * 
	   * @author Peter Thies
	   */
	
		private static StoreMapper storeMapper = null;

		
		/**
		 * Gesch�tzter Konstruktor - verhindert die M�glichkeit, mit <code>new</code>
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
				 * Zun�chst schauen wir nach, welches der momentan h�chste
				 * Prim�rschl�sselwert ist.
				 */
				ResultSet rs = stmt.executeQuery("SELECT MAX(Store_id) AS id " + "FROM Store ");

				// Wenn wir etwas zur�ckerhalten, kann dies nur einzeilig sein
				
				if (rs.next()) {
				/* 
				 * s erh�lt den bisher maximalen, nun um 1 inkrementierten
				 * Prim�rschl�ssel.
				 */
					s.setId(rs.getInt("id") + 1);

					stmt = con.createStatement();
				}
				
				// Jetzt erst erfolgt die tats�chliche Einf�geoperation
				stmt.executeUpdate("INSERT INTO store (Store_id, Name, Street, Postcode, City, Creationdate, Changedate) " + "VALUES (" + s.getId() + ",'"
						+ s.getName() + "','" + s.getStreet() + " ','" + s.getPostcode() + "','" + s.getCity()+" ','" + s.getCreationdate() +" ','" + s.getChangedate()+ "')");
			

			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			/*
			 * R�ckgabe, der evtl. korrigierten Group.
			 */
			return s;
				
		}
		
		//Eigenschaften eines Stores aendern
		public Store updateStore(Store r) {
			
			Connection con = DBConnection.connection();
			
			try {
				Statement stmt = con.createStatement();
				
				stmt.executeUpdate("UPDATE Store " + "SET name=\"" + r.getName() + "\", " + "Street=\""
						+ r.getStreet()+ "\" " + "Postcode=\""
						+ r.getPostcode()+ "\" " +"City=\""
						+ r.getCity()+ "\" " +"Changdate=\""
						+ r.getChangedate()+ "\" " +"WHERE id=" + r.getId());
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			// Um Analogie zu insert(item r) zu wahren, wird i zur�ckgegeben
					return r;
		}
		
		
		
         //Store aus Datenbank l�schen
			
		public void deleteStore(Store s) {
			
			Connection con = DBConnection.connection();

			try {
				Statement stmt = con.createStatement();

				stmt.executeUpdate("DELETE * FROM Store WHERE id=" + "'" + s.getId() + "'");

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
				
		
	
		public Store findByID(int ID) {
		
			Connection con = DBConnection.connection();
			
			
			try {
				//leeres SQL-Statement (JDBC) anlegen
				Statement stmt = con.createStatement();
				
				//Statement ausf�llen und als Query an die DB schicken
				ResultSet rs = stmt.executeQuery("SELECT Store_id, Name, Street, Postcode, City, Creationdate, Changedate FROM Store "
						+ " WHERE Store_ID = " +ID);
				while (rs.next()) {
				//Ergebnis-Tupel in Objekt umwandeln
				 Store s = new Store();
				 s.setId(rs.getInt("Store_id"));
				 s.setName(rs.getString("Name"));
				 s.setPostcode(rs.getInt("Postcode"));
				 s.setCity(rs.getString("City"));
				 s.setCreationdate(rs.getTimestamp("Creationdate"));
				 s.setChangedate(rs.getTimestamp("Changedate"));
					
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
				
				ResultSet rs = stmt.executeQuery("SELECT Store_id, Name, Street, Postcode, City, Creationdate, Changedate FROM Store "+ "ORDER BY Name");
						
				while (rs.next()) {
					
					//Ergebnis-Tupel in Objekt umwandeln
					 Store s = new Store();
					 s.setId(rs.getInt("Store_id"));
					 s.setName(rs.getString("Name"));
					 s.setPostcode(rs.getInt("Postcode"));
					 s.setCity(rs.getString("City"));
					 s.setCreationdate(rs.getTimestamp("Creationdate"));
					 s.setChangedate(rs.getTimestamp("Changedate"));
					
					//Hinzuf�gen des neuen Objekts zum Ergebnisvektor
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
