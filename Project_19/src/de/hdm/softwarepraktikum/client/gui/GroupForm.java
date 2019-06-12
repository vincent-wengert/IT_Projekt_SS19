package de.hdm.softwarepraktikum.client.gui;

import java.util.ArrayList;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.MultiWordSuggestOracle;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.SuggestOracle;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.SuggestOracle.Suggestion;

import de.hdm.softwarepraktikum.shared.bo.Group;
import de.hdm.softwarepraktikum.shared.bo.Person;
import de.hdm.softwarepraktikum.client.ClientsideSettings;
import de.hdm.softwarepraktikum.shared.ShoppingListAdministrationAsync;
import de.hdm.softwarepraktikum.shared.bo.Person;

/**
 * Die Klasse <code>NewGroupForm</code> ist eine Form die verschiedene Methoden
 * und Widgets zur Erstellung einer neuen <code>Group</code> bietet.
 * 
 * @author VincentWengert
 * @version 1.0
 */

public class GroupForm extends VerticalPanel {

	ShoppingListAdministrationAsync administration = ClientsideSettings.getShoppinglistAdministration();

	private HorizontalPanel formHeaderPanel = new HorizontalPanel();
	private HorizontalPanel bottomButtonsPanel = new HorizontalPanel();
	private HorizontalPanel topButtonsPanel = new HorizontalPanel();
	VerticalPanel vp = new VerticalPanel();

	private Label infoTitleLabel = new Label("Neue Gruppe erstellen");
	private Label groupNameLabel = new Label("Name der Gruppe");
	private Label groupMembersLabel = new Label("Mitglieder");
	private Label addedGroupMembersLabel = new Label("Mitglieder der Gruppe");
	private Label tempGroupMembersLabel = new Label("");

	private TextBox groupNameBox = new TextBox();

	private Button confirmButton = new Button("\u2714");
	private Button cancelButton = new Button("\u2716");
	private Grid groupGrid = new Grid(3, 3);
	private Button editButton = new Button();
	private Button deleteButton = new Button();

	private ArrayList<Person> allPersons = new ArrayList<Person>();
	private ArrayList<Person> membersList = new ArrayList<Person>();
	private Person selectedPerson = null;

	private Group groupToDisplay = null;

	private Boolean initial;
	private Boolean editable;

	private MultiWordSuggestOracle personSearchBar = new MultiWordSuggestOracle();
	private final SuggestBox personSuggestBox = new SuggestBox(personSearchBar);
	String tempString = new String();

	private GroupForm newGroupForm;

	// SearchForm
	private Grid searchGridPersons = new Grid(1, 3);

	private Button addButton = new Button("\u271A");
	private Button cancelSearchButton = new Button("\u2716");

	public GroupForm() {
		editButton.addClickHandler(new EditClickHandler());
		deleteButton.addClickHandler(new DeleteClickHandler());

		confirmButton.addClickHandler(new CreateClickHandler());
		bottomButtonsPanel.add(confirmButton);

		cancelButton.addClickHandler(new CancelClickHandler());
		bottomButtonsPanel.add(cancelButton);
		
		cancelSearchButton.addClickHandler(new CancelSearchClickHandler());
	}

	/**
	 * In dieser Methode werden die Widgets der Form hinzugefügt. Außerdem findet
	 * hier die Formatierungen der Widgets statt.
	 */
	public void onLoad() {

		this.setWidth("100%");
		this.load();
		// groupNameLabel.setStylePrimaryName("textLabel");
		formHeaderPanel.setStylePrimaryName("formHeaderPanel");
		infoTitleLabel.setStylePrimaryName("infoTitleLabel");
		bottomButtonsPanel.setStylePrimaryName("bottomButtonsPanel");
		cancelButton.setStylePrimaryName("cancelButton");
		confirmButton.setStylePrimaryName("confirmButton");
		editButton.setStylePrimaryName("editButton");
		deleteButton.setStylePrimaryName("deleteButton");

		editButton.setHeight("8vh");
		editButton.setWidth("8vh");
		topButtonsPanel.setCellHorizontalAlignment(editButton, ALIGN_LEFT);
		deleteButton.setHeight("8vh");
		deleteButton.setWidth("8vh");
		topButtonsPanel.setCellHorizontalAlignment(deleteButton, ALIGN_RIGHT);

		formHeaderPanel.setHeight("8vh");
		formHeaderPanel.setWidth("100%");
		cancelButton.setPixelSize(130, 40);
		confirmButton.setPixelSize(130, 40);

		formHeaderPanel.add(infoTitleLabel);
		formHeaderPanel.add(topButtonsPanel);
		formHeaderPanel.setCellVerticalAlignment(infoTitleLabel, ALIGN_BOTTOM);
		formHeaderPanel.setCellHorizontalAlignment(infoTitleLabel, ALIGN_LEFT);

		formHeaderPanel.setCellVerticalAlignment(topButtonsPanel, ALIGN_BOTTOM);
		formHeaderPanel.setCellHorizontalAlignment(topButtonsPanel, ALIGN_RIGHT);

		topButtonsPanel.add(editButton);
		topButtonsPanel.add(deleteButton);

		bottomButtonsPanel.setSpacing(20);

		this.add(formHeaderPanel);
		this.add(groupGrid);
		this.setCellHorizontalAlignment(groupGrid, ALIGN_CENTER);

		groupNameBox.setMaxLength(10);

		groupGrid.setCellSpacing(10);
		groupGrid.setWidget(0, 0, groupNameLabel);
		groupGrid.setWidget(0, 1, groupNameBox);
		groupGrid.setWidget(1, 0, groupMembersLabel);

		personSuggestBox.setSize("300px", "27px");
		cancelSearchButton.setPixelSize(30, 30);
		personSuggestBox.getElement().setPropertyString("placeholder", "Mitgliedernamen eingeben...");
		cancelSearchButton.setStylePrimaryName("cancelSearchButton");
		addButton.setStylePrimaryName("addPersonButton");

		searchGridPersons.setWidget(0, 0, personSuggestBox);
		searchGridPersons.setWidget(0, 1, addButton);
		searchGridPersons.setWidget(0, 2, cancelSearchButton);

		groupGrid.setWidget(1, 1, searchGridPersons);
		groupGrid.setWidget(2, 0, addedGroupMembersLabel);
		groupGrid.setWidget(2, 1, tempGroupMembersLabel);

		this.add(bottomButtonsPanel);
		this.setCellHorizontalAlignment(bottomButtonsPanel, ALIGN_CENTER);

		setTableEditable(editable);
	}

	private VerticalPanel showGroupMembers() {
		tempString = " ";
		if (initial == false) {
			for (Person p : groupToDisplay.getMember()) {
				vp.add(new HTML(p.getName()));
			}
		} else if (initial == true) {
			for (Person p : membersList) {
				tempString = tempString +  p.getName() + " ";
				tempGroupMembersLabel.setText(tempString);
			}
		}
		return vp;
	}

	private void load() {
		administration.getAllPersons(new getAllPersonsCallback());
	}

	public void loadSearchbar() {
		for (Person p : allPersons) {

			personSearchBar.add(p.getGmail());
		}

		personSuggestBox.addSelectionHandler(new SelectionHandler<SuggestOracle.Suggestion>() {

			@Override
			public void onSelection(SelectionEvent<Suggestion> arg0) {
				setSelectedUser(personSuggestBox.getValue());
			}
		});
	}

	/**
	 * Setzt die aktuell ausgewählte Person
	 * 
	 * @param gMail Die Email-Adresse des selektierten <code>Person</code>
	 */
	private void setSelectedUser(String value) {
		// TODO Auto-generated method stub
		for (Person p : allPersons) {

			if (p.getGmail().equals(value)) {
				membersList.add(p);
				personSuggestBox.setText(null);
				Notification.show(p.getName() + " wurde zur Auswahl hinzugefügt.");
				showGroupMembers();
			}
		}
	}

	public void setSelected(Group g) {
		groupToDisplay = g;
		infoTitleLabel.setText(groupToDisplay.getTitle());
	}

	public void setTableEditable(Boolean editable) {
		if (editable == true) {
			groupNameBox.setEnabled(true);
			groupNameBox.setFocus(true);
			personSuggestBox.setEnabled(true);
			bottomButtonsPanel.setVisible(true);
			topButtonsPanel.setVisible(false);
		} else {
			groupNameBox.setEnabled(false);
			groupNameBox.setFocus(false);
			personSuggestBox.setEnabled(false);
			bottomButtonsPanel.setVisible(false);
			topButtonsPanel.setVisible(true);
		}
	}

