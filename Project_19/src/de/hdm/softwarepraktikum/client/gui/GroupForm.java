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
import com.google.gwt.user.client.ui.ListBox;
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
import de.hdm.softwarepraktikum.client.Project_19.CurrentPerson;
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

	private Person currentPerson = CurrentPerson.getPerson();

	private ShoppingListAdministrationAsync administration = ClientsideSettings.getShoppinglistAdministration();
	private CustomTreeModel ctm = null;
	private AllItemsCellList aicl = null;

	private HorizontalPanel formHeaderPanel = new HorizontalPanel();
	private HorizontalPanel bottomButtonsPanel = new HorizontalPanel();
	private HorizontalPanel topButtonsPanel = new HorizontalPanel();
	private VerticalPanel vp = new VerticalPanel();


	private Label infoTitleLabel = new Label("Neue Gruppe erstellen");
	private Label groupNameLabel = new Label("Name der Gruppe");
	private Label groupMembersLabel = new Label("Mitglieder");
	private Label addedGroupMembersLabel = new Label("Mitglieder der Gruppe");

	private TextBox groupNameBox = new TextBox();

	private Button confirmButton = new Button("\u2714");
	private Button cancelButton = new Button("\u2716");
	private Grid groupGrid = new Grid(3, 3);
	private Button editButton = new Button();
	private Button deleteButton = new Button();

	private ArrayList<Person> allPersons = new ArrayList<Person>();
	private ArrayList<Person> membersList = new ArrayList<Person>();
	
	private ArrayList<Person> membersToAdd = new ArrayList<Person>();
	private ArrayList<Person> membersToRemove= new ArrayList<Person>();
	private Person selectedPerson = new Person();

	private Group groupToDisplay = null;

	private Boolean initial;
	private Boolean editable;

	private MultiWordSuggestOracle personSearchBar = new MultiWordSuggestOracle();
	private final SuggestBox personSuggestBox = new SuggestBox(personSearchBar);
	
	private ListBox groupMembersListBox = new ListBox();
	private ListBox addMemberListBox = new ListBox();

	// SearchForm
	private Grid searchGridPersons = new Grid(1, 3);
	private Button addGroupMember = new Button("\u271A");
	private Button removeGroupMember = new Button("\u2716");
	
	private GroupForm newGroupForm;

	/**
	 * Bei der Instanziierung der <code>GroupForm</code> werden die
	 * ClickHandler den Buttons und die Buttons anschließend dem Panel hinzugefügt.
	 */
	public GroupForm() {
		editButton.addClickHandler(new EditClickHandler());
		deleteButton.addClickHandler(new DeleteClickHandler());

		confirmButton.addClickHandler(new CreateClickHandler());
		bottomButtonsPanel.add(confirmButton);

		cancelButton.addClickHandler(new CancelClickHandler());
		bottomButtonsPanel.add(cancelButton);
		
		addGroupMember.addClickHandler(new AddGroupMemberClickHandler());
		removeGroupMember.addClickHandler(new RemoveGroupMemberClickHandler());
	}
	
	/**
	 * ************************* ABSCHNITT der Methoden *************************
	 */

	/**
	 * In dieser Methode werden die Widgets der Form hinzugefügt. Außerdem findet
	 * hier die Formatierungen der Widgets statt.
	 */
	public void onLoad() {
		this.load();
		
		this.setWidth("100%");
		formHeaderPanel.setStylePrimaryName("formHeaderPanel");
		infoTitleLabel.setStylePrimaryName("infoTitleLabel");
		bottomButtonsPanel.setStylePrimaryName("bottomButtonsPanel");
		cancelButton.setStylePrimaryName("cancelButton");
		confirmButton.setStylePrimaryName("confirmButton");
		editButton.setStylePrimaryName("editButton");
		deleteButton.setStylePrimaryName("deleteButton");
		topButtonsPanel.setStylePrimaryName("topButtonsPanel2");

		cancelButton.setPixelSize(130, 40);
		confirmButton.setPixelSize(130, 40);
		addMemberListBox.setSize("320px", "40px");

		formHeaderPanel.add(infoTitleLabel);
		formHeaderPanel.add(topButtonsPanel);

		topButtonsPanel.add(editButton);
		topButtonsPanel.add(deleteButton);

		bottomButtonsPanel.setSpacing(20);

		this.add(formHeaderPanel);

		groupNameBox.setMaxLength(15);

		groupGrid.setCellSpacing(10);
		
		groupGrid.setWidget(0, 0, groupNameLabel);
		groupGrid.setWidget(0, 1, groupNameBox);
		groupGrid.setWidget(1, 0, groupMembersLabel);
		
		groupMembersListBox.setWidth("300px");

		addMemberListBox.setSize("300px", "27px");
		removeGroupMember.setPixelSize(30, 30);
		addMemberListBox.getElement().setPropertyString("placeholder", "Mitgliedernamen eingeben...");
		removeGroupMember.setStylePrimaryName("cancelSearchButton");
		addGroupMember.setStylePrimaryName("addPersonButton");

		// searchGridPersons.setWidget(0, 0, personSuggestBox);
		searchGridPersons.setWidget(0, 0, addMemberListBox);
		searchGridPersons.setWidget(0, 1, addGroupMember);
		

		groupGrid.setWidget(1, 1, searchGridPersons);
		groupGrid.setWidget(2, 0, addedGroupMembersLabel);
		groupGrid.setWidget(2, 1, groupMembersListBox);
		groupGrid.setWidget(2, 2, removeGroupMember);
		
		this.add(groupGrid);
		this.setCellHorizontalAlignment(groupGrid, ALIGN_CENTER);

		this.add(bottomButtonsPanel);
		this.setCellHorizontalAlignment(bottomButtonsPanel, ALIGN_CENTER);

		setTableEditable(editable);

	}
	
	/**
	 * Methode zum Auslesen des gmail Attributts der übergebenen <code>Person</code>.
	 * 
	 * @param person wird mit allen <code>Person</code> Objekten innerhalb der ArrayList
	 * allPersons verglichen und gibt bei einer Übereinstimmung das gmail Attribut zurück.
	 * 
	 * @return Gmail Attribut der <code>Person</code>.
	 */
	private String getGmail(Person person) {
			for(Person p1: allPersons) {
				if(person.getId() == p1.getId()) {
					return p1.getGmail();
				}
			}
			return null;
	}

	/**
	 * Methode um die <code>Person</code> Objekte, die Teil einer <code>Group</code>
	 * sind anzuzeigen. 
	 */
	private void showGroupMembers() {

		if (groupToDisplay.getMember().isEmpty() == false) {
		groupMembersListBox.clear();
		
		for (Person p : groupToDisplay.getMember()) {
			groupMembersListBox.addItem(p.getName());
		}
		groupMembersListBox.setVisibleItemCount(groupToDisplay.getMember().size());
		}
	}
	
	/**
	 * Methode um alle <code>Person</code> Objekte zu laden.
	 */
	private void load() {
		administration.getAllPersons(new getAllPersonsCallback());
	}

	/**
	 * Methode um die addMemberListBox mit allen <code>Person</code> 
	 * Objekten zu befüllen. 
	 */
	public void loadSearchbar() {
		
		addMemberListBox.clear();
		
		for(Person person : allPersons) {
			if(person.getId() != currentPerson.getId()) {
				addMemberListBox.addItem(person.getGmail());
			}
				
		}
		
		if(groupToDisplay.getMember().isEmpty()) {
		for(Person person : groupToDisplay.getMember()) {

			for (int i = 0; i < addMemberListBox.getItemCount(); i++) {
				if (addMemberListBox.getItemText(i) == getGmail(person)) {
					addMemberListBox.removeItem(i);
					}
				}
			}
		}
	}
	/**
	 * Setzt die aktuell ausgewählte Person
	 * 
	 * @param gMail Die Email-Adresse des selektierten <code>Person</code>
	 */

	private void setSelectedPerson(String value) {
		// TODO Auto-generated method stub
		for (Person p : allPersons) {
			if (p.getGmail() == value) {
				selectedPerson = p;
			}
		}
	}
	
	/**
	 * Methode um die ausgewählte <code>Group</code> zu setzen und in den Widgets anzuzeigen.
	 * @param g das ausgewählte <code>Group</code> Objekt.
	 */
	public void setSelected(Group g) {
		groupToDisplay = g;
		infoTitleLabel.setText(groupToDisplay.getTitle());
		groupNameBox.setText(groupToDisplay.getTitle());
	}
	
	/**
	 * /**
	 * Methode um die <code>GroupForm</code> in den EditModus zu setzen oder den EditModus zu beenden.
	 * @param editable setzt für true den EditModus und verlässt ihn für false.
	 */
	public void setTableEditable(Boolean editable) {
		if (editable == true) {
			groupNameBox.setEnabled(true);
			groupNameBox.setFocus(true);
			groupGrid.setWidget(1, 0, groupMembersLabel);
			groupGrid.setWidget(1, 1, searchGridPersons);
			bottomButtonsPanel.setVisible(true);
			topButtonsPanel.setVisible(false);
			removeGroupMember.setVisible(true);
			groupGrid.setWidget(2, 0, addedGroupMembersLabel);
			groupGrid.setWidget(2, 1, groupMembersListBox);
			groupGrid.setWidget(2, 2, removeGroupMember);
		} else {
			groupNameBox.setEnabled(false);
			groupNameBox.setFocus(false);
			groupMembersLabel.removeFromParent();
			searchGridPersons.removeFromParent();
			removeGroupMember.removeFromParent();
			bottomButtonsPanel.setVisible(false);
			topButtonsPanel.setVisible(true);
			removeGroupMember.setVisible(false);
			groupGrid.setWidget(1, 0, addedGroupMembersLabel);
			groupGrid.setWidget(1, 1, groupMembersListBox);
			groupMembersListBox.setFocus(false);
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
	
	/**
	 * Methode zum Auslesen des Boolean Attributs initial innerhalb der <code>GroupForm</code>.
	 * 
	 * @return der Boolean initial der <code>GroupForm</code>.
	 */
	public Boolean getInitial() {
		return initial;
	}
	
	/**
	 * Setzen des Boolean Attributs initial innerhalb der <code>GroupForm</code>.
	 * 
	 * @param initial Mithilfe des Wertes wird entschieden ob die <code>GroupForm</code>
	 * sich im Neu-Anlegen-Modus befindet oder im Editier-Modus.
	 */
	public void setInitial(Boolean initial) {
		this.initial = initial;
	}

	/**
	 * Auslesen des Boolean Attributs editable innerhalb der <code>GroupForm</code>.
	 * 
	 * @return Boolean der Auskunft darüber gibt, ob die <code>GroupForm</code> sich im
	 * Editier-Modus befindet.
	 */
	public Boolean getEditable() {
		return editable;
	}
	
	/**
	 * Setzen des Boolean Attributs editable innerhalb der <code>GroupForm</code>.
	 * 
	 * @param initial Mithilfe des Wertes wird entschieden ob die <code>ItemForm</code> 
	 * sich im Neu-Anlegen-Modus befindet oder im Editier-Modus.
	 */
	public void setEditable(Boolean editable) {
		this.editable = editable;
	}
	
	/**
	 * Auslesen des <code>CustomTreeModel</code> innerhalb der <code>GroupForm</code>.
	 * @return das <code>CustomTreeModel</code> innerhalb der <code>GroupForm</code>.
	 */
	public CustomTreeModel getCtm() {
		return ctm;
	}
	
	/**
	 * Setzen des <code>CustomTreeModel</code> innerhalb der <code>GroupForm</code>.
	 * 
	 * @param ctm neues <code>CustomTreeModel</code> innerhalb der <code>GroupForm</code>
	 */
	public void setCtm(CustomTreeModel ctm) {
		this.ctm = ctm;
	}
	
	/**
	 * Setzen der <code>AllItemsCellList</code> innerhalb der <code>GroupForm</code>.
	 * 
	 * @param aicl neue <code>AllItemsCellList</code> innerhalb der <code>GroupForm</code>
	 */
	public void setAllItemsCelllist(AllItemsCellList aicl) {
		this.aicl = aicl;
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
			groupNameBox.setText(groupToDisplay.getTitle());
			addMemberListBox.clear();
			groupMembersListBox.clear();
			loadSearchbar();
			showGroupMembers();
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
				membersList.add(currentPerson);
				administration.createGroup(groupNameBox.getText(),membersList, new createGroupCallback());
				setEditable(false);
				setTableEditable(editable);
			} else {
				groupToDisplay.setTitle(groupNameBox.getText());
				administration.updateGroup(groupToDisplay, new updateGroupCallback());
				
				if(membersToAdd.isEmpty() == false) {
					for(Person p : membersToAdd) {
						administration.addGroupMembership(p, groupToDisplay, new AddGroupMembershipCallback());
						groupToDisplay.getMember().add(p);
					}
				}else if(membersToRemove.isEmpty() == false) {
					for(Person p : membersToRemove) {
						administration.deleteGroupMembership(p, groupToDisplay , new RemoveGroupMembershipCallback());
						groupToDisplay.getMember().remove(p);
					}
				}
				
				setEditable(false);
				setTableEditable(editable);
			}

		}
	}
	
	/**
	 * Versetzt die <code>GroupForm</code> in den Editier-Modus durch den Aufruf der Methode
	 * setTableEditable().
	 */
	private class EditClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			setEditable(true);
			setTableEditable(editable);
			setInitial(false);
			loadSearchbar();
			groupMembersListBox.clear();
			showGroupMembers();
		}
	}

	/**
	 * ClickHandler mit dem das im <code>CustomTreeModel</code> 
	 * ausgewählte <code>Group</code> Objekt gelöscht wird.
	 */
	private class DeleteClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			if(Window.confirm("Wollen Sie wirklich entfernen?") == true) {
				administration.deleteGroup(groupToDisplay, new deleteGroupCallback());
			}
		}
	}

	/**
	 * ClickHandler mit dem die ausgewählte <code>Person</code> zur 
	 * ausgewählten <code>Group</code> hinzugefügt wird.
	 */
	private class AddGroupMemberClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			if (initial == true) {
			// setSelectedPerson(personSuggestBox.getValue());
			setSelectedPerson(addMemberListBox.getSelectedItemText());
			addMemberListBox.removeItem(addMemberListBox.getSelectedIndex());
			membersList.add(selectedPerson);
			groupMembersListBox.addItem(selectedPerson.getName());
			groupMembersListBox.setVisibleItemCount(membersList.size()+1);
			
			}else {
			setSelectedPerson(addMemberListBox.getSelectedItemText());
			membersToAdd.add(selectedPerson);
			groupMembersListBox.addItem(selectedPerson.getName());			
			addMemberListBox.removeItem(addMemberListBox.getSelectedIndex());
			groupMembersListBox.setVisibleItemCount(groupMembersListBox.getVisibleItemCount()+1);
			}
		}
	}
	
	/**
	 * ClickHandler mit dem die ausgewählte <code>Person</code> von der
	 * ausgewählten <code>Group</code> entfernt wird.
	 */
	private class RemoveGroupMemberClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			for (Person p : allPersons) {
				//TODO hier kommt der angemeldete user rein
				if (p.getName().equals(groupMembersListBox.getSelectedItemText())) {
					selectedPerson = p;
					if(initial == true) {
						groupMembersListBox.removeItem(groupMembersListBox.getSelectedIndex());
						membersList.remove(p);
						groupMembersListBox.setVisibleItemCount(membersList.size()+1);
						addMemberListBox.addItem(selectedPerson.getGmail());

					}else {
						groupMembersListBox.removeItem(groupMembersListBox.getSelectedIndex());
						membersList.remove(p);
						groupMembersListBox.setVisibleItemCount(groupToDisplay.getMember().size());
						addMemberListBox.addItem(selectedPerson.getGmail());
						membersToRemove.add(selectedPerson);
						groupMembersListBox.removeItem(groupMembersListBox.getSelectedIndex());
						groupToDisplay.getMember().remove(selectedPerson);
						addMemberListBox.setVisibleItemCount(groupToDisplay.getMember().size());
					}
				}
			}	
		}
	}

	/**
	 * ************************* ABSCHNITT der Callbacks *************************
	 */

	/**
	 * Callback mit dem alle <code>Person</code> Objekte aus der Datenbank geladen werden.
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
			if (initial == false){
			administration.getAllGroupMembers(groupToDisplay.getId(), new getAllGroupMembersCallback());
			}else {
				for(Person person : allPersons) {
					addMemberListBox.addItem(person.getGmail());
					
				}
				GroupForm.this.loadSearchbar();
			}
		}
	}
	
	/**
	 * Callback mit dem Mitgliedschaften von <code>Person</code> Objekten in <code>Group</code> Objekten
	 * in die Datenbank hinzugefügt werden.
	 */
	private class AddGroupMembershipCallback implements AsyncCallback<Void> {

		@Override
		public void onFailure(Throwable caught) {
			Notification.show("Die Gruppenmitglieder konnten leider nicht gefunden werden:\n" + caught.toString());
		}

		@Override
		public void onSuccess(Void result) {
			Notification.show(selectedPerson.getName() + " wurde der Gruppe hinzugefügt");
		}
	}
	
	/**
	 * Callback mit dem Mitgliedschaften von <code>Person</code> Objekten in <code>Group</code> Objekten
	 * aus der Datenbank entfernt werden.
	 */
	private class RemoveGroupMembershipCallback implements AsyncCallback<Void> {

		@Override
		public void onFailure(Throwable caught) {
			Notification.show("Die Gruppenmitglieder konnten leider nicht entfernt werden:\n" + caught.toString());
		}

		@Override
		public void onSuccess(Void result) {
			Notification.show(selectedPerson.getName() + " wurde aus der Gruppe entfernt");
		}
	}
	
	/**
	 * Callback mit dem alle <code>Person</code> die Mitglieder einer <code>Group</code> sind geladen werden.
	 */
	private class getAllGroupMembersCallback implements AsyncCallback<ArrayList<Person>> {

		@Override
		public void onFailure(Throwable caught) {
			Notification.show("Die Gruppenmitglieder konnten leider nicht gefunden werden:\n" + caught.toString());
		}

		@Override
		public void onSuccess(ArrayList<Person> result) {
			for (Person p : result) {
				for(Person p1: allPersons) {
					if(p.getId() == p1.getId()) {
						p.setName(p1.getName());
					}
				}
			}
			groupToDisplay.setMember(result);
			showGroupMembers();
			GroupForm.this.loadSearchbar();
		}
	}

	/**
	 * Callback mit dem eine neues <code>Group</code> Objekt in die Datenbank geladen wird.
	 */
	private class createGroupCallback implements AsyncCallback<Group> {

		@Override
		public void onFailure(Throwable caught) {
			Notification.show("Die Gruppe konnte leider nicht erstellt werden:\n" + caught.toString());
		}

		@Override
		public void onSuccess(Group result) {
			ctm.updateAddedGroup(result);
			aicl.updateAddedGroup(result);
		}
	}
	
	/**
	 * Callback mit dem Änderungen in einem <code>Group</code> Objekt in die Datenbank geladen werden.
	 */
	private class updateGroupCallback implements AsyncCallback<Void> {

		@Override
		public void onFailure(Throwable caught) {
			Notification.show("Die Gruppe konnte leider nicht aktualisiert werden:\n" + caught.toString());
		}

		@Override
		public void onSuccess(Void result) {
			Notification.show("Die Gruppe wurde aktualisiert");
			ctm.updateGroup(groupToDisplay);
			setSelected(groupToDisplay);
		}
		
	}
	
	/**
	 * Callback mit dem ein <code>Group</code> Objekt aus der Datenbank entfernt wird.
	 */
	private class deleteGroupCallback implements AsyncCallback<Void> {

		@Override
		public void onFailure(Throwable caught) {
			Notification.show("Die Gruppe konnte leider nicht gel�scht werden:\n" + caught.toString());
		}

		@Override
		public void onSuccess(Void result) {
			ctm.updateRemovedGroup(groupToDisplay);
			Notification.show("Die Gruppe wurde gel�scht");
			RootPanel.get("Details").clear();
		}
		
	}

	
}