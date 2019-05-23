package de.hdm.softwarepraktikum.client.gui;

import org.apache.commons.io.filefilter.TrueFileFilter;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.datepicker.client.DateBox;
import de.hdm.softwarepraktikum.client.gui.AllItemssCellList.ItemDemo;


/**
 * Die Klasse <code>ShowItemForm</code> ist eine Form die verschiedene Methoden und Widgets zur Anzeige
 * eines <code>Item</code> bietet.
 * 
 * @author VincentWengert
 * @version 1.0
 */

public class ItemForm extends VerticalPanel{

	private HorizontalPanel formHeaderPanel = new HorizontalPanel();
	private HorizontalPanel bottomButtonsPanel = new HorizontalPanel();
	private HorizontalPanel topButtonsPanel = new HorizontalPanel();

	private ItemDemo itemToDisplayProduct = null;
	private Label infoTitleLabel = new Label("Artikel");
	private Label itemNameLabel = new Label("Name des Artikels");

	private TextBox itemNameBox = new TextBox();

	private Button confirmButton = new Button("\u2714");
	private Button cancelButton = new Button("\u2716");
	private Button editButton = new Button();
	private Grid itemGrid = new Grid(2,2);

	private Boolean init = new Boolean(true);
	private static ItemForm itemForm;

	public ItemForm() {
		editButton.addClickHandler(new EditClickHandler());
		topButtonsPanel.add(editButton);

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
		editButton.setStylePrimaryName("editButton");
		itemNameLabel.setStylePrimaryName("textLabel");
		formHeaderPanel.setStylePrimaryName("formHeaderPanel");
		infoTitleLabel.setStylePrimaryName("infoTitleLabel");
		bottomButtonsPanel.setStylePrimaryName("bottomButtonsPanel");
		cancelButton.setStylePrimaryName("cancelButton");
		confirmButton.setStylePrimaryName("confirmButton");

		
		editButton.setWidth("8vh");
		editButton.setHeight("8vh");
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
		
		topButtonsPanel.setCellHorizontalAlignment(editButton, ALIGN_CENTER);
		formHeaderPanel.setCellVerticalAlignment(topButtonsPanel, ALIGN_BOTTOM);
		formHeaderPanel.setCellHorizontalAlignment(topButtonsPanel, ALIGN_RIGHT);

		itemNameBox.setMaxLength(10);

		itemGrid.setCellSpacing(10);
		itemGrid.setWidget(0, 0, itemNameLabel);
		itemGrid.setWidget(0, 1, itemNameBox);

		this.add(bottomButtonsPanel);
		
		
		this.setCellHorizontalAlignment(bottomButtonsPanel, ALIGN_CENTER);
		

		setTableEditable(init);

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
	
	public void setInit(Boolean init) {

		this.init = init;
	}
	
	public void setSelected(AllItemssCellList.ItemDemo i) {
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
			//RootPanel.get("Details").clear();
			setTableEditable(false);
		}
	}

	
	/**
	 * Sobald der Änderungsvorgang bestätigt wird durch einen Klick auf den Bestätigungsbutton, 
	 * wird das <code>Item</code> aktualisiert.
	 */
	 class CreateClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
//				shoppingListAdministration.createItem(nameBox.getText(), new CreateItemCallback());
			//RootPanel.get("Details").clear();
			setTableEditable(false);
		}
	}
}
