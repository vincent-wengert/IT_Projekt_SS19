package de.hdm.softwarepraktikum.client.gui;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.cell.client.CheckboxCell;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.cellview.client.ColumnSortEvent.ListHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.DefaultSelectionEventManager;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.MultiSelectionModel;
import com.google.gwt.view.client.ProvidesKey;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;

import de.hdm.softwarepraktikum.shared.bo.ListItem;
import de.hdm.softwarepraktikum.shared.bo.ListItem.Unit;
import de.hdm.softwarepraktikum.shared.bo.ShoppingList;

public class ShowShoppingListForm extends VerticalPanel{
	private HorizontalPanel formHeaderPanel = new HorizontalPanel();
	private HorizontalPanel shoppingListPanel = new HorizontalPanel();
	private HorizontalPanel topButtonsPanel = new HorizontalPanel();
	
	private Button addListItemButton = new Button();
	
	private Label infoTitleLabel = new Label();
	
	private CellTable<ListItem> cellTable = null;
	private ProductKeyProvider keyProvider = null;
	private ListDataProvider<ListItem> dataProvider = new ListDataProvider<ListItem>();
	private SingleSelectionModel<ListItem> singleSelectionModel = null;
	private ArrayList<ListItem> productsToDisplay = null;

	
	public ShowShoppingListForm () {
		addListItemButton.addClickHandler(new AddListItemClickHandler());
		topButtonsPanel.add(addListItemButton);
	}
	
	
	public void onLoad() {
		this.setWidth("100%");
		
		addListItemButton.setStylePrimaryName("addListItemButton");
		formHeaderPanel.setStylePrimaryName("formHeaderPanel");
		infoTitleLabel.setStylePrimaryName("infoTitleLabel");
	
		addListItemButton.setHeight("8vh");
		addListItemButton.setWidth("8vh");
		
		formHeaderPanel.setHeight("8vh");
		formHeaderPanel.setWidth("100%");
		
		formHeaderPanel.add(infoTitleLabel);
		formHeaderPanel.add(topButtonsPanel);
		
		
		formHeaderPanel.setCellVerticalAlignment(infoTitleLabel, ALIGN_BOTTOM);
		formHeaderPanel.setCellVerticalAlignment(topButtonsPanel, ALIGN_BOTTOM);
		formHeaderPanel.setCellHorizontalAlignment(topButtonsPanel, ALIGN_RIGHT);
		
		topButtonsPanel.setCellHorizontalAlignment(addListItemButton, ALIGN_CENTER);
		topButtonsPanel.setHorizontalAlignment(ALIGN_RIGHT);
		

		this.add(formHeaderPanel);
		
		keyProvider = new ProductKeyProvider();
		singleSelectionModel = new SingleSelectionModel<ListItem>(keyProvider);
		cellTable = new CellTable<ListItem>();
		dataProvider.addDataDisplay(cellTable);
		
		cellTable.setSelectionModel(singleSelectionModel,
			        DefaultSelectionEventManager.<ListItem> createCheckboxManager());
		
	
		singleSelectionModel.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
			
			@Override
			public void onSelectionChange(SelectionChangeEvent event) {
				// TODO Auto-generated method stub
			      ListItem selected = singleSelectionModel.getSelectedObject();
			        if (selected != null) {
			          Window.alert("You selected: " + selected.getName());
			        }
			}
		});
		 
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
		    
	    TextColumn<ListItem> unitColumn = new TextColumn<ListItem>() {
		      @Override
		      public String getValue(ListItem i) {
		        return i.getUnit().toString();
		      }
		    };
		    
	    TextColumn<ListItem> storeColumn = new TextColumn<ListItem>() {
		      @Override
		      public String getValue(ListItem i) {
		        return i.getUnit().toString();
		      }
		    };
			    
	    TextColumn<ListItem> personColumn = new TextColumn<ListItem>() {
		      @Override
		      public String getValue(ListItem i) {
		        return i.getUnit().toString();
		      }
		    };


			   
		   
	    Column<ListItem, Boolean> checkColumn = new Column<ListItem, Boolean>(new CheckboxCell(true, false)) {
		      @Override
		      public Boolean getValue(ListItem object) {
		        // Get the value from the selection model.
		        return singleSelectionModel.isSelected(object);
		      }
		    };
		    
		cellTable.addColumn(checkColumn, "Eingekauft");
		//cellTable.setColumnWidth(checkColumn, 40,);
		cellTable.setColumnWidth(checkColumn, "10");
		    
	    cellTable.addColumnSortHandler(sortHandler);
	    cellTable.addColumn(nameColumn, "Name");
	    cellTable.addColumn(amountColumn, "Menge");
	    cellTable.addColumn(unitColumn, "Einheit");
	    cellTable.addColumn(storeColumn, "Laden");
	    cellTable.addColumn(personColumn, "Verantwortlicher");
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
	

	public void AddListItem (ListItem li) {
		dataProvider.getList().add(li);
		dataProvider.refresh();
	}
	
	/**
	 * Sobald der Button ausgewahlt wird werden neue <code>ListItem</code> Objekte erzeugt und 
	 * der Shoppinglist hinzugefugt.
	 */
	 class AddListItemClickHandler implements ClickHandler {


		@Override
		public void onClick(ClickEvent event) {
			ListItemDialog li = new ListItemDialog();
			li.setShowShoppingListForm(ShowShoppingListForm.this);
			li.show();
		}
	}
	 
}

