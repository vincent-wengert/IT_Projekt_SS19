package de.hdm.softwarepraktikum.server.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import java.util.ArrayList;
import de.hdm.softwarepraktikum.shared.Retailer;

public class RetailerMapper {

	
	
		//Instanzierung
		private static RetailerMapper retailerMapper = null;

		
		protected RetailerMapper() {
		}
		
		
		// Sicherstellung Singleton
		public static RetailerMapper retailerMapper() {
			if (retailerMapper == null) {
				retailerMapper = new RetailerMapper();
			}
		
			return retailerMapper;
		}
	
		
		//Erstellen eines neuen Retailers
			
		public void insertRetailer() {
			
			Connection con = DBConnection.connection();
			
			try {

				Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery("SELECT MAX(Retailer_ID) AS id " + "FROM Retailer ");

				if (rs.next()) {

					Retailer.setBO_ID(rs.getInt("Retailer_ID") + 1);

				}

				PreparedStatement stmt2 = con.prepareStatement("INSERT INTO Retailer (Retailer_ID, Name, Street, Postcode, City) VALUES (?, ?, ?)",
						Statement.RETURN_GENERATED_KEYS);

				stmt2.setInt(1, Retailer.getBO_ID());
				stmt2.setString(2, Retailer.getName());
				stmt2.setString(3, Retailer.getStreet());
				stmt2.setInt(4, Retailer.getPostcode());
				stmt2.setString(5, Retailer.getCity());
				stmt2.executeUpdate();

			} catch (SQLException e) {
				e.printStackTrace();

			}
			
			
				
		}
		
		//Eigenschaften eines Retailers aendern
		public Retailer updateRetailer(Retailer r) {
			
			Connection con = DBConnection.connection();

			try {
				PreparedStatement stmt = con.prepareStatement("UPDATE Retailer SET = ?, Name= ? WHERE BO_ID = ?");

				stmt.setString(2, Retailer.getName());
				stmt.setString(3, Retailer.getStreet());
				stmt.setInt(3, Retailer.getPostcode());
				stmt.setString(3, Retailer.getCity());
				stmt.executeUpdate();

			} catch (SQLException e) {
				e.printStackTrace();
				}
			return r;	
		}
		
		// bestimmten Retailer aufrufen
		/*public Retailer readRetailer(String name) {
				
		
		return Retailer;
		}*/
		
		//Retailer aus Datenbank löschen
			
		public void deleteRetailer() {
			
			Connection con = DBConnection.connection();

			try {
				Statement stmt = con.createStatement();

				stmt.executeUpdate("DELETE * FROM retailer WHERE id=" + "'" + Retailer.getId() + "'");

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
				
		}
	
		public Retailer findByID(int ID) {
		
			
			return retailer;
		}
	
		//Alle Retailer als Array-Liste ausgeben
		public ArrayList<Retailer> findAllRetailer(){
			
			onnection con = DBConnection.connection();
			ArrayList<Retailer> allRetailers = new ArrayList<Retailer>();
			
			try {
				Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery("SELECT * FROM retailer "
						+ " WHERE Retailer_ID = " + Retailer.getBO_ID());
				while (rs.next()) {

					allRetailers.add(rs.getRetailer("Retailer"));

				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			return allRetailers;
		
		}
	
		public Retailer findByObject(Retailer retailer) {
		
		
		}
	
	
	
}
