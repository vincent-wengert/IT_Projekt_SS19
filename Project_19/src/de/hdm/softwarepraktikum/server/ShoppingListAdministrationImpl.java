package de.hdm.softwarepraktikum.server;

import de.hdm.softwarepraktikum.server.db.ListItemMapper;
import de.hdm.softwarepraktikum.server.db.PersonMapper;
import de.hdm.softwarepraktikum.server.db.ResponsibilityMapper;
import de.hdm.softwarepraktikum.server.db.ShoppingListMapper;
import de.hdm.softwarepraktikum.server.db.StoreMapper;
import de.hdm.softwarepraktikum.server.db.GroupMapper;
import de.hdm.softwarepraktikum.server.db.ItemMapper;

public class ShoppingListAdministrationImpl {
	
private ListItemMapper listItemMapper = null;
private ItemMapper itemMapper = null;
private PersonMapper personMapper = null;
private ShoppingListMapper shoppingListMapper = null;
private StoreMapper storeMapper = null;
private GroupMapper groupMapper = null;
private ResponsibilityMapper responsibilityMapper = null;


	public ShoppingListAdministrationImpl() {

	}
	
	
	//Initialisierung aller Mapper in der Klasse
	public void InitMapper() throws IllegalArgumentException {
		this.listItemMapper = ListItemMapper.listitemMapper();
		this.itemMapper = ItemMapper.itemMapper();
		this.personMapper = PersonMapper.personMapper();
		this.shoppingListMapper = ShoppingListMapper.shoppinglistMapper();
		this.storeMapper = StoreMapper.storeMapper();
		this.groupMapper = GroupMapper.groupMapper();
		this.responsibilityMapper = ResponsibilityMapper.responsibilityMapper();
		
		
	}

}
