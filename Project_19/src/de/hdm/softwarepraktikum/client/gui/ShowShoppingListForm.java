package de.hdm.softwarepraktikum.client.gui;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.cell.client.CheckboxCell;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.cellview.client.ColumnSortEvent.ListHandler;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.DefaultSelectionEventManager;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.MultiSelectionModel;
import com.google.gwt.view.client.ProvidesKey;

import de.hdm.softwarepraktikum.shared.bo.Item;
import de.hdm.softwarepraktikum.shared.bo.ListItem;
import de.hdm.softwarepraktikum.shared.bo.ShoppingList;

public class ShowShoppingListForm extends VerticalPanel{
	private HorizontalPanel formHeaderPanel = new HorizontalPanel();
	private HorizontalPanel shoppingListPanel = new HorizontalPanel();
	
	private Label infoTitleLabel = new Label();
	
	private CellTable<ListItem> cellTable = null;
	private ProductKeyProvider keyProvider = null;
	private ListDataProvider<ListItem> dataProvider = new ListDataProvider<ListItem>();
	private MultiSelectionModel<ListItem> multiselectionModel = null;
	private ArrayList<ListItem> productsToDisplay = null;
	
	public void onLoad() {
		this.setWidth("100%");
		
		formHeaderPanel.setStylePrimaryName("formHeaderPanel");
		infoTitleLabel.setStylePrimaryName("infoTitleLabel");
	
		
		formHeaderPanel.setHeight("8vh");
		formHeaderPanel.setWidth("100%");
		
		formHeaderPanel.add(infoTitleLabel);
		formHeaderPanel.setCellVerticalAlignment(infoTitleLabel, ALIGN_BOTTOM);

		this.add(formHeaderPanel);
		
		keyProvider = new ProductKeyProvider();
		multiselectionModel = new MultiSelectionModel<ListItem>(keyProvider);
		cellTable = new CellTable<ListItem>();
		dataProvider.addDataDisplay(cellTable);
		
		cellTable.setSelectionModel(multiselectionModel,
			        DefaultSelectionEventManager.<ListItem> createCheckboxManager());
		 
		ListHandler<ListItem> sortHandler = new ListHandler<ListItem>(null);
		
		TextColumn<ListItem> nameColumn = new TextColumn<ListItem>() {
		      @Override
		      public String getValue(ListItem i) {
		        return i.getName();
		      }
		    };
		
	    TextColumn<ListItem> amountColumn = new TextColumn<ListItem>() {
		      @Override
		      public String getValue(ListItem i) {
		        return Double.toString(i.getAmount());
		      }
		    };    
		   
	    Column<ListItem, Boolean> checkColumn = new Column<ListItem, Boolean>(new CheckboxCell(true, false)) {
		      @Override
		      public Boolean getValue(ListItem object) {
		        // Get the value from the selection model.
		        return multiselectionModel.isSelected(object);
		      }
		    };
		    
		cellTable.addColumn(checkColumn, "Eingekauft");
		cellTable.setColumnWidth(checkColumn, 40, Unit.PX);
		    
	    cellTable.addColumnSortHandler(sortHandler);
	    cellTable.addColumn(nameColumn, "Name");
	    cellTable.addColumn(amountColumn, "Amount");
	    nameColumn.setSortable(true);
	    
	    shoppingListPanel.add(cellTable);
	    this.add(shoppingListPanel);
	}
	
	private class ProductKeyProvider implements ProvidesKey<ListItem>{
 		
		@Override
		public Object getKey(ListItem item) {
			  return (item == null) ? null : item.getId();
      }	
	}

	public void setSelected(ShoppingList sl) {
		if(sl != null) {
			//shoppingListToDisplay = sl;
			productsToDisplay = sl.getShoppinglist();
			List<ListItem> list = dataProvider.getList();
			infoTitleLabel.setText(sl.getTitle());
		    for (ListItem p : productsToDisplay) {
		    	list.add(p);
		    }
		}
	}
}

