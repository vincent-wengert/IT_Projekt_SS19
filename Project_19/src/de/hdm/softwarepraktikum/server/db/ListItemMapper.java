package de.hdm.softwarepraktikum.server.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import de.hdm.softwarepraktikum.shared.bo.Group;
import de.hdm.softwarepraktikum.shared.bo.ListItem;

public class ListItemMapper {
	
	/**
	   * Die Klasse ListItemMapper wird nur einmal instantiiert. Man spricht hierbei
	   * von einem sogenannten <b>Singleton</b>.
	   * <p>
	   * Diese Variable ist durch den Bezeichner <code>static</code> nur einmal f�r
	   * s�mtliche eventuellen Instanzen dieser Klasse vorhanden. Sie speichert die
	   * einzige Instanz dieser Klasse.
	   * 
	   * @author Peter Thies
	   */
	
	private static ListItemMapper listItemMapper = null;
	
	/**
	   * Gesch�tzter Konstruktor - verhindert die M�glichkeit, mit <code>new</code>
	   * neue Instanzen dieser Klasse zu erzeugen.
	   */
	
	protected ListItemMapper() {
		
	}
	
	/*
	 * Einhaltung der Singleton Eigenschaft des Mappers.
	 */
	
	public static ListItemMapper listitemMapper() {
		
		if(listItemMapper == null) {
			listItemMapper = new ListItemMapper();
		}
		
		return listItemMapper;
	}
	
	public ListItem insert(ListItem li) {
		Connection con = DBConnection.connection();
		
		try {
			Statement stmt = con.createStatement();
			
			/*
			 * Zun�chst schauen wir nach, welches der momentan h�chste
			 * Prim�rschl�sselwert ist.
			 */
			ResultSet rs = stmt.executeQuery("SELECT MAX(bo_id) AS maxbo_id " + "FROM listitem ");
			
			// Wenn wir etwas zur�ckerhalten, kann dies nur einzeilig sein
			if (rs.next()) {
			/*
			 * li erh�lt den bisher maximalen, nun um 1 inkrementierten
			 * Prim�rschl�ssel.
			 */
			li.setBO_ID(rs.getInt("maxbo_id") + 1);
					
			stmt = con.createStatement();
							
			// Jetzt erst erfolgt die tats�chliche Einf�geoperation
			stmt.executeUpdate("INSERT INTO listitems (bo_id, item, amount, unit) " + "VALUES (" + li.getBO_ID() + ",'"
					+ li.getIt() + "','" + li.getAmount() + "','" + li.getUnit() + "')");
		}
	} catch (SQLException e) {
		e.printStackTrace();
	}
		
		/*
		 * R�ckgabe des evtl. korrigierten ListItems.
		 */
		return li;
	}
	
	/**
	 * Wiederholtes Schreiben eines Objekts in die Datenbank.
	 * 
	 * @param li
	 *            das Objekt, das in die DB geschrieben werden soll
	 * @return das als Parameter �bergebene Objekt
	 */
	
	public ListItem update(ListItem li) {
		Connection con = DBConnection.connection();
		
		try {
			Statement stmt = con.createStatement();
			
			stmt.executeUpdate("UPDATE listitems " + "SET item=\"" + li.getIt() + "\", " + "amount=\""
					+ li.getAmount() + "\" " + "WHERE bo_id=" + li.getBO_ID());
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		// Um Analogie zu insert(listitem li) zu wahren, wird li zur�ckgegeben
				return li;
	}
	
	//L�schung eines Listitems
		public void delete(ListItem li) {
			Connection con = DBConnection.connection();
			
			try {
				Statement stmt = con.createStatement();
				
				stmt.executeUpdate("DELETE FROM listitems " + "WHERE bo_id=" + li.getBO_ID());
			} catch(SQLException e) {
				e.printStackTrace();
			}
		}
		
		
		//Finden eins ListItems anhand der BO_ID
		public ListItem findById(int bo_id) {
			
		//Herstellung einer Verbindung zur DB-Connection
			Connection con =DBConnection.connection();
			
			try {
				//leeres SQL-Statement (JDBC) anlegen
				Statement stmt = con.createStatement();
		
				//Statement ausf�llen und als Query an die DB schicken
				ResultSet rs = stmt.executeQuery("Select bo_id, amount, responsibility, unit, ischecked" + "WHERE bo_id= " + bo_id);
		
				/*
				 * Da id Prim�rschl�ssel ist, kann max. nur ein Tupel zur�ckgegeben
				 * werden. Pr�fe, ob ein Ergebnis vorliegt.
				 */
				if (rs.next()) {
					//Ergebnis-Tupel in Objekt umwandeln
					ListItem li = new ListItem();
					li.setBO_ID(rs.getInt("bo_id"));
					//li.setIt(rs.getString("item"));
					li.setAmount(rs.getDouble("amount"));
					//li.setUnit(rs.getInt("Unit"));
					li.setChecked(rs.getBoolean("iscchecked"));
			
					return li;		
}
	}catch (SQLException e) {
		e.printStackTrace();
		return null;
	}
return null;
}
		
		//Liste mit allen ListItems
		public ArrayList<ListItem> findAllListItems() {
			
			ArrayList<ListItem> result = new ArrayList<ListItem>();
			
			//Herstellung einer Verbindung zur DB-Connection
			Connection con =DBConnection.connection();
			
		try {
			//leeres SQL-Statement (JDBC) anlegen
			Statement stmt = con.createStatement();
			
			//Statement ausf�llen und als Query an die DB schicken
			ResultSet rs = stmt.executeQuery("SELECT * FROM listitem");
			
			while (rs.next()){
				ListItem li = new ListItem();
				li.setBO_ID(rs.getInt("bo_id"));
				//li.setIt(rs.getString("item"));
				li.setAmount(rs.getDouble("amount"));
				//li.setUnit(rs.getInt("Unit"));
				li.setChecked(rs.getBoolean("iscchecked"));
				
				//Hinzuf�gen des neuen Objekts zur ArrayList
				result.add(li);
			}
		}catch (SQLException e) {
			e.printStackTrace();
			
			} return result;
		}
		
		
		//Finden eines ListItems anhand eines Objects
		public ListItem findbyObject(ListItem li) {
			
			/*
			 * To be done
			 */
		}
}
