package de.hdm.softwarepraktikum.client.gui;

import org.apache.commons.io.filefilter.TrueFileFilter;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.datepicker.client.DateBox;
import de.hdm.softwarepraktikum.client.ClientsideSettings;
import de.hdm.softwarepraktikum.shared.ShoppingListAdministrationAsync;
import de.hdm.softwarepraktikum.shared.bo.Item;
import de.hdm.softwarepraktikum.shared.bo.ListItem.Unit;


/**
 * Die Klasse <code>ShowItemForm</code> ist eine Form die verschiedene Methoden und Widgets zur Anzeige
 * eines <code>Item</code> bietet.
 * 
 * @author VincentWengert
 * @version 1.0
 */

public class ItemForm extends VerticalPanel{
	
	private ShoppingListAdministrationAsync shoppinglistAdministration = ClientsideSettings.getShoppinglistAdministration();

	private HorizontalPanel formHeaderPanel = new HorizontalPanel();
	private HorizontalPanel bottomButtonsPanel = new HorizontalPanel();
	private HorizontalPanel topButtonsPanel = new HorizontalPanel();

	private Item itemToDisplayProduct = null;
	private Label infoTitleLabel = new Label("Artikel");
	private Label itemNameLabel = new Label("Name des Artikels");

	private TextBox itemNameBox = new TextBox();

	private Button confirmButton = new Button("\u2714");
	private Button cancelButton = new Button("\u2716");
	private Button favButton = new Button();
	private Button editButton = new Button();
	private Button deleteButton = new Button();
	private Grid itemGrid = new Grid(2,2);

	private Boolean editable;
	private Boolean initial;
	private static ItemForm itemForm = null;
	private AllItemsCellList aicl = null;

	public ItemForm() {
		favButton.addClickHandler(new FavClickHandler());
		topButtonsPanel.add(favButton);
		
		editButton.addClickHandler(new EditClickHandler());
		topButtonsPanel.add(editButton);
		
		deleteButton.addClickHandler(new DeleteClickHandler());
		topButtonsPanel.add(deleteButton);

		confirmButton.addClickHandler(new CreateClickHandler());
		bottomButtonsPanel.add(confirmButton);

		cancelButton.addClickHandler(new CancelClickHandler());
		bottomButtonsPanel.add(cancelButton);
		
		bottomButtonsPanel.setVisible(false);
		itemNameBox.setEnabled(false);
	}
	
	
	/**
	 * In dieser Methode werden die Widgets der Form hinzugefügt.
	 * Außerdem findet hier die Formatierungen der Widgets statt.
	 */
	public void onLoad() {

		this.setWidth("100%");
		favButton.setStylePrimaryName("favButton");
		editButton.setStylePrimaryName("editButton");
		deleteButton.setStylePrimaryName("deleteButton");
		itemNameLabel.setStylePrimaryName("textLabel");
		formHeaderPanel.setStylePrimaryName("formHeaderPanel");
		infoTitleLabel.setStylePrimaryName("infoTitleLabel");
		bottomButtonsPanel.setStylePrimaryName("bottomButtonsPanel");
		cancelButton.setStylePrimaryName("cancelButton");
		confirmButton.setStylePrimaryName("confirmButton");

		
		editButton.setHeight("8vh");
		editButton.setWidth("8vh");
		topButtonsPanel.setCellHorizontalAlignment(editButton, ALIGN_LEFT);
		favButton.setHeight("8vh");
		favButton.setWidth("8vh");
		topButtonsPanel.setCellHorizontalAlignment(favButton, ALIGN_LEFT);
		deleteButton.setHeight("8vh");
		deleteButton.setWidth("8vh");
		topButtonsPanel.setCellHorizontalAlignment(deleteButton, ALIGN_RIGHT);
		
		formHeaderPanel.setHeight("8vh");
		formHeaderPanel.setWidth("100%");
		infoTitleLabel.setWidth("100%");
		
		cancelButton.setPixelSize(130, 40);
		confirmButton.setPixelSize(130, 40);

		formHeaderPanel.add(infoTitleLabel);
		formHeaderPanel.setCellVerticalAlignment(infoTitleLabel, ALIGN_BOTTOM);
		formHeaderPanel.setCellHorizontalAlignment(infoTitleLabel, ALIGN_LEFT);

		bottomButtonsPanel.setSpacing(20);
		formHeaderPanel.add(topButtonsPanel);
		
		this.add(formHeaderPanel);
		this.add(itemGrid);
		this.setCellHorizontalAlignment(itemGrid, ALIGN_CENTER);
		
		formHeaderPanel.setCellVerticalAlignment(topButtonsPanel, ALIGN_BOTTOM);
		formHeaderPanel.setCellHorizontalAlignment(topButtonsPanel, ALIGN_RIGHT);

		itemNameBox.setMaxLength(10);

		itemGrid.setCellSpacing(10);
		itemGrid.setWidget(0, 0, itemNameLabel);
		itemGrid.setWidget(0, 1, itemNameBox);

		this.add(bottomButtonsPanel);
		this.setCellHorizontalAlignment(bottomButtonsPanel, ALIGN_CENTER);

		setTableEditable(editable);

	}
	
	/**
	 * Methode um die aktuelle <code>NewShowItemForm</code> Instanz zu setzen.
	 * Diese Instanz wird für das Aktualisieren nach dem Anlegen eines Items benötigt.
	 * 
	 * @param newShowItemForm das zu setzende <code>newShowItemForm</code> Objekt.
	 */
	public void setItemForm(ItemForm itemForm) {

		this.itemForm = itemForm;
	}
	
	/**
	 * Methode um die aktuelle <code>NewShowItemForm</code> Instanz zu setzen.
	 * Diese Instanz wird für das Aktualisieren nach dem Anlegen eines Items benötigt.
	 * 
	 * @param newShowItemForm das zu setzende <code>newShowItemForm</code> Objekt.
	 */
	public void setAllItemsCelllist(AllItemsCellList allItemsCellist) {

		this.aicl = allItemsCellist;
	}
	
	public void setEditable(Boolean editable) {

		this.editable = editable;
	}
	
	public void setInitial(Boolean initial) {

		this.initial = initial;
	}
	
	public void setSelected(Item i) {
		itemToDisplayProduct =i;
		if(i != null) {
			infoTitleLabel.setText("Ausgewählter Artikel: " + i.getName());
			itemNameBox.setText(i.getName());
			setTableEditable(false);
		}
	}

	public void setTableEditable (boolean editable) {
		if (editable == true) {
			itemNameBox.setEnabled(true);
			itemNameBox.setFocus(true);
			bottomButtonsPanel.setVisible(true);
			topButtonsPanel.setVisible(false);
			}else {
			itemNameBox.setEnabled(false);
			itemNameBox.setFocus(false);
			bottomButtonsPanel.setVisible(false);
			topButtonsPanel.setVisible(true);
			}
	}
	
	/**
	 * ***************************************************************************
	 * ABSCHNITT der ClickHandler
	 * ***************************************************************************
	 */

	/**
	 * EditClickHandler der das Bearbeiten des Items ermöglicht.
	 * Durch diesen werden alle Textboxen aktiviert, sowie ein zusätzliches ButtonPanel angezeigt.
	 */
	
	private class FavClickHandler implements ClickHandler {
		@Override
		public void onClick(ClickEvent event) {
			setTableEditable(false);
			favButton.setStylePrimaryName("favButtonClick");

			Notification.show("Artikel wurde zu den Favoriten hinzugef&uuml;gt");
		}
	}
	
	private class EditClickHandler implements ClickHandler {
		@Override
		public void onClick(ClickEvent event) {
			setTableEditable(true);
		}
	}
	


	/**
	 * Hiermit wird der Änderungsvorgang eines Items abbgebrochen.
	 */
	 class CancelClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			if (initial == true) {
			RootPanel.get("Details").clear();
			}else {
			setTableEditable(false);
			}
		}
	}

	
	/**
	 * Sobald der Änderungsvorgang bestätigt wird durch einen Klick auf den Bestätigungsbutton, 
	 * wird das <code>Item</code> aktualisiert.
	 */
	 class CreateClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
		
			if (initial == true) {
			shoppinglistAdministration.createItem(itemNameBox.getText(), true, new CreateItemCallback());
			} else {
			itemToDisplayProduct.setName(itemNameBox.getText());
			shoppinglistAdministration.updateItem(itemToDisplayProduct, new UpdateItemCallback());	
			}
			setTableEditable(false);
		}
	}
	 
		/**
		 * EditClickHandler der das Loschen des Items ermöglicht.
		 */
		private class DeleteClickHandler implements ClickHandler {
			@Override
			public void onClick(ClickEvent event) {
				shoppinglistAdministration.deleteItem(itemToDisplayProduct, new DeleteItemCallback());
			}
		}
		
		/**
		 * Nachdem ein neues <code>Item</code> Objekt erstellt wurde, wird dieses der Liste der aktuellen
		 *  <code>AllItemsCelllist</code> Instanz hinzugefügt.
		 */
		private class CreateItemCallback implements AsyncCallback<Item> {

			@Override
			public void onFailure(Throwable caught) {
				Notification.show("Der Artikel konnte leider nicht erstellt werden:\n" + caught.toString());
			}

			@Override
			public void onSuccess(Item item) {
				//add item to cellist
				Notification.show("Artikel wurde erstellt");
				aicl.updateCelllist();
				RootPanel.get("Details").clear();

			}
		}
		
		/**
		 * Nachdem ein neues <code>Item</code> Objekt erstellt wurde, wird dieses der Liste der aktuellen
		 *  <code>AllItemsCelllist</code> Instanz hinzugefügt.
		 */
		private class UpdateItemCallback implements AsyncCallback<Void> {

			@Override
			public void onFailure(Throwable caught) {
				Notification.show("Der Artikel konnte leider nicht aktualisiert werden:\n" + caught.toString());
			}

			@Override
			public void onSuccess(Void result) {
				// TODO Auto-generated method stub
				aicl.updateCelllist();
				RootPanel.get("Details").clear();
				Notification.show("Artikel wurde aktualisiert");
			}
		}
		
		/**
		 * Hiermit kann <code>Item</code> Objekt geloscht werden und aus der 
		 *  <code>AllItemsCelllist</code> Instanz entfernt werden.
		 */
		private class DeleteItemCallback implements AsyncCallback<Void> {

			@Override
			public void onFailure(Throwable caught) {
				Notification.show("Der Artikel konnte leider nicht entfernt werden:\n" + caught.toString());
			}

			@Override
			public void onSuccess(Void item) {
				//add item to cellist
				Notification.show("Artikel wurde entfernt");
				aicl.updateCelllist();
				RootPanel.get("Details").clear();
			}
		}
}
