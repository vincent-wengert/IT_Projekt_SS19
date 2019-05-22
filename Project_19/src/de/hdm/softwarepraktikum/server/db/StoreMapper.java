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
	   * @author Peter Thies
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
				ResultSet rs = stmt.executeQuery("SELECT MAX(store_id) AS id " + "FROM store ");

				// Wenn wir etwas zurückerhalten, kann dies nur einzeilig sein
				
				if (rs.next()) {
				/* 
				 * s erhält den bisher maximalen, nun um 1 inkrementierten
				 * Primärschlüssel.
				 */
					s.setId(rs.getInt("maxstore_id") + 1);

					stmt = con.createStatement();
				}
				
				// Jetzt erst erfolgt die tatsächliche Einfügeoperation
				stmt.executeUpdate("INSERT INTO store (store_id, name, street, postcode, city) " + "VALUES (" + s.getId() + ",'"
						+ s.getName() + "','" + s.getStreet() + " ','" + s.getPostcode() + "','" + s.getCity()+ "')");
			

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
				PreparedStatement stmt = con.prepareStatement("UPDATE Store SET = ?, Name= ? WHERE BO_ID = ?");

				stmt.setString(1, r.getName());
				stmt.setString(2, r.getStreet());
				stmt.setInt(3, r.getPostcode());
				stmt.setString(4, r.getCity());
				stmt.executeUpdate();

			} catch (SQLException e) {
				e.printStackTrace();
				}
			return r;	
		}
		
		// bestimmten Store aufrufen
		/*public Store readStore(String name) {
				
		
		return Store;
		}*/
		
		//Store aus Datenbank löschen
			
		public void deleteStore(Store s) {
			
			Connection con = DBConnection.connection();

			try {
				Statement stmt = con.createStatement();

				stmt.executeUpdate("DELETE * FROM store WHERE id=" + "'" + s.getId() + "'");

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
				
		
	
		public Store findByID(int ID) {
		
			Connection con = DBConnection.connection();
			
			Store store = new Store();
			
			try {
				Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery("SELECT * FROM store "
						+ " WHERE Store_ID = " +ID);
				while (rs.next()) {
					
				//	allStores.add(rs.getStore("Store"));

				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			
			
			
			return store;
		}
	
		//Alle Store als Array-Liste ausgeben
		public ArrayList<Store> findAllStore(){
			
			Connection con = DBConnection.connection();
			ArrayList<Store> allStores = new ArrayList<Store>();
			
			try {
				Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery("SELECT * FROM store ");
						
				while (rs.next()) {
					
					allStores.add(this.findByID(rs.getInt("Store_ID")));

				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			return allStores;
		
		}
	
		public Store findByObject(Store store) {
			return store;
		
		
		}
	
	
	
}
