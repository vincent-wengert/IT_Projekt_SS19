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
 * Die Klasse <code>NewStoreForm</code> ist eine Form die verschiedene Methoden und Widgets zur Erstellung
 * einer neuen <code>Store</code> bietet.
 * 
 * @author VincentWengert
 * @version 1.0
 */

public class NewStoreForm extends VerticalPanel{
	
	private HorizontalPanel formHeaderPanel = new HorizontalPanel();
	private HorizontalPanel bottomButtonsPanel = new HorizontalPanel();

	private Label infoTitleLabel = new Label("Neuen Store erstellen");
	private Label storeNameLabel = new Label("Name des Stores");
	private Label postCodeLabel = new Label("Postleitzahl");
	private Label cityNameLabel = new Label("Ort");
	private Label streetNameLabel = new Label("Straße und Hausnummer");

	private TextBox storeNameBox = new TextBox();
	private TextBox postCodeBox = new TextBox();
	private TextBox cityNameBox = new TextBox();
	private TextBox streetNameBox = new TextBox();


	private Button confirmButton = new Button("\u2714");
	private Button cancelButton = new Button("\u2716");
	private Grid storeGrid = new Grid(4, 4);

	private NewStoreForm newStoreForm;

	public NewStoreForm() {

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
//		postCodeLabel.setStylePrimaryName("textLabel");
//		storeNameLabel.setStylePrimaryName("textLabel");
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
		this.add(storeGrid);
		this.setCellHorizontalAlignment(storeGrid, ALIGN_CENTER);

		storeNameBox.setMaxLength(10);
		postCodeBox.setMaxLength(15);

		storeGrid.setCellSpacing(10);
		storeGrid.setWidget(0, 0, storeNameLabel);
		storeGrid.setWidget(1, 0, postCodeLabel);
		storeGrid.setWidget(2, 0, cityNameLabel);
		storeGrid.setWidget(3, 0, streetNameLabel);
		
		storeGrid.setWidget(0, 1, postCodeBox);
		storeGrid.setWidget(1, 1, storeNameBox);
		storeGrid.setWidget(2, 1, cityNameBox);
		storeGrid.setWidget(3, 1, streetNameBox);

		this.add(bottomButtonsPanel);
		this.setCellHorizontalAlignment(bottomButtonsPanel, ALIGN_CENTER);

	}
	
	/**
	 * Methode um die aktuelle <code>NewStoreForm</code> Instanz zu setzen.
	 * Diese Instanz wird für das Aktualisieren nach dem Anlegen eines <code>Store</code> benötigt.
	 * 
	 * @param newStoreForm das zu setzende <code>newStoreForm</code> Objekt.
	 */
	public void setNewStoreForm(NewStoreForm newStoreForm) {

		this.newStoreForm = newStoreForm;
	}

	
	/**
	 * ***************************************************************************
	 * ABSCHNITT der ClickHandler
	 * ***************************************************************************
	 */

	
	/**
	 * Hiermit wird der Erstellvorgang eines neuen Store abbgebrochen.
	 */
	private class CancelClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			RootPanel.get("Details").clear();

		}
	}

	
	/**
	 * Sobald die Textfelder für den Namen und die Auswahl der Stores ausgefüllt wurden, wird
	 * ein neuer <code>Store</code> nach dem Klicken des Bestätigungsbutton erstellt.
	 */
	private class CreateClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
//
//				shoppingListAdministration.createStore(all textboxes,new CreateArticleCallback());
//
				RootPanel.get("Details").clear();
		}
	}
}
