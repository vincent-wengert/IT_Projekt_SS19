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
 * Die Klasse <code>NewGroupForm</code> ist eine Form die verschiedene Methoden und Widgets zur Erstellung
 * einer neuen <code>Gruppe</code> bietet.
 * 
 * @author VincentWengert
 * @version 1.0
 */

public class NewShoppingListForm extends VerticalPanel {

	private HorizontalPanel formHeaderPanel = new HorizontalPanel();
	private HorizontalPanel bottomButtonsPanel = new HorizontalPanel();

	private Label infoTitleLabel = new Label("Neue Einkaufsliste erstellen");
	private Label shoppinglistNameLabel = new Label("Name der Einkaufsliste");

	private TextBox shoppinglistNameBox = new TextBox();

	private Button confirmButton = new Button("\u2714");
	private Button cancelButton = new Button("\u2716");
	private Grid shoppinglistGrid = new Grid(2, 2);
	
	private NewShoppingListForm newShoppingListForm;

	public NewShoppingListForm() {

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
		shoppinglistNameLabel.setStylePrimaryName("textLabel");
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
		this.add(shoppinglistGrid);
		this.setCellHorizontalAlignment(shoppinglistGrid, ALIGN_CENTER);

		shoppinglistNameBox.setMaxLength(10);

		shoppinglistGrid.setCellSpacing(10);
		shoppinglistGrid.setWidget(0, 0, shoppinglistNameLabel);
		shoppinglistGrid.setWidget(0, 1, shoppinglistNameBox);


		this.add(bottomButtonsPanel);
		this.setCellHorizontalAlignment(bottomButtonsPanel, ALIGN_CENTER);

	}
	
	/**
	 * Methode um die aktuelle <code>NewShoppingListForm</code> Instanz zu setzen.
	 * Diese Instanz wird für das Aktualisieren nach dem Anlegen einer Einkaufliste benötigt.
	 * 
	 * @param newShoppingListForm das zu setzende <code>NewShoppingListForm</code> Objekt.
	 */
	public void setNewShoppingListForm(NewShoppingListForm newShoppingListForm) {

		this.newShoppingListForm = newShoppingListForm;
	}

	
	/**
	 * ***************************************************************************
	 * ABSCHNITT der ClickHandler
	 * ***************************************************************************
	 */
	
	/**
	 * Hiermit wird der Erstellvorgang eines neuer Einkaufsliste abbgebrochen.
	 */
	private class CancelClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			RootPanel.get("Details").clear();
		}
	}

	
	/**
	 * Sobald die Textfelder für den Namen und die Auswahl der Artikel ausgefüllt wurden, wird
	 * ein neuer <code>ShoppingList</code> nach dem Klicken des Bestätigungsbutton erstellt.
	 */
	private class CreateClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {

		}
	}
}
