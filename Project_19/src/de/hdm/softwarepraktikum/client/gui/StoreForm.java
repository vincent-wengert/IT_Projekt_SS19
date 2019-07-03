package de.hdm.softwarepraktikum.client.gui;

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

import de.hdm.softwarepraktikum.client.ClientsideSettings;
import de.hdm.softwarepraktikum.shared.ShoppingListAdministrationAsync;
import de.hdm.softwarepraktikum.shared.bo.Item;
import de.hdm.softwarepraktikum.shared.bo.Store;

/**
 * In der Klasse <code>StoreForm</code> wird die Detailansicht der Stores
 * implementiert. Die Klasse wird bei der Erstellung eines neuen Stores und bei
 * der Bearbeitung eines Stores aufgerufen.
 * 
 * @author Jan Duwe
 * @version 1.0
 *
 */

public class StoreForm extends VerticalPanel {

	private ShoppingListAdministrationAsync administration = ClientsideSettings.getShoppinglistAdministration();

	private Store storeToDisplay = null;
	private AllStoresCellList ascl = null;

	private HorizontalPanel formHeaderPanel = new HorizontalPanel();
	private HorizontalPanel bottomButtonsPanel = new HorizontalPanel();
	private HorizontalPanel topButtonsPanel = new HorizontalPanel();
	private HorizontalPanel streetHouseNumberPanel = new HorizontalPanel();

	private Label infoTitleLabel = new Label("Store");
	private Label storeNameLabel = new Label("Name des Stores");
	private Label postCodeLabel = new Label("Postleitzahl");
	private Label cityNameLabel = new Label("Ort");
	private Label streetNameLabel = new Label("Straße und Hausnummer");

	private TextBox storeNameBox = new TextBox();
	private TextBox postCodeBox = new TextBox();
	private TextBox cityNameBox = new TextBox();
	private TextBox streetNameBox = new TextBox();
	private TextBox houseNumberBox = new TextBox();

	private Button editButton = new Button();
	private Button deleteButton = new Button();
	private Button confirmButton = new Button("\u2714");
	private Button cancelButton = new Button("\u2716");
	private Grid storeGrid = new Grid(4, 3);

	private StoreForm sf;
	private Boolean editable;
	private Boolean initial;

	/**
	 * Bei der Instanziierung der <code>StoreForm</code>, werden die ClickHandler
	 * den Buttons und dem Panel hinzugefügt
	 */
	public StoreForm() {

		confirmButton.addClickHandler(new CreateClickHandler());
		bottomButtonsPanel.add(confirmButton);

		cancelButton.addClickHandler(new CancelClickHandler());
		bottomButtonsPanel.add(cancelButton);

		editButton.addClickHandler(new EditClickHandler());
		deleteButton.addClickHandler(new DeleteClickHandler());
	}

	/**
	 * ***************************************************************************
	 * ABSCHNITT der Methoden
	 * ***************************************************************************
	 */

	/**
	 * In dieser Methode werden die Widgets der Form hinzugefügt. Außerdem findet
	 * hier die Formatierungen der Widgets statt.
	 * 
	 */
	public void onLoad() {
		this.setWidth("100%");
		editButton.setStylePrimaryName("editButton");
		deleteButton.setStylePrimaryName("deleteButton");
		formHeaderPanel.setStylePrimaryName("formHeaderPanel");
		infoTitleLabel.setStylePrimaryName("infoTitleLabel");
		bottomButtonsPanel.setStylePrimaryName("bottomButtonsPanel");
		cancelButton.setStylePrimaryName("cancelButton");
		confirmButton.setStylePrimaryName("confirmButton");
		topButtonsPanel.setStylePrimaryName("topButtonsPanel2");

		cancelButton.setPixelSize(130, 40);
		confirmButton.setPixelSize(130, 40);

		houseNumberBox.setWidth("42%");
		streetNameBox.setWidth("95%");

		editButton.setHeight("8vh");
		editButton.setWidth("8vh");
		topButtonsPanel.setCellHorizontalAlignment(editButton, ALIGN_LEFT);
		deleteButton.setHeight("8vh");
		deleteButton.setWidth("8vh");
		topButtonsPanel.setCellHorizontalAlignment(deleteButton, ALIGN_RIGHT);

		formHeaderPanel.add(infoTitleLabel);
		formHeaderPanel.add(topButtonsPanel);

		formHeaderPanel.setCellVerticalAlignment(infoTitleLabel, ALIGN_BOTTOM);
		formHeaderPanel.setCellHorizontalAlignment(infoTitleLabel, ALIGN_LEFT);

		formHeaderPanel.setCellVerticalAlignment(topButtonsPanel, ALIGN_BOTTOM);
		formHeaderPanel.setCellHorizontalAlignment(topButtonsPanel, ALIGN_RIGHT);

		topButtonsPanel.add(editButton);
		topButtonsPanel.add(deleteButton);

		bottomButtonsPanel.setSpacing(20);

		streetHouseNumberPanel.add(streetNameBox);
		streetHouseNumberPanel.add(houseNumberBox);

		this.add(formHeaderPanel);
		this.add(storeGrid);
		this.setCellHorizontalAlignment(storeGrid, ALIGN_CENTER);

		storeNameBox.setMaxLength(15);
		postCodeBox.setMaxLength(5);
		cityNameBox.setMaxLength(15);
		streetNameBox.setMaxLength(15);
		houseNumberBox.setMaxLength(4);

		storeGrid.setCellSpacing(10);
		storeGrid.setWidget(0, 0, storeNameLabel);
		storeGrid.setWidget(1, 0, postCodeLabel);
		storeGrid.setWidget(2, 0, cityNameLabel);
		storeGrid.setWidget(3, 0, streetNameLabel);

		storeGrid.setWidget(0, 1, storeNameBox);
		storeGrid.setWidget(1, 1, postCodeBox);
		storeGrid.setWidget(2, 1, cityNameBox);
		storeGrid.setWidget(3, 1, streetHouseNumberPanel);

		this.add(bottomButtonsPanel);
		this.setCellHorizontalAlignment(bottomButtonsPanel, ALIGN_CENTER);

		setTableEditable(editable);

	}

	/**
	 * Methode um die aktuelle <code>StoreForm</code> Instanz zu setzen.
	 * 
	 * @param storeForm das zu setzende <code>StoreForm</code> Objekt.
	 */
	public void setStoreForm(StoreForm storeForm) {
		this.sf = storeForm;
	}

	/**
	 * Methode um die aktuelle <code>AllStoresCellList</code> Instanz zu setzen.
	 * 
	 * @param ascl das zu setzende <code>AllStoresCellList</code> Objekt.
	 */
	public void setAllStoresCellList(AllStoresCellList ascl) {
		this.ascl = ascl;
	}

	/**
	 * Setzt den aktuell ausgewählten <code>Store</code>
	 * 
	 * @param s Das zu setzende <code>Store</code> Objekt
	 */
	public void setSelected(Store s) {
		if (s != null) {
			storeToDisplay = s;
			storeNameBox.setText(s.getName());
			postCodeBox.setText(Integer.toString(s.getPostcode()));
			cityNameBox.setText(s.getCity());
			streetNameBox.setText(s.getStreet());
			houseNumberBox.setText(Integer.toString(s.getHouseNumber()));
			infoTitleLabel.setText(s.getName());
		}
	}

	/**
	 * Methode um die Form in den Bearbeitungsmodus zu setzten
	 * 
	 * @param editable Der zu setzende Staus
	 */
	public void setTableEditable(boolean editable) {
		if (editable == true) {
			storeNameBox.setEnabled(true);
			postCodeBox.setEnabled(true);
			cityNameBox.setEnabled(true);
			streetNameBox.setEnabled(true);
			houseNumberBox.setEnabled(true);
			topButtonsPanel.setVisible(false);
			bottomButtonsPanel.setVisible(true);
		} else {
			storeNameBox.setEnabled(false);
			postCodeBox.setEnabled(false);
			cityNameBox.setEnabled(false);
			streetNameBox.setEnabled(false);
			houseNumberBox.setEnabled(false);
			topButtonsPanel.setVisible(true);
			bottomButtonsPanel.setVisible(false);
		}
	}

	/**
	 * Setzt die aktuellen Status der Tabelle
	 * 
	 * @param editable Der zu setzende Status
	 */
	public void setEditable(Boolean editable) {
		this.editable = editable;
	}

	/**
	 * Setzt ob der das erste Mal angelegt wird
	 * 
	 * @param intital Der zu setzende Status
	 */
	public void setInitial(Boolean initial) {
		this.initial = initial;
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

			if (initial == true) {
				Notification.show("Erstellen abgebrochen");
				RootPanel.get("Details").clear();
			} else {
				setTableEditable(false); // store wird neu geladen
			}
		}
	}

	/**
	 * Sobald die Textfelder für den Namen und die Auswahl der Stores ausgefüllt
	 * wurden, wird ein neuer <code>Store</code> nach dem Klicken des
	 * Bestätigungsbutton erstellt.
	 */
	private class CreateClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			if (cityNameBox.getText() != "" && postCodeBox.getText() != "" && streetNameBox.getText() != ""
					&& houseNumberBox.getText() != "" && storeNameBox.getText() != "") {
							if (initial == false) {
								storeToDisplay.setCity(cityNameBox.getText());
								storeToDisplay.setPostcode(Integer.parseInt(postCodeBox.getText()));
								storeToDisplay.setStreet(streetNameBox.getText());
								storeToDisplay.setHouseNumber(Integer.parseInt(houseNumberBox.getText()));
								storeToDisplay.setName(storeNameBox.getText());
								administration.updateStore(storeToDisplay, new UpdateStoreCallback());
								setTableEditable(false);
							} else {
								administration.createStore(storeNameBox.getText(), streetNameBox.getText(),
										Integer.parseInt(postCodeBox.getText()), cityNameBox.getText(),
										Integer.parseInt(houseNumberBox.getText()), new CreateStoreCallback());
								setTableEditable(false);
							}
			} else {
				Window.alert("Bitte füllen sie alle Felder des Ladens richtig aus");
			}
		}
	}

	/**
	 * Hiermit wird der Erstellvorgang eines Stores begonnen.
	 */
	private class EditClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			Notification.show("edit");
			setInitial(false);
			setTableEditable(true);
		}
	}

	/**
	 * Clickhandler um den Store zu löschen.
	 */
	private class DeleteClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			if (Window.confirm("Wollen Sie wirklich entfernen?") == true) {
				administration.checkforExisitingStores(storeToDisplay.getId(), new checkForExistingStoresCallback());
			}
		}
	}

	/**
	 * ***************************************************************************
	 * ABSCHNITT der Callbacks
	 * ***************************************************************************
	 */

	/**
	 * Private Klasse des Callback um eine <code>Store</code> Instanzen zu erzeugen.
	 */
	private class CreateStoreCallback implements AsyncCallback<Store> {

		@Override
		public void onFailure(Throwable caught) {
			Notification.show("Der Store konnte leider nicht erstellt werden:\n" + caught.toString());
		}

		@Override
		public void onSuccess(Store store) {
			// add item to cellist
			Notification.show("Store wurde erstellt");
			RootPanel.get("Details").clear();
			ascl.updateCellList(store);
		}
	}

	/**
	 * Private Klasse des Callback um eine <code>Store</code> Instanzen zu
	 * aktualisieren.
	 */
	private class UpdateStoreCallback implements AsyncCallback<Void> {

		@Override
		public void onFailure(Throwable caught) {
			Notification.show("Der Store konnte leider nicht aktualisiert werden:\n" + caught.toString());
		}

		@Override
		public void onSuccess(Void result) {
			// TODO Auto-generated method stub
			Notification.show("Store wurde aktualisiert");
			RootPanel.get("Details").clear();
			ascl.updateCellList(storeToDisplay);
			ascl.setSelectedStore(storeToDisplay);
		}

	}

	/**
	 * Private Klasse des Callback um eine <code>Store</code> Instanzen zu löschen.
	 */
	private class DeleteStoreCallback implements AsyncCallback<Void> {

		@Override
		public void onFailure(Throwable caught) {
			Notification.show("Der Store konnte leider nicht entfernt werden:\n" + caught.toString());
		}

		@Override
		public void onSuccess(Void result) {
			// TODO Auto-generated method stub
			Notification.show("Store wurde entfernt");
			RootPanel.get("Details").clear();
			ascl.updateCellList(null);
		}

	}

	/**
	 * Private Klasse des Callback um abzufragen ob zu einer <code>Store</code>
	 * Instanzen <code>ListItem</code> Objekte vorhanden sind.
	 */
	private class checkForExistingStoresCallback implements AsyncCallback<Boolean> {

		@Override
		public void onFailure(Throwable caught) {
		}

		@Override
		public void onSuccess(Boolean result) {
			// add item to cellist
			if (result == true) {
				Window.alert("Der Laden kann nicht gelöscht, da dieser noch in einer Einkaufliste vorhanden ist."
						+ " Wenn dieser dennoch gelöscht werden möchte dann kontaktieren sie den Administrator");
			} else {
				administration.deleteStore(storeToDisplay, new DeleteStoreCallback());
				ascl.updateCellList(null);
			}
		}
	}
}
