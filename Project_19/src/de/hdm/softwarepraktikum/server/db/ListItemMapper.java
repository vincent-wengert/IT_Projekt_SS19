package de.hdm.softwarepraktikum.server.db;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;

import de.hdm.softwarepraktikum.shared.bo.Group;
import de.hdm.softwarepraktikum.shared.bo.Item;
import de.hdm.softwarepraktikum.shared.bo.ListItem;
import de.hdm.softwarepraktikum.shared.bo.Person;
import de.hdm.softwarepraktikum.shared.bo.Responsibility;
import de.hdm.softwarepraktikum.shared.bo.ShoppingList;
import de.hdm.softwarepraktikum.shared.bo.Store;

public class ListItemMapper {

	/**
	 * Die Klasse ListItemMapper wird nur einmal instantiiert. Man spricht hierbei
	 * von einem sogenannten <b>Singleton</b>.
	 * <p>
	 * Diese Variable ist durch den Bezeichner <code>static</code> nur einmal fuer
	 * saemtliche eventuellen Instanzen dieser Klasse vorhanden. Sie speichert die
	 * einzige Instanz dieser Klasse.
	 * 
	 * @author Bruno Herceg & Niklas Oexle
	 */

	private static ListItemMapper listItemMapper = null;

	/**
	 * Geschuetzter Konstruktor - verhindert die Moeglichkeit, mit <code>new</code>
	 * neue Instanzen dieser Klasse zu erzeugen.
	 */

	protected ListItemMapper() {

	}

	/*
	 * Einhaltung der Singleton Eigenschaft des Mappers.
	 */

	public static ListItemMapper listitemMapper() {

		if (listItemMapper == null) {
			listItemMapper = new ListItemMapper();
		}

		return listItemMapper;
	}

	public ListItem insert(ListItem li, Responsibility res) {
		Connection con = DBConnection.connection();

		try {

			Statement stmt = con.createStatement();

			/*
			 * Zunaechst schauen wir nach, welches der momentan hoechste
			 * Primaerschluesselwert ist.
			 */

			ResultSet rs = stmt.executeQuery("SELECT MAX(ListItem_ID) AS maxid " + "FROM ListItem");

			// Wenn wir etwas zurueckerhalten, kann dies nur einzeilig sein
			if (rs.next()) {
				/*
				 * i erhaelt den bisher maximalen, nun um 1 inkrementierten Primaerschluessel.
				 */
				li.setId(rs.getInt("maxid") + 1);

				PreparedStatement stmt2 = con.prepareStatement(
						"INSERT INTO ListItem (ListItem_ID, Unit, Amount, BoughtOn, Responsibility_ID, Item_ID) "
								+ "VALUES (?, ?, ?, ?, ?, ?)",
						Statement.RETURN_GENERATED_KEYS);

				stmt2.setInt(1, li.getId());
				stmt2.setString(2, li.getUnit());
				stmt2.setDouble(3, li.getAmount());

				if (li.getChecked() == true) {
					stmt2.setTimestamp(4, li.getChangedate());
				} else {
					stmt2.setTimestamp(4, null);
				}
				stmt2.setInt(5, res.getId());
				stmt2.setInt(6, li.getItemId());
				System.out.println(stmt2);
				stmt2.executeUpdate();

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		/*
		 * Rueckgabe des evtl. korrigierten ListItems.
		 */
		return li;
	}

	/**
	 * Wiederholtes Schreiben eines Objekts in die Datenbank.
	 * 
	 * @param li das Objekt, das in die DB geschrieben werden soll
	 * @return das als Parameter uebergebene Objekt
	 */

	public ListItem update(ListItem li) {
		Connection con = DBConnection.connection();

		try {

			PreparedStatement st = con.prepareStatement("UPDATE ListItem SET Unit= ?, Amount= ? WHERE ListItem_ID= ?");

			st.setString(1, li.getUnit());
			st.setDouble(2, li.getAmount());
			st.setInt(3, li.getId());
			System.out.println(st);
			st.executeUpdate();

			PreparedStatement st2 = con
					.prepareStatement("UPDATE Responsibility SET Store_ID= ?, Person_ID= ? WHERE Responsibility_ID= ?");

			st2.setInt(1, li.getStoreID());
			st2.setInt(2, li.getBuyerID());
			st2.setInt(3, li.getResID());
			st2.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		// Um Analogie zu insert(listitem li) zu wahren, wird li zurueckgegeben
		return li;
	}

	// Loeschung eines Listitems
	public void delete(ListItem li) {
		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();

			stmt.executeUpdate(" DELETE FROM ListItem " + " WHERE ListItem_ID=" + li.getId());

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Finden eins ListItems anhand der Id
	 * 
	 * @param id
	 * @return
	 */
	public ListItem findById(int id) {

		// Herstellung einer Verbindung zur DB-Connection
		Connection con = DBConnection.connection();

		try {
			// leeres SQL-Statement (JDBC) anlegen
			Statement stmt = con.createStatement();

			// Statement ausf�llen und als Query an die DB schicken
			ResultSet rs = stmt.executeQuery("Select id, amount, responsibility, unit, ischecked" + "WHERE id= " + id);

			/*
			 * Da id Prim�rschl�ssel ist, kann max. nur ein Tupel zur�ckgegeben werden.
			 * Pr�fe, ob ein Ergebnis vorliegt.
			 */
			if (rs.next()) {
				// Ergebnis-Tupel in Objekt umwandeln
				ListItem li = new ListItem();
				li.setId(rs.getInt("id"));
				// li.setIt(rs.getString("item"));
				li.setAmount(rs.getDouble("amount"));
				// li.setUnit(rs.getInt("Unit"));
				li.setChecked(rs.getBoolean("iscchecked"));

				return li;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return null;
	}

	// Liste mit allen ListItems
	/**
	 * Methode, um alle ListItems zu finden.
	 * 
	 * @return ArrayList mit <code>ListItem</code>-Objekten
	 */
	public ArrayList<ListItem> findAllListItems() {

		ArrayList<ListItem> result = new ArrayList<ListItem>();

		// Herstellung einer Verbindung zur DB-Connection
		Connection con = DBConnection.connection();

		try {
			// leeres SQL-Statement (JDBC) anlegen
			Statement stmt = con.createStatement();

			// Statement ausf�llen und als Query an die DB schicken
			ResultSet rs = stmt.executeQuery("SELECT * FROM listitem");

			while (rs.next()) {
				ListItem li = new ListItem();
				li.setId(rs.getInt("id"));
				// li.setIt(rs.getString("item"));
				li.setAmount(rs.getDouble("amount"));
				// li.setUnit(rs.getInt("Unit"));
				li.setChecked(rs.getBoolean("iscchecked"));

				// Hinzuf�gen des neuen Objekts zur ArrayList
				result.add(li);
			}
		} catch (SQLException e) {
			e.printStackTrace();

		}
		return result;
	}

	/**
	 * Methode, um das abhaken eines <code>ListItem</code>-Objekts zu speichern.
	 * 
	 * @param li
	 */
	public void checkListItem(ListItem li) {

		Connection con = DBConnection.connection();
		try {
			PreparedStatement st = con.prepareStatement("UPDATE ListItem SET BoughtOn = ? WHERE ListItem_ID = ?");

			if (li.getChecked() == true) {
				System.out.println("true");
				st.setTimestamp(1, li.getChangedate());
			} else {
				System.out.println("false");
				st.setTimestamp(1, null);
			}
			st.setInt(2, li.getId());
			System.out.println(st);
			st.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Methode, um alle <code>ListItem</code>-Objekte einer ShoppingList zu finden.
	 * 
	 * @param sl
	 * @return ArrayList mit allen ListItems einer ShoppingList
	 */
	public ArrayList<ListItem> findAllListItemsby(ShoppingList sl) {
		Connection con = DBConnection.connection();

		ArrayList<ListItem> listItems = new ArrayList<ListItem>();

		String st = "SELECT * from ListItem JOIN Responsibility \r\n"
				+ "					ON Responsibility.Responsibility_ID = ListItem.Responsibility_ID JOIN  ShoppingList \r\n"
				+ "					ON ShoppingList.ShoppingList_ID = Responsibility.Shoppinglist_ID WHERE ShoppingList.ShoppingList_ID="
				+ sl.getId();

		try {

			Statement stmt = con.createStatement();

			ResultSet rs = stmt.executeQuery(st);

			while (rs.next()) {
				ListItem listItem = new ListItem();
				listItem.setId(rs.getInt("ListItem_ID"));
				listItem.setUnit(rs.getString("Unit"));
				listItem.setAmount(rs.getDouble("Amount"));

				if (rs.getTimestamp("BoughtOn") != null) {
					listItem.setChecked(true);
				} else {
					listItem.setChecked(false);
				}

				listItem.setItemId(rs.getInt("Item_ID"));
				listItem.setResID(rs.getInt("Responsibility_ID"));

				// Ab hier Resposibility Tabelle eigentlich
				listItem.setBuyerID(rs.getInt("Person_ID"));
				listItem.setStoreID(rs.getInt("Store_ID"));

				listItems.add(listItem);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return listItems;

	}

	/**
	 * Methode, um alle abgehakten <code>ListItem</code>-Objekte einer ShoppingList
	 * zu finden.
	 * 
	 * @param sl
	 * @return ArrayList mit allen abgehakten ListItems einer ShoppingList
	 */
	public ArrayList<ListItem> findAllCheckedListItems(ShoppingList sl) {
		// TODO Auto-generated method stub
		Connection con = DBConnection.connection();

		ArrayList<ListItem> allCheckedListItems = new ArrayList<ListItem>();

		String st = "SELECT * from ListItem WHERE ";

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

	/**
	 * Methode um abgehakte <code>ListItem</code>-Objekte einer Gruppe in einem
	 * bestimmten Zeitraum zu finden.
	 * 
	 * @param groupId
	 * @param start
	 * @param end
	 * @return ArrayList mit abgehakten ListItems einer Gruppe in einem bestimmten
	 *         Zeitraum
	 */
	public ArrayList<ListItem> getCheckedListItemsOfGroupBetweenDates(int groupId, Timestamp start, Timestamp end) {
		Connection con = DBConnection.connection();

		ArrayList<ListItem> listItems = new ArrayList<ListItem>();

		String st = "SELECT * from ListItem JOIN Responsibility ON Responsibility.Responsibility_ID = ListItem.Responsibility_ID"
				+ " JOIN  ShoppingList ON ShoppingList.ShoppingList_ID = Responsibility.Shoppinglist_ID"
				+ " JOIN `Group` ON `Group`.Group_ID = ShoppingList.Group_ID WHERE `Group`.Group_ID= " + groupId
				+ " AND ListItem.BoughtOn BETWEEN \"" + start + " \"AND \" " + end + "\""
				+ " AND BoughtOn IS NOT NULL ORDER BY Store_ID ASC";

		try {

			Statement stmt = con.createStatement();

			ResultSet rs = stmt.executeQuery(st);

			while (rs.next()) {
				ListItem listItem = new ListItem();
				listItem.setId(rs.getInt("ListItem_ID"));
				listItem.setUnit(rs.getString("Unit"));
				listItem.setAmount(rs.getDouble("Amount"));
				listItem.setItemId(rs.getInt("Item_ID"));
				listItem.setResID(rs.getInt("Responsibility_ID"));
				listItem.setChangedate(rs.getTimestamp("BoughtOn"));

				// Ab hier Resposibility Tabelle eigentlich
				listItem.setBuyerID(rs.getInt("Person_ID"));
				listItem.setGrID(rs.getInt("Group_ID"));
				listItem.setStoreID(rs.getInt("Store_ID"));

				listItems.add(listItem);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return listItems;

	}

	/**
	 * Methode um abgehakte <code>ListItem</code>-Objekte einer Gruppe zu finden.
	 * 
	 * @param groupId
	 * @return ArrayList mit abgehakten ListItems einer Gruppe
	 */
	public ArrayList<ListItem> getCheckedListItemsOfGroup(int groupId) {
		Connection con = DBConnection.connection();

		ArrayList<ListItem> listItems = new ArrayList<ListItem>();

		String st = "SELECT * from ListItem JOIN Responsibility ON Responsibility.Responsibility_ID = ListItem.Responsibility_ID"
				+ " JOIN  ShoppingList ON ShoppingList.ShoppingList_ID = Responsibility.Shoppinglist_ID"
				+ " JOIN `Group` ON `Group`.Group_ID = ShoppingList.Group_ID WHERE `Group`.Group_ID= " + groupId
				+ " AND BoughtOn IS NOT NULL ORDER BY Store_ID ASC";

		try {

			Statement stmt = con.createStatement();

			ResultSet rs = stmt.executeQuery(st);

			while (rs.next()) {
				ListItem listItem = new ListItem();
				listItem.setId(rs.getInt("ListItem_ID"));
				listItem.setUnit(rs.getString("Unit"));
				listItem.setAmount(rs.getDouble("Amount"));
				listItem.setItemId(rs.getInt("Item_ID"));
				listItem.setResID(rs.getInt("Responsibility_ID"));
				listItem.setChangedate(rs.getTimestamp("BoughtOn"));

				// Ab hier Resposibility Tabelle eigentlich
				listItem.setBuyerID(rs.getInt("Person_ID"));
				listItem.setGrID(rs.getInt("Group_ID"));
				listItem.setStoreID(rs.getInt("Store_ID"));

				listItems.add(listItem);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return listItems;

	}

	/**
	 * Methode um abgehakte <code>ListItem</code>-Objekte einer Person in einem
	 * bestimmten Zeitraum zu finden.
	 * 
	 * @param personId
	 * @param start
	 * @param end
	 * @return ArrayList mit abgehakten ListItems einer Person in einem bestimmten
	 *         Zeitraum
	 */
	public ArrayList<ListItem> getCheckedListItemsOfPersonBetweenDates(int personId, Timestamp start, Timestamp end) {
		Connection con = DBConnection.connection();

		java.sql.Timestamp from = java.sql.Timestamp.valueOf("2019-06-15 14:38:58");

		java.sql.Timestamp to = java.sql.Timestamp.valueOf("2019-06-30 18:42:58");

		ArrayList<ListItem> listItems = new ArrayList<ListItem>();

		String st = "SELECT * from ListItem JOIN Responsibility ON Responsibility.Responsibility_ID = ListItem.Responsibility_ID"
				+ " JOIN  ShoppingList ON ShoppingList.ShoppingList_ID = Responsibility.Shoppinglist_ID"
				+ " JOIN `Group` ON `Group`.Group_ID = ShoppingList.Group_ID WHERE Person_ID= " + personId
				+ " AND ListItem.BoughtOn BETWEEN \"" + from + " \"AND \" " + to + "\""
				+ " AND BoughtOn IS NOT NULL ORDER BY Store_ID ASC";

		try {

			Statement stmt = con.createStatement();

			ResultSet rs = stmt.executeQuery(st);

			while (rs.next()) {
				ListItem listItem = new ListItem();
				listItem.setId(rs.getInt("ListItem_ID"));
				listItem.setUnit(rs.getString("Unit"));
				listItem.setAmount(rs.getDouble("Amount"));
				listItem.setItemId(rs.getInt("Item_ID"));
				listItem.setResID(rs.getInt("Responsibility_ID"));
				listItem.setChangedate(rs.getTimestamp("BoughtOn"));

				// Ab hier Resposibility Tabelle eigentlich
				listItem.setBuyerID(rs.getInt("Person_ID"));
				listItem.setStoreID(rs.getInt("Store_ID"));
				listItem.setGrID(rs.getInt("Group_ID"));

				listItems.add(listItem);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return listItems;

	}

	/**
	 * Methode um abgehakte <code>ListItem</code>-Objekte einer Person zu finden.
	 * 
	 * @param personId
	 * @return ArrayList mit abgehakten ListItems einer Person
	 */
	public ArrayList<ListItem> getCheckedListItemsOfPerson(int personId) {
		Connection con = DBConnection.connection();

		ArrayList<ListItem> listItems = new ArrayList<ListItem>();

		String st = "SELECT * from ListItem JOIN Responsibility ON Responsibility.Responsibility_ID = ListItem.Responsibility_ID"
				+ " JOIN  ShoppingList ON ShoppingList.ShoppingList_ID = Responsibility.Shoppinglist_ID"
				+ " JOIN `Group` ON `Group`.Group_ID = ShoppingList.Group_ID WHERE Person_ID= " + personId
				+ " AND BoughtOn IS NOT NULL ORDER BY Store_ID ASC";

		try {

			Statement stmt = con.createStatement();

			ResultSet rs = stmt.executeQuery(st);

			while (rs.next()) {
				ListItem listItem = new ListItem();
				listItem.setId(rs.getInt("ListItem_ID"));
				listItem.setUnit(rs.getString("Unit"));
				listItem.setAmount(rs.getDouble("Amount"));
				listItem.setItemId(rs.getInt("Item_ID"));
				listItem.setResID(rs.getInt("Responsibility_ID"));
				listItem.setChangedate(rs.getTimestamp("BoughtOn"));

				// Ab hier Resposibility Tabelle eigentlich
				listItem.setBuyerID(rs.getInt("Person_ID"));
				listItem.setStoreID(rs.getInt("Store_ID"));
				listItem.setGrID(rs.getInt("Group_ID"));

				listItems.add(listItem);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return listItems;
	}

	// �berpr�fung ob Artikel mehr als 5x gekauft wurde
	public ArrayList<Integer> autoSetFav(Group g) {

		ArrayList<Integer> setAsFav = new ArrayList<Integer>();
		Connection con = DBConnection.connection();

		try {

			Statement stmt = con.createStatement();

			ResultSet rs = stmt.executeQuery(
					"SELECT ListItem.Item_ID, COUNT(Item_ID)  FROM ListItem JOIN Responsibility ON ListItem.Responsibility_ID = Responsibility.Responsibility_ID \r\n"
							+ "JOIN ShoppingList ON Responsibility.Shoppinglist_ID = ShoppingList.ShoppingList_ID WHERE ShoppingList.Group_ID = "
							+ g.getId() + " AND BoughtOn IS NOT NULL GROUP BY Item_ID HAVING COUNT(Item_ID) > 4");

			while (rs.next()) {

				setAsFav.add(rs.getInt("itemid"));

			}

		} catch (SQLException e) {
			e.printStackTrace();

		}
		return setAsFav;
	}

	// L�schen von Listitems anhand der Person ID
	public void deleteListItemByPersonID(Person p) {
		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();
			stmt.executeUpdate("DELETE ListItem FROM Responsibility INNER JOIN ListItem ON \r\n"
					+ "ListItem.Responsibility_ID = Responsibility.Responsibility_ID \r\n"
					+ "WHERE Responsibility.Person_ID = " + p.getId());
			System.out.println(stmt);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// L�schen eines Listitems anhand der Item ID - Notwendig falls zugeh�riges Item
	// gel�scht wird
	public void deleteListItemByItemID(Item i) {
		// TODO Auto-generated method stub
		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();
			stmt.executeUpdate("DELETE FROM ListItem WHERE Item_ID = " + i.getId());

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
