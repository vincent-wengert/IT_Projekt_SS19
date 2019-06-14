package de.hdm.softwarepraktikum.server.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import de.hdm.softwarepraktikum.shared.bo.Group;
import de.hdm.softwarepraktikum.shared.bo.ListItem;
import de.hdm.softwarepraktikum.shared.bo.ListItem.Unit;
import de.hdm.softwarepraktikum.shared.bo.Responsibility;
import de.hdm.softwarepraktikum.shared.bo.Item;
import de.hdm.softwarepraktikum.shared.bo.ShoppingList;

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
	

	public ListItem insert(ListItem li, Responsibility res) {
		Connection con = DBConnection.connection();
		
		try {
			
			Statement stmt = con.createStatement();
			
		/*
		 * Zun�chst schauen wir nach, welches der momentan h�chste
		 * Prim�rschl�sselwert ist.
		 */
			
			
		ResultSet rs = stmt.executeQuery("SELECT MAX(ListItem_ID) AS maxid " + "FROM ListItem");
		
		// Wenn wir etwas zur�ckerhalten, kann dies nur einzeilig sein
		if (rs.next()) {
		/*
		 * i erh�lt den bisher maximalen, nun um 1 inkrementierten
		 * Prim�rschl�ssel.
		 */
		li.setId(rs.getInt("maxid") + 1);
				
		PreparedStatement stmt2 = con.prepareStatement(
				"INSERT INTO ListItem (ListItem_ID, Unit, Amount, IsChecked, Responsibility_ID, Item_ID) " + "VALUES (?, ?, ?, ?, ?, ?)",
				Statement.RETURN_GENERATED_KEYS);
		
		stmt2.setInt(1, li.getId());
		stmt2.setString(2, li.getUnit().toString());
		stmt2.setDouble(3, li.getAmount());
		stmt2.setBoolean(4, li.getChecked());
		stmt2.setInt(5, res.getId());
		stmt2.setInt(6, li.getItemId());
		System.out.println(stmt2);
		stmt2.executeUpdate();
			
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
			
			//muss man noch verbessern zwecks responsibility
			Statement stmt = con.createStatement();
			
			stmt.executeUpdate("UPDATE listitems " + "SET item=\"" + li.getItemId() + "\", " + "amount=\""
					+ li.getAmount() + "\" " + "WHERE id=" + li.getId());
			
			
			//PreparedStatement st = con.prepareStatement("UPDATE ListItem SET Name = ?, Street = ?, Postcode = ?, City = ?,"
			//		+ " Changedate = ? WHERE Store_ID = ?");
			
			//st.setString(1, r.getName());
			//st.setString(2, r.getStreet());
			//st.setInt(3, r.getPostcode());
			//st.setString(4, r.getCity());
			//st.setTimestamp(5, r.getChangedate());
			//st.setInt(6, r.getId());
			//st.executeUpdate();
			
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
				
				stmt.executeUpdate(" DELETE FROM ListItem " + " WHERE ListItem_ID=" + li.getId());
				
			} catch(SQLException e) {
				e.printStackTrace();
			}
		}
		
		
		/**
		 * Finden eins ListItems anhand der Id
		 * @param id
		 * @return
		 */
		public ListItem findById(int id) {
			
		//Herstellung einer Verbindung zur DB-Connection
			Connection con =DBConnection.connection();
			
			try {
				//leeres SQL-Statement (JDBC) anlegen
				Statement stmt = con.createStatement();
		
				//Statement ausf�llen und als Query an die DB schicken
				ResultSet rs = stmt.executeQuery("Select id, amount, responsibility, unit, ischecked" + "WHERE id= " + id);
		
				/*
				 * Da id Prim�rschl�ssel ist, kann max. nur ein Tupel zur�ckgegeben
				 * werden. Pr�fe, ob ein Ergebnis vorliegt.
				 */
				if (rs.next()) {
					//Ergebnis-Tupel in Objekt umwandeln
					ListItem li = new ListItem();
					li.setId(rs.getInt("id"));
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
				li.setId(rs.getInt("id"));
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

		public void checkListItem(ListItem li, Boolean isChecked) {
			// TODO Auto-generated method stu
				
				Connection con = DBConnection.connection();
				try {
					PreparedStatement st = con.prepareStatement("UPDATE ListItem SET IsChecked = ?, Creationdate = ?, Changedate = ? WHERE ListItem_ID = ?");
					
					st.setBoolean(1, isChecked);
					st.setTimestamp(2, li.getCreationdate());
					st.setTimestamp(3, li.getChangedate());
					st.setInt(4, li.getId());
					st.executeUpdate();
					
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		
		public ArrayList<ListItem> findAllListItemsby(ShoppingList sl, Responsibility rl) {
			Connection con = DBConnection.connection();
			
			ArrayList<ListItem> listItems = new ArrayList<ListItem>();

			String st = "SELECT * from ListItem JOIN Responsibility \r\n" + 
					"					ON Responsibility.Responsibility_ID = ListItem.Responsibility_ID JOIN  ShoppingList \r\n" + 
					"					ON ShoppingList.ShoppingList_ID = Responsibility.Shoppinglist_ID WHERE ShoppingList.ShoppingList_ID=" +
					sl.getId();

			try {
				
				Statement stmt = con.createStatement();
				
				ResultSet rs = stmt.executeQuery(st);
				
				while (rs.next()) {
					ListItem listItem = new ListItem();
					listItem.setId(rs.getInt("ListItem_ID"));
					listItem.setUnit(listItem.getItemUnit(rs.getString("Unit")));
					listItem.setAmount(rs.getDouble("Amount"));
					listItem.setChecked(rs.getBoolean("IsChecked"));
					listItem.setItemId(rs.getInt("Item_ID"));
					
					//Ab hier Resposibility Tabelle eigentlich
					listItem.setBuyerID(rs.getInt("Person_ID"));

					listItem.setStoreID(rs.getInt("Store_ID"));
					
					listItems.add(listItem);
				}
					
				} catch (SQLException e) {
					e.printStackTrace();
					}
				
				return listItems;
				
			}

		public ArrayList<ListItem> findAllCheckedListItems(ShoppingList sl) {
			// TODO Auto-generated method stub
			Connection con = DBConnection.connection();
			
			ArrayList<ListItem> allCheckedListItems = new ArrayList<ListItem>();
	
			String st = "SELECT * from ListItem WHERE IsChecked = 'True'";
			
			try {
				
				Statement stmt = con.createStatement();
				
				ResultSet rs = stmt.executeQuery(st);
				
				while (rs.next()) {
					ListItem listItem = new ListItem();
					listItem.setId(rs.getInt("ListItem_ID"));
					listItem.setChecked(rs.getBoolean("IsChecked"));
				
					allCheckedListItems.add(listItem);
				}
					
				} catch (SQLException e) {
					e.printStackTrace();
					}
				
				return allCheckedListItems;
		}
		
		
		///TO_DO mit Joins""
		
		public ArrayList<ListItem> allcheckedListItemsbyGroup(Group g) {
			// TODO Auto-generated method stub
			Connection con = DBConnection.connection();
			
			ArrayList<ListItem> allCheckedListItems = new ArrayList<ListItem>();
	
			String st = "SELECT * from listitem WHERE slID=" + sl.getId() + "AND isChecked = 'True'";
			
			try {
				
				Statement stmt = con.createStatement();
				
				ResultSet rs = stmt.executeQuery(st);
				
				while (rs.next()) {
					ListItem listItem = new ListItem();
					listItem.setId(rs.getInt("ListItem_ID"));
					listItem.setName(rs.getString("name"));
					listItem.setAmount(rs.getDouble("amount"));
					listItem.setUnit(listItem.getItemUnit(rs.getString("unit")));
					listItem.setBuyerID(rs.getInt("buyerID"));
					listItem.setChecked(rs.getBoolean("ischecked"));
					listItem.setGrID(rs.getInt("grID"));
					listItem.setSlID(rs.getInt("slID"));
					listItem.setStoreID(rs.getInt("storeID"));
					
					allCheckedListItems.add(listItem);
				}
					
				} catch (SQLException e) {
					e.printStackTrace();
					}
				
				return allCheckedListItems;
		}
		
			
}
