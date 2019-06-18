package de.hdm.softwarepraktikum.shared;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.AsyncCallback;

import de.hdm.softwarepraktikum.shared.bo.Group;
import de.hdm.softwarepraktikum.shared.bo.Item;
import de.hdm.softwarepraktikum.shared.bo.ListItem;
import de.hdm.softwarepraktikum.shared.bo.Person;
import de.hdm.softwarepraktikum.shared.bo.ShoppingList;
import de.hdm.softwarepraktikum.shared.bo.Store;
import de.hdm.softwarepraktikum.shared.report.ItemsByGroupReport;
import de.hdm.softwarepraktikum.shared.report.ItemsByPersonReport;



public interface ReportGeneratorAsync {
	
	public void init(AsyncCallback<Void> callback) throws IllegalArgumentException;
	
	void createUserStatisticsReport(Person p, AsyncCallback<ItemsByPersonReport> callback);
	
	void createGroupStatisticsReport(Group g, AsyncCallback<ItemsByGroupReport> callback);
 	
	public void getAllItems(int id, AsyncCallback<ArrayList<Item>> callback) throws IllegalArgumentException;
	
	public void getAllStores(int id, AsyncCallback<ArrayList<Store>> callback) throws IllegalArgumentException;;
	
	public void getStore(int id, AsyncCallback<Store> callback) throws IllegalArgumentException;
	
	public void AddImprint(AsyncCallback<Void> callback) throws IllegalArgumentException;
	
	public void getAllParticipations(Person p, AsyncCallback<ArrayList<ShoppingList>> callback) throws IllegalArgumentException;
	
	
}
