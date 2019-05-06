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
	   * Diese Variable ist durch den Bezeichner <code>static</code> nur einmal für
	   * sämtliche eventuellen Instanzen dieser Klasse vorhanden. Sie speichert die
	   * einzige Instanz dieser Klasse.
	   * 
	   * @author Peter Thies
	   */
	
	private static ListItemMapper listItemMapper = null;
	
	/**
	   * Geschützter Konstruktor - verhindert die Möglichkeit, mit <code>new</code>
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
			 * Zunächst schauen wir nach, welches der momentan höchste
			 * Primärschlüsselwert ist.
			 */
			ResultSet rs = stmt.executeQuery("SELECT MAX(bo_id) AS maxbo_id " + "FROM listitem ");
			
			// Wenn wir etwas zurückerhalten, kann dies nur einzeilig sein
			if (rs.next()) {
			/*
			 * li erhält den bisher maximalen, nun um 1 inkrementierten
			 * Primärschlüssel.
			 */
			li.setBO_ID(rs.getInt("maxbo_id") + 1);
					
			stmt = con.createStatement();
							
			// Jetzt erst erfolgt die tatsächliche Einfügeoperation
			stmt.executeUpdate("INSERT INTO listitems (bo_id, item, amount, unit) " + "VALUES (" + li.getBO_ID() + ",'"
					+ li.getIt() + "','" + li.getAmount() + "','" + li.getUnit() + "')");
		}
	} catch (SQLException e) {
		e.printStackTrace();
	}
		
		/*
		 * Rückgabe des evtl. korrigierten ListItems.
		 */
		return li;
	}
	
	/**
	 * Wiederholtes Schreiben eines Objekts in die Datenbank.
	 * 
	 * @param li
	 *            das Objekt, das in die DB geschrieben werden soll
	 * @return das als Parameter übergebene Objekt
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
		
		// Um Analogie zu insert(listitem li) zu wahren, wird li zurückgegeben
				return li;
	}
	
	//Löschung eines Listitems
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
		
				//Statement ausfüllen und als Query an die DB schicken
				ResultSet rs = stmt.executeQuery("Select bo_id, amount, responsibility, unit, ischecked" + "WHERE bo_id= " + bo_id);
		
				/*
				 * Da id Primärschlüssel ist, kann max. nur ein Tupel zurückgegeben
				 * werden. Prüfe, ob ein Ergebnis vorliegt.
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
			
			//Statement ausfüllen und als Query an die DB schicken
			ResultSet rs = stmt.executeQuery("SELECT * FROM listitem");
			
			while (rs.next()){
				ListItem li = new ListItem();
				li.setBO_ID(rs.getInt("bo_id"));
				//li.setIt(rs.getString("item"));
				li.setAmount(rs.getDouble("amount"));
				//li.setUnit(rs.getInt("Unit"));
				li.setChecked(rs.getBoolean("iscchecked"));
				
				//Hinzufügen des neuen Objekts zur ArrayList
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
