package de.hdm.softwarepraktikum.client.gui.report;


import java.util.ArrayList;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.softwarepraktikum.client.ClientsideSettings;
import de.hdm.softwarepraktikum.client.ReportEntry;
import de.hdm.softwarepraktikum.client.gui.Notification;
import de.hdm.softwarepraktikum.shared.ReportGeneratorAsync;
import de.hdm.softwarepraktikum.shared.bo.Group;
import de.hdm.softwarepraktikum.shared.bo.Person;
import de.hdm.softwarepraktikum.shared.report.HTMLReportWriter;
import de.hdm.softwarepraktikum.shared.report.ItemsByGroupReport;

/**
 * Die Klasse <code>ReportByGroup</code> ist eine Erstellungsform zur Erzeugung 
 * eines neuen <code>ItemsByGroupReport</code>
 * @author Bruno Herceg
 *
 */
public class ReportbyGroup extends VerticalPanel{
	/*
	private Person p = CurrentReportPerson.getUser();
	
	private Group g =
	*/
	private ReportGeneratorAsync report = ClientsideSettings.getReportGenerator();
	private ReportEntry reportGenerator = new ReportEntry();
	
	private VerticalPanel selectionPanel = new VerticalPanel();
	private HorizontalPanel reportHeaderPanel = new HorizontalPanel();
	
	private ArrayList<Group> groupList = new ArrayList<Group>();
	
	private Label ILabel = new Label("Statistik der eingekauften Produkte der Gruppe");
	private Button printReportButton = new Button("");
	private Button back = new Button("");
	private ListBox listBox = new ListBox();
	
	
	/**
	 * Festlegen der Panels und der Buttons.
	 * Diese Methode startet, sobald diese Klasse aufgerufen wird. 
	 */
	public void onLoad() {

		this.setWidth("100%");
		
		
		selectionPanel.setStylePrimaryName("selectionPanel");
		reportHeaderPanel.setStylePrimaryName("reportHeaderPanel");
		ILabel.setStylePrimaryName("infoLabel");
		printReportButton.setStylePrimaryName("printReportButton");
		back.setStylePrimaryName("back");
		
		selectionPanel.setWidth("100%");
		selectionPanel.setHeight("100%");

		reportHeaderPanel.add(back);
		reportHeaderPanel.add(ILabel);
		
		printReportButton.setWidth("8vh");
		printReportButton.setHeight("8vh");

		back.setWidth("8vh");
		back.setHeight("8vh");
		
		listBox.setWidth("320px");
		listBox.setHeight("40px");
		
		reportHeaderPanel.setWidth("100%");
		reportHeaderPanel.setHeight("8vh");

		reportHeaderPanel.setCellVerticalAlignment(ILabel, null);
		
		printReportButton.addClickHandler(new OutputClickHandler());
		back.addClickHandler(new BackClickHandler());
		
		//report.getAllItems(i, new Groupstatisticcallback());

		RootPanel.get("Selection").add(reportHeaderPanel);

	}
	
	/**
	 * Clickhandler, um einen Report auszugeben.
	 *
	 */
	private class OutputClickHandler implements ClickHandler{

		@Override
		public void onClick(ClickEvent event) {
			
			RootPanel.get("Result").clear();
			Group selectedG = new Group();
			
			for(Group g: groupList) {
				if(g.getTitle().equals(listBox.getSelectedItemText())) {
					selectedG = g;
					report.createGroupStatisticsReport(selectedG, new Groupstatisticcallback());
				}
			}
			
		}
		
	}


	/**
	 * Clickhandler um auf die Startseite des ReportGenerators zu gelangen 
	 * 
	 */
	private class BackClickHandler implements ClickHandler {

		public void onClick(ClickEvent event) {

			RootPanel.get("Result").clear();
			RootPanel.get("Selection").clear();
			reportGenerator.onModuleLoad();


		}
	}


	private class Groupstatisticcallback implements AsyncCallback<ItemsByGroupReport> {


		public void onFailure(Throwable caught) {
			Notification.show(caught.toString() + "Callback Fehler");
		}


		public void onSuccess(ItemsByGroupReport result) {
			HTMLReportWriter writer = new HTMLReportWriter();

			writer.process(result);

			HTML content = new HTML(writer.getReportText());

			RootPanel.get("Result").add(content);
		}
	}
	

}
