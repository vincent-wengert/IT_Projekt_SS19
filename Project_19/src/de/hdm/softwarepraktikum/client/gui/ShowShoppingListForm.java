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

import de.hdm.softwarepraktikum.client.gui.AllShoppingListsCellList.DemoShoppingList;
import de.hdm.softwarepraktikum.client.gui.AllShoppingListsCellList.ItemDemo;

public class ShowShoppingListForm extends VerticalPanel{
	private HorizontalPanel formHeaderPanel = new HorizontalPanel();
	private HorizontalPanel shoppingListPanel = new HorizontalPanel();
	
	private Label infoTitleLabel = new Label("Einkaufsliste");
	
	private CellTable<ItemDemo> cellTable = null;
	private ProductKeyProvider keyProvider = null;
	private ListDataProvider<ItemDemo> dataProvider = new ListDataProvider<ItemDemo>();
	private MultiSelectionModel<ItemDemo> multiselectionModel = null;
	private ArrayList<ItemDemo> productsToDisplay = null;
	
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
		multiselectionModel = new MultiSelectionModel<ItemDemo>(keyProvider);
		cellTable = new CellTable<ItemDemo>();
		dataProvider.addDataDisplay(cellTable);
		
		cellTable.setSelectionModel(multiselectionModel,
			        DefaultSelectionEventManager.<ItemDemo> createCheckboxManager());
		 
		ListHandler<ItemDemo> sortHandler = new ListHandler<ItemDemo>(null);
		
		TextColumn<ItemDemo> nameColumn = new TextColumn<ItemDemo>() {
		      @Override
		      public String getValue(ItemDemo p) {
		        return p.getName();
		      }
		    };
		
	    TextColumn<ItemDemo> amountColumn = new TextColumn<ItemDemo>() {
		      @Override
		      public String getValue(ItemDemo p) {
		        return Integer.toString(p.getAmount());
		      }
		    };    
		   
	    Column<ItemDemo, Boolean> checkColumn = new Column<ItemDemo, Boolean>(new CheckboxCell(true, false)) {
		      @Override
		      public Boolean getValue(ItemDemo object) {
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
	
	private class ProductKeyProvider implements ProvidesKey<ItemDemo>{
 		
		@Override
		public Object getKey(ItemDemo item) {
			  return (item == null) ? null : item.getId();
      }	
	}

	public void setSelected(DemoShoppingList sl) {
		if(sl != null) {
			//shoppingListToDisplay = sl;
			productsToDisplay = sl.getProductsByList();
			List<ItemDemo> list = dataProvider.getList();
		    for (ItemDemo p : productsToDisplay) {
		    	list.add(p);
		    }
			dataProvider.flush();
			}
		}
}

