package de.hdm.softwarepraktikum.client.gui;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * Die Klasse <code>NewItemForm</code> ist eine Form die verschiedene Methoden und Widgets zur Erstellung
 * eines neuen <code>Item</code> bietet.
 * 
 * @author VincentWengert
 * @version 1.0
 */

public class NewItemForm extends VerticalPanel{
	
	private HorizontalPanel formHeaderPanel = new HorizontalPanel();
	private HorizontalPanel bottomButtonsPanel = new HorizontalPanel();

	private Label infoTitleLabel = new Label("Neuen Artikel erstellen");
	private Label itemNameLabel = new Label("Name des Artikels");

	private TextBox itemNameBox = new TextBox();

	private Button confirmButton = new Button("\u2714");
	private Button cancelButton = new Button("\u2716");
	private Grid itemGrid = new Grid(2,2);

	private NewItemForm newItemForm;

	public NewItemForm() {

		confirmButton.addClickHandler(new CreateClickHandler());
		bottomButtonsPanel.add(confirmButton);

		cancelButton.addClickHandler(new CancelClickHandler());
		bottomButtonsPanel.add(cancelButton);
	}
	
	
	/**
	 * In dieser Methode werden die Widgets der Form hinzugefügt.
	 * Außerdem findet hier die Formatierungen der Widgets statt.
	 */
	public void onLoad() {

		this.setWidth("100%");
		itemNameLabel.setStylePrimaryName("textLabel");
		formHeaderPanel.setStylePrimaryName("formHeaderPanel");
		infoTitleLabel.setStylePrimaryName("infoTitleLabel");
		bottomButtonsPanel.setStylePrimaryName("bottomButtonsPanel");
		cancelButton.setStylePrimaryName("cancelButton");
		confirmButton.setStylePrimaryName("confirmButton");

		formHeaderPanel.setHeight("8vh");
		formHeaderPanel.setWidth("100%");
		cancelButton.setPixelSize(130, 40);
		confirmButton.setPixelSize(130, 40);

		formHeaderPanel.add(infoTitleLabel);
		formHeaderPanel.setCellVerticalAlignment(infoTitleLabel, ALIGN_BOTTOM);

		bottomButtonsPanel.setSpacing(20);

		this.add(formHeaderPanel);
		this.add(itemGrid);
		this.setCellHorizontalAlignment(itemGrid, ALIGN_CENTER);

		itemNameBox.setMaxLength(10);

		itemGrid.setCellSpacing(10);
		itemGrid.setWidget(0, 0, itemNameLabel);
		itemGrid.setWidget(0, 1, itemNameBox);

		this.add(bottomButtonsPanel);
		this.setCellHorizontalAlignment(bottomButtonsPanel, ALIGN_CENTER);
	}
	
	/**
	 * Methode um die aktuelle <code>NewItemForm</code> Instanz zu setzen.
	 * Diese Instanz wird für das Aktualisieren nach dem Anlegen eines Items benötigt.
	 * 
	 * @param newItemForm das zu setzende <code>NewItemForm</code> Objekt.
	 */
	public void setNewItemForm(NewItemForm newItemForm) {

		this.newItemForm = newItemForm;
	}

	
	/**
	 * ***************************************************************************
	 * ABSCHNITT der ClickHandler
	 * ***************************************************************************
	 */

	
	/**
	 * Hiermit wird der Erstellvorgang eines neuen <code>Item</code> abbgebrochen.
	 */
	private class CancelClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			RootPanel.get("Details").clear();
		}
	}

	
	/**
	 * Sobald das Textfeld für den Artikelnamen ausgefüllt wurden, wird
	 * ein neuer <code>Item</code> nach dem Klicken des Bestätigungsbutton erstellt.
	 */
	private class CreateClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {

		}
	}
}
