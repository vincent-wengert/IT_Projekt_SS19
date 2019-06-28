package de.hdm.softwarepraktikum.client.gui;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.softwarepraktikum.client.ClientsideSettings;
import de.hdm.softwarepraktikum.client.Project_19.CurrentPerson;
import de.hdm.softwarepraktikum.shared.ShoppingListAdministrationAsync;
import de.hdm.softwarepraktikum.shared.bo.Person;

/**
 * Dieses PopupPanel wird der MenuBar im Header hinzugefügt um den Namen der aktuellen <code>Person</code> zu aktualisieren.
 * @author Jan Duwe
 *
 */
public class EditPersonDialog extends PopupPanel{
	private Person currentPerson = CurrentPerson.getPerson();
	private ShoppingListAdministrationAsync administration = ClientsideSettings.getShoppinglistAdministration();
	
	private VerticalPanel verticalPanel = new VerticalPanel();
	private HorizontalPanel bottomButtonsPanel = new HorizontalPanel();
	private Grid settingsGrid = new Grid(1,2);
	
	private Button confirmButton = new Button("\u2714");
	private Button cancelButton = new Button("\u2716");
	private TextBox userNameTextBox = new TextBox();
	
	private Label accountLabel = new Label(currentPerson.getGmail());
	private Label nameChangeLabel = new Label("Name \u00E4ndern");
	
	private Header header;
	
	/**
 	 * Im Konstruktor der Klasse <code>EditPersonDialog</code> werden die Widget in das PopupPanel hinzugefügt.
 	 */
	public EditPersonDialog() {
		this.setTitle("Einstellungen");
		this.setGlassEnabled(true);
		this.add(verticalPanel);
		verticalPanel.setSpacing(20);

		accountLabel.setStylePrimaryName("textLabel");
		cancelButton.setStylePrimaryName("cancelButton");
		confirmButton.setStylePrimaryName("confirmButton");
		
		cancelButton.setPixelSize(130, 40);
		confirmButton.setPixelSize(130, 40);
		
		settingsGrid.setWidget(0, 0, nameChangeLabel);
		settingsGrid.setWidget(0, 1, userNameTextBox);
//		itemGrid.setWidget(1, 0, accountDeleteLabel);

		settingsGrid.setCellSpacing(5);
		
		userNameTextBox.setText(currentPerson.getName());

		bottomButtonsPanel.add(confirmButton);
		bottomButtonsPanel.add(cancelButton);

		verticalPanel.add(accountLabel);
		verticalPanel.add(settingsGrid);
		verticalPanel.add(bottomButtonsPanel);
		
		confirmButton.addClickHandler(new UpdatePersonClickHandler());
		cancelButton.addClickHandler(new CancelClickHandler());

		verticalPanel.setCellHorizontalAlignment(bottomButtonsPanel, HasHorizontalAlignment.ALIGN_CENTER);
		
		this.center();
	}
	
	/**
	 * ************************* ABSCHNITT der Methoden *************************
	 */
	
	/**
	 * Methode zum Setzen des <code>Header</code> Objekts des <code>EditPersonDialog</code>.
	 * @param header
	 */
	public void setHeader(Header header) {
		this.header = header;
	}
	
	/**
	 * ************************* ABSCHNITT der Click-/EventHandler *************************
	 */
	
	/**
	 * ClickHandler zum aktualisieren des Namen der aktuellen <code>Person</code>.
	 */
	private class UpdatePersonClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent arg0) {
			currentPerson.setName(userNameTextBox.getText());
			
			administration.updatePerson(currentPerson, new AsyncCallback<Void>() {

				@Override
				public void onFailure(Throwable arg0) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void onSuccess(Void arg0) {
					EditPersonDialog.this.hide();
					header.getLogoutMenu().setHTML(currentPerson.getName());
				}
				
			});
			
		}
		
	}
	
	/**
	 *ClickHandler um den Bearbeitungsvorgang abzubrechen.
	 */
	private class CancelClickHandler implements ClickHandler {
		@Override
		public void onClick(ClickEvent event) {
			EditPersonDialog.this.hide();
		}
	}
}