	/**
	 * Methode um die aktuelle <code>NewGroupForm</code> Instanz zu setzen.
	 * 
	 * @param GroupForm die zu setzende <code>Group</code> Objekt.
	 */
	public void setNewGroupForm(GroupForm newGroupForm) {

		this.newGroupForm = newGroupForm;
	}

	public Boolean getInitial() {
		return initial;
	}

	public void setInitial(Boolean initial) {
		this.initial = initial;
	}

	public Boolean getEditable() {
		return editable;
	}

	public void setEditable(Boolean editable) {
		this.editable = editable;
	}

	/**
	 * ***************************************************************************
	 * ABSCHNITT der ClickHandler
	 * ***************************************************************************
	 */

	/**
	 * Hiermit wird der Erstellvorgang eines neuer Gruppe abbgebrochen.
	 */
	private class CancelClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			setEditable(false);
			setTableEditable(editable);
		}
	}

	/**
	 * Sobald die Textfelder für den Namen der Gruppe ausgefüllt wurden, wird ein
	 * neuer <code>Group</code> nach dem Klicken des Bestätigungsbutton erstellt.
	 */
	private class CreateClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			if (initial == true) {
				administration.createGroup(groupNameBox.getText(),membersList, new createGroupCallback());
				setEditable(false);
				setTableEditable(editable);
			} else {
				Window.alert("update");
				groupToDisplay.setTitle(groupNameBox.getText());
				administration.updateGroup(groupToDisplay, new updateGroupCallback());
				setEditable(false);
				setTableEditable(editable);
			}

		}
	}

	private class EditClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			setEditable(true);
			setTableEditable(editable);
			setInitial(false);
		}
	}

	private class DeleteClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			//
		}
	}

	private class CancelSearchClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {

			if (personSuggestBox.getValue() != null) {
				personSuggestBox.setText(null);
				selectedPerson = null;
			}
		}
	}

	/**
	 * Hiermit kann <code>Item</code> Objekt geloscht werden und aus der
	 * <code>AllItemsCelllist</code> Instanz entfernt werden.
	 */
	private class CreatePersonCallback implements AsyncCallback<Person> {

		@Override
		public void onFailure(Throwable caught) {
			Notification.show("Die Person konnte leider nicht erstellt werden:\n" + caught.toString());
		}

		@Override
		public void onSuccess(Person person) {
			// add item to cellist
			// aicl.updateCellList();
			Notification.show("Person wurde erstellt");

		}
	}

	/**
	 * Hiermit kann <code>Item</code> Objekt geloscht werden und aus der
	 * <code>AllItemsCelllist</code> Instanz entfernt werden.
	 */
	private class getAllPersonsCallback implements AsyncCallback<ArrayList<Person>> {

		@Override
		public void onFailure(Throwable caught) {
			Notification.show("Die Person konnte leider nicht erstellt werden:\n" + caught.toString());
		}

		@Override
		public void onSuccess(ArrayList<Person> result) {
			// add item to cellist
			// aicl.updateCellList();
			allPersons = result;
			GroupForm.this.loadSearchbar();

		}
	}

	/**
	 * Hiermit kann <code>Item</code> Objekt geloscht werden und aus der
	 * <code>AllItemsCelllist</code> Instanz entfernt werden.
	 */
	private class createGroupCallback implements AsyncCallback<Void> {

		@Override
		public void onFailure(Throwable caught) {
			Notification.show("Die Gruppe konnte leider nicht erstellt werden:\n" + caught.toString());
		}

		@Override
		public void onSuccess(Void result) {
			// add item to cellist
			// aicl.updateCellList();
			Notification.show("Gruppe wurde erstellt");
		}
	}
	
	private class updateGroupCallback implements AsyncCallback<Void> {

		@Override
		public void onFailure(Throwable caught) {
			Notification.show("Die Gruppe konnte leider nicht aktualisiert werden:\n" + caught.toString());
		}

		@Override
		public void onSuccess(Void result) {
			Notification.show("Die Gruppe wurde aktualisiert");
		}
		
	}
}