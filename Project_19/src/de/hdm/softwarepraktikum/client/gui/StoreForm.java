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

import de.hdm.softwarepraktikum.client.gui.AllStoresCellList.DemoStore;
/**
 * In der Klasse <code>StoreForm</code> wird die Detailansicht der Stores implementiert.
 * Die Klasse wird bei der Erstellung eines neuen Stores und bei der Bearbeitung eines Stores aufgerufen.
 * 
 * @author Jan Duwe
 * @version 1.0
 *
 */

public class StoreForm extends VerticalPanel{

	private DemoStore storeToDisplay = null;

	private HorizontalPanel formHeaderPanel = new HorizontalPanel();
	private HorizontalPanel bottomButtonsPanel = new HorizontalPanel();
	private HorizontalPanel topButtonsPanel = new HorizontalPanel();

	private Label infoTitleLabel = new Label("Store");
	private Label storeNameLabel = new Label("Name des Stores");
	private Label postCodeLabel = new Label("Postleitzahl");
	private Label cityNameLabel = new Label("Ort");
	private Label streetNameLabel = new Label("Straße und Hausnummer");

	private TextBox storeNameBox = new TextBox();
	private TextBox postCodeBox = new TextBox();
	private TextBox cityNameBox = new TextBox();
	private TextBox streetNameBox = new TextBox();

	private Button editButton = new Button();
	private Button confirmButton = new Button("\u2714");
	private Button cancelButton = new Button("\u2716");
	private Grid storeGrid = new Grid(4, 2);
	
//	private FieldVerifier verifier = new FieldVerifier();

	private StoreForm StoreForm;
	private Boolean editable;
	private Boolean initial;
	
	public StoreForm() {

		confirmButton.addClickHandler(new CreateClickHandler());
		bottomButtonsPanel.add(confirmButton);

		cancelButton.addClickHandler(new CancelClickHandler());
		bottomButtonsPanel.add(cancelButton);
		
		editButton.addClickHandler(new EditClickHandler());
	}
	
	public void setSelected(DemoStore s) {
		if(s != null) {
			storeNameBox.setText(s.getName());
			postCodeBox.setText(Integer.toString(s.getPostleitZahl()));
			cityNameBox.setText(s.getOrt());
			streetNameBox.setText(s.getStrasse() + " " + s.getHausNummer());
			infoTitleLabel.setText(s.getName());
		}
	}
	/**
	 * In dieser Methode werden die Widgets der Form hinzugefügt.
	 * Außerdem findet hier die Formatierungen der Widgets statt.
	 * 
	 */
	public void onLoad() {

		this.setWidth("100%");
//		postCodeLabel.setStylePrimaryName("textLabel");
//		storeNameLabel.setStylePrimaryName("textLabel");
		editButton.setStylePrimaryName("editButton");
		formHeaderPanel.setStylePrimaryName("formHeaderPanel");
		infoTitleLabel.setStylePrimaryName("infoTitleLabel");
		bottomButtonsPanel.setStylePrimaryName("bottomButtonsPanel");
		cancelButton.setStylePrimaryName("cancelButton");
		confirmButton.setStylePrimaryName("confirmButton");

		formHeaderPanel.setHeight("8vh");
		formHeaderPanel.setWidth("100%");
		cancelButton.setPixelSize(130, 40);
		confirmButton.setPixelSize(130, 40);
		
		editButton.setHeight("8vh");
		editButton.setWidth("8vh");
		topButtonsPanel.setCellHorizontalAlignment(editButton, ALIGN_CENTER);

		formHeaderPanel.add(infoTitleLabel);
		formHeaderPanel.add(topButtonsPanel);
		formHeaderPanel.setCellVerticalAlignment(infoTitleLabel, ALIGN_BOTTOM);
		formHeaderPanel.setCellHorizontalAlignment(infoTitleLabel, ALIGN_LEFT);
		
		formHeaderPanel.setCellVerticalAlignment(topButtonsPanel, ALIGN_BOTTOM);
		formHeaderPanel.setCellHorizontalAlignment(topButtonsPanel, ALIGN_RIGHT);
		
		topButtonsPanel.add(editButton);

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
		
		storeGrid.setWidget(0, 1, storeNameBox);
		storeGrid.setWidget(1, 1, postCodeBox);
		storeGrid.setWidget(2, 1, cityNameBox);
		storeGrid.setWidget(3, 1, streetNameBox);

		this.add(bottomButtonsPanel);
		this.setCellHorizontalAlignment(bottomButtonsPanel, ALIGN_CENTER);
		
		setTableEditable(editable);

	}
	
	public void setStoreForm(StoreForm StoreForm) {

		this.StoreForm = StoreForm;
	}
	
	public void setTableEditable(boolean editable) {
		if(editable == true) {
			storeNameBox.setEnabled(true);
			postCodeBox.setEnabled(true);
			cityNameBox.setEnabled(true);
			streetNameBox.setEnabled(true);
			topButtonsPanel.setVisible(false);
			bottomButtonsPanel.setVisible(true);
		}else {
			storeNameBox.setEnabled(false);
			postCodeBox.setEnabled(false);
			cityNameBox.setEnabled(false);
			streetNameBox.setEnabled(false);
			topButtonsPanel.setVisible(true);
			bottomButtonsPanel.setVisible(false);
		}
	}
	
	public void setEditable(Boolean editable) {
		this.editable = editable;
	}
	
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
			
			if(initial==true) {
				Notification.show("Cancel");
				RootPanel.get("Details").clear();
			}else {
				setTableEditable(false); 		//store wird neu geladen
			}
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
			setTableEditable(false);
		}
	}
	
	private class EditClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			Notification.show("edit");
			setTableEditable(true);
		}
	}
}
