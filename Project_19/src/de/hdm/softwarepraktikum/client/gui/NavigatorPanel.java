package de.hdm.softwarepraktikum.client.gui;

import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class NavigatorPanel extends TabPanel {
	

private VerticalPanel contentPanelGroups = new VerticalPanel();
private VerticalPanel contentPanelShoppinglists = new VerticalPanel();
private VerticalPanel contentPanelStores = new VerticalPanel();
private VerticalPanel contentPanelArticles = new VerticalPanel();
		
/**
 * ***************************************************************************
 * ABSCHNITT der Methoden
 * ***************************************************************************
 */


 /**
  * In dieser Methode wird das Design des NavigatorPanels und der Widgets festgelegt. 
  * Diese Methode wird aufgerufen, sobald ein Objekt dieser Klasse instanziert wird. 
  */
public void onLoad() {


		this.setWidth("35vw");
		this.setAnimationEnabled(true);
		
		
		
		this.add(contentPanelGroups, "Gruppen");
		this.selectTab(0);
		this.add(contentPanelShoppinglists, "Einkaufslisten");
		this.add(contentPanelStores, "Alle Händler");
		this.add(contentPanelArticles, "Alle Artikel");
		
	}
}

