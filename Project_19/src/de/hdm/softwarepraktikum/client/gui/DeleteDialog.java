package de.hdm.softwarepraktikum.client.gui;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class DeleteDialog extends PopupPanel{
	private StoreForm sf = null;
	
	private VerticalPanel vp = new VerticalPanel();

	private Button confirmButton = new Button("\u2714");
	private Button cancelButton = new Button("\u2716");
	
	private Grid buttonGrid = new Grid(1,2);
	private Label infoLabel = new Label("Wollen sie die Auswahl entfernen?");
	
	private Boolean confirm = false; 
	
	public DeleteDialog(Object object) {
		this.setGlassEnabled(true);
		this.add(vp);
		
		vp.setSpacing(20);
		vp.add(infoLabel);
		vp.add(buttonGrid);
		
		cancelButton.setStylePrimaryName("cancelButton");
		confirmButton.setStylePrimaryName("confirmButton");
		infoLabel.setStylePrimaryName("textLabel");
		
		cancelButton.addClickHandler(new CancelClickHandler());
		confirmButton.addClickHandler(new ConfirmClickHandler());
		
		cancelButton.setPixelSize(130, 40);
		confirmButton.setPixelSize(130, 40);
		
		buttonGrid.setWidget(0, 0, confirmButton);
		buttonGrid.setWidget(0, 1, cancelButton);
		
		
		this.center();
	}
	
	public Boolean getConfirm() {
		return this.confirm;
	}
	
	public void setConfirm(Boolean b) {
		this.confirm = b;
	}
	
	public void setStoreForm(StoreForm sf) {
		this.sf = sf;
	}
	
	private class ConfirmClickHandler implements ClickHandler {
		
		@Override
		public void onClick(ClickEvent event) {
			setConfirm(true);
			DeleteDialog.this.hide();
		}
		
	}
	
	private class CancelClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			setConfirm(false);
			DeleteDialog.this.hide();
		}
		
	}
}
