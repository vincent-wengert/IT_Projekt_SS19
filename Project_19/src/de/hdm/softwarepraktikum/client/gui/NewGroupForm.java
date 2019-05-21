package de.hdm.softwarepraktikum.client.gui;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.MultiWordSuggestOracle;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * Die Klasse <code>NewGroupForm</code> ist eine Form die verschiedene Methoden und Widgets zur Erstellung
 * einer neuen <code>Group</code> bietet.
 * 
 * @author VincentWengert
 * @version 1.0
 */

public class NewGroupForm extends VerticalPanel {

	private HorizontalPanel formHeaderPanel = new HorizontalPanel();
	private HorizontalPanel bottomButtonsPanel = new HorizontalPanel();

	private Label infoTitleLabel = new Label("Neue Gruppe erstellen");
	private Label groupNameLabel = new Label("Name der Gruppe");

	private TextBox groupNameBox = new TextBox();

	private Button confirmButton = new Button("\u2714");
	private Button cancelButton = new Button("\u2716");
	private Grid groupGrid = new Grid(3, 3);

	private NewGroupForm newGroupForm;

	public NewGroupForm() {

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
		//groupNameLabel.setStylePrimaryName("textLabel");
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
		this.add(groupGrid);
		this.setCellHorizontalAlignment(groupGrid, ALIGN_CENTER);

		groupNameBox.setMaxLength(10);
	
		groupGrid.setCellSpacing(10);
		groupGrid.setWidget(0, 0, groupNameLabel);
		groupGrid.setWidget(0, 1, groupNameBox);
		
		this.add(bottomButtonsPanel);
		this.setCellHorizontalAlignment(bottomButtonsPanel, ALIGN_CENTER);
	}
	
	/**
	 * Methode um die aktuelle <code>NewGroupForm</code> Instanz zu setzen.
	 * 
	 * @param NewGroupForm die zu setzende <code>Group</code> Objekt.
	 */
	public void setNewGroupForm(NewGroupForm newGroupForm) {

		this.newGroupForm = newGroupForm;
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
			RootPanel.get("Details").clear();
		}
	}

	
	/**
	 * Sobald die Textfelder für den Namen der Gruppe ausgefüllt wurden, wird
	 * ein neuer <code>Group</code> nach dem Klicken des Bestätigungsbutton erstellt.
	 */
	private class CreateClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
		RootPanel.get("Details").clear();
		}
	}
}
