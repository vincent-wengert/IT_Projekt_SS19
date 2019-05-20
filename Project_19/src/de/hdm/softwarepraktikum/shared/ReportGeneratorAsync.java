package de.hdm.softwarepraktikum.shared;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.AsyncCallback;

import de.hdm.softwarepraktikum.shared.bo.Group;
import de.hdm.softwarepraktikum.shared.bo.Item;
import de.hdm.softwarepraktikum.shared.bo.Person;
import de.hdm.softwarepraktikum.shared.bo.ShoppingList;
import de.hdm.softwarepraktikum.shared.bo.Store;
import de.hdm.softwarepraktikum.shared.report.ItemsByGroupReport;
import de.hdm.softwarepraktikum.shared.report.ItemsByPersonReport;



public interface ReportGeneratorAsync {
	
	public void init(AsyncCallback<Void> callback) throws IllegalArgumentException;
	
	public void getShoppingListAdministration(int id, AsyncCallback<ShoppingListAdministration> callback) throws IllegalArgumentException;
	
	public void createUserStatisticsReport(int id, AsyncCallback<ItemsByPersonReport> callback) throws IllegalArgumentException;
	
	public void createGroupStatisticsReport(int id, AsyncCallback<ItemsByGroupReport> callback) throws IllegalArgumentException;
 	
	public void getAllItems(int id, AsyncCallback<ArrayList<Item>> callback) throws IllegalArgumentException;
	
	public void getAllStores(int id, AsyncCallback<ArrayList<Store>> callback) throws IllegalArgumentException;;
	
	public void getStore(int id, AsyncCallback<Store> callback) throws IllegalArgumentException;
	
	public void AddImprint(AsyncCallback<Void> callback) throws IllegalArgumentException;
	
	public void getAllParticipations(Person p, AsyncCallback<ArrayList<ShoppingList>> callback) throws IllegalArgumentException;
	
	
}
