package de.hdm.softwarepraktikum.server.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import java.util.ArrayList;

import de.hdm.softwarepraktikum.shared.bo.Store;

public class StoreMapper {

	
	
		//Instanzierung
		private static StoreMapper storeMapper = null;

		
		protected StoreMapper() {
		}
		
		
		// Sicherstellung Singleton
		public static StoreMapper storeMapper() {
			if (storeMapper == null) {
				storeMapper = new StoreMapper();
			}
		
			return storeMapper;
		}
	
		
		//Erstellen eines neuen Stores
			
		public void insertStore() {
			
			Connection con = DBConnection.connection();
			
			try {

				Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery("SELECT MAX(Store_ID) AS id " + "FROM Store ");

				if (rs.next()) {

					Store.setBO_ID(rs.getInt("Store_ID") + 1);

				}

				PreparedStatement stmt2 = con.prepareStatement("INSERT INTO Store (Store_ID, Name, Street, Postcode, City) VALUES (?, ?, ?)",
						Statement.RETURN_GENERATED_KEYS);

				stmt2.setInt(1, Store.getBO_ID());
				stmt2.setString(2, Store.getName());
				stmt2.setString(3, Store.getStreet());
				stmt2.setInt(4, Store.getPostcode());
				stmt2.setString(5, Store.getCity());
				stmt2.executeUpdate();

			} catch (SQLException e) {
				e.printStackTrace();

			}
			
			
				
		}
		
		//Eigenschaften eines Stores aendern
		public Store updateStore(Store r) {
			
			Connection con = DBConnection.connection();

			try {
				PreparedStatement stmt = con.prepareStatement("UPDATE Store SET = ?, Name= ? WHERE BO_ID = ?");

				stmt.setString(1, Store.getName());
				stmt.setString(2, Store.getStreet());
				stmt.setInt(3, Store.getPostcode());
				stmt.setString(4, Store.getCity());
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
			
		public void deleteStore() {
			
			Connection con = DBConnection.connection();

			try {
				Statement stmt = con.createStatement();

				stmt.executeUpdate("DELETE * FROM store WHERE id=" + "'" + Store.getBO_ID() + "'");

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
				
		}
	
		public Store findByID(int ID) {
		
			
			return ;
		}
	
		//Alle Store als Array-Liste ausgeben
		public ArrayList<Store> findAllStore(){
			
			onnection con = DBConnection.connection();
			ArrayList<Store> allStores = new ArrayList<Store>();
			
			try {
				Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery("SELECT * FROM store "
						+ " WHERE Store_ID = " + Store.getBO_ID());
				while (rs.next()) {

					allStores.add(rs.getStore("Store"));

				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			return allStores;
		
		}
	
		public Store findByObject(Store store) {
		
		
		}
	
	
	
}
