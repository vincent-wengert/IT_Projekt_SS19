package de.hdm.softwarepraktikum.client.gui;

import java.util.ArrayList;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.softwarepraktikum.client.ClientsideSettings;
import de.hdm.softwarepraktikum.shared.ShoppingListAdministrationAsync;

public class ListItemDialog extends PopupPanel{

	private ShoppingListAdministrationAsync administration = ClientsideSettings.getShoppinglistAdministration();

	private Button confirmButton = new Button("\u2714");
	private Button cancelButton = new Button ("\u2716");
	
	private RadioButton existingButton = new RadioButton("Bestehend");
	private RadioButton newButton = new RadioButton("Neu");

	private Label existingLabel = new Label("Bestehenden Artikel");
	private Label newLabel = new Label("Neuen Anlegen");
	
	private VerticalPanel verticalPanel = new VerticalPanel();
	private HorizontalPanel radioButtonPanel = new HorizontalPanel();
	private HorizontalPanel bottomButtonsPanel = new HorizontalPanel();

	private Label itemLabel = new Label("Artikel hinzufügen");
	private Label personLabel = new Label("Person auswählen");
	private Label storeLabel = new Label("Laden auswählen");
	
	private	ListBox itemListBox = new ListBox();
	private	ListBox personListBox = new ListBox();
	private	ListBox storeListBox = new ListBox();


	/**
	 * Bei der Instanziierung der Dialogbox werden alle <code>Item</code>, <code>Store</code>,<code>Person</code> geladen und mitels
	 * einer SuggestBox angezeigt, um so ein <code>Listitem</code> zu erstellen.
	 */
	public ListItemDialog() {

		this.load();
		
		this.setTitle("Artikel hinzufugen");
		this.setGlassEnabled(true);
		this.add(verticalPanel);
		
		verticalPanel.setSpacing(20);
		
		cancelButton.setStylePrimaryName("cancelButton");
		confirmButton.setStylePrimaryName("confirmButton");
		itemLabel.setStylePrimaryName("textLabel");
		personLabel.setStylePrimaryName("textLabel");
		storeLabel.setStylePrimaryName("textLabel");
		
		
		cancelButton.setPixelSize(130, 40);
		confirmButton.setPixelSize(130, 40);
		bottomButtonsPanel.setSpacing(20);
		personListBox.setSize("320px"," 40px");
		storeListBox.setSize("320px"," 40px");
		itemListBox.setSize("320px"," 40px");
		
		bottomButtonsPanel.add(confirmButton);
		bottomButtonsPanel.add(cancelButton);
		
		radioButtonPanel.setSpacing(5);
		radioButtonPanel.add(existingButton);
		radioButtonPanel.add(existingLabel);
		radioButtonPanel.add(newButton);
		radioButtonPanel.add(newLabel);
	
		verticalPanel.add(itemLabel);
		verticalPanel.add(radioButtonPanel);
		verticalPanel.add(itemListBox);
		
		verticalPanel.add(storeLabel);
		verticalPanel.add(storeListBox);
		
		verticalPanel.add(personLabel);
		verticalPanel.add(personListBox);
		
		verticalPanel.add(bottomButtonsPanel);
	
//		emailButton.addValueChangeHandler(new EmailChangeHandler()); 
//		nameButton.addValueChangeHandler(new NameChangeHandler());	
//		
//		confirmButton.addClickHandler(new RemoveClickHandler());
//		cancelButton.addClickHandler(new CancelClickHandler());

		verticalPanel.setCellHorizontalAlignment(radioButtonPanel, HasHorizontalAlignment.ALIGN_CENTER);
		verticalPanel.setCellHorizontalAlignment(personListBox,HasHorizontalAlignment.ALIGN_CENTER);
		verticalPanel.setCellHorizontalAlignment(itemListBox,HasHorizontalAlignment.ALIGN_CENTER);
		verticalPanel.setCellHorizontalAlignment(storeListBox,HasHorizontalAlignment.ALIGN_CENTER);
		verticalPanel.setCellHorizontalAlignment(bottomButtonsPanel, HasHorizontalAlignment.ALIGN_CENTER);
		this.center();
	}

	
	/**
	 * Load Methode, damit werden alle User mittels der contactAdministration
	 * geladen
	 */
	private void load() {
		//administration.getAllItems(new GetAllItemsCallback());
	}
	
	

	
	/**
	 * Implementierung der ListBox, wird bei der Instanziierung augfgerufen
	 */
	public void loadListBox() {
	}
	}
