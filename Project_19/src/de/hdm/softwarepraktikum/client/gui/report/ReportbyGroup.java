package de.hdm.softwarepraktikum.client.gui.report;


import java.util.ArrayList;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.softwarepraktikum.client.ClientsideSettings;
import de.hdm.softwarepraktikum.client.gui.Notification;
import de.hdm.softwarepraktikum.shared.ReportGeneratorAsync;
import de.hdm.softwarepraktikum.shared.bo.Group;
import de.hdm.softwarepraktikum.shared.report.HTMLReportWriter;
import de.hdm.softwarepraktikum.shared.report.ItemsByGroupReport;

/**
 * Die Klasse <code>ReportByGroup</code> ist eine Erstellungsform zur Erzeugung 
 * eines neuen <code>ItemsByGroupReport</code>
 * @author Bruno Herceg
 *
 */
public class ReportbyGroup extends VerticalPanel{
	
	
	private ReportGeneratorAsync report = ClientsideSettings.getReportGenerator();
	private ReportEntry reportGenerator = new ReportEntry();
	
	private VerticalPanel selectionPanel = new VerticalPanel();
	private HorizontalPanel reportHeaderPanel = new HorizontalPanel();
	
	private ArrayList<Group> GroupList = new ArrayList<Group>();
	
	private Label ILabel = new Label("Statistik der eingekauften Produkte der Gruppe");
	private Button printReportButton = new Button("");
	private Button back = new Button("");
	
	
	/**
	 * Festlegen der Panels und der Buttons.
	 * Diese Methode startet, sobald diese Klasse aufgerufen wird. 
	 */
	public void onLoad() {


		back.setStylePrimaryName("back");
		reportHeaderPanel.setStylePrimaryName("reportHeaderPanel");
		ILabel.setStylePrimaryName("infoLabel");

		reportHeaderPanel.add(back);
		reportHeaderPanel.add(ILabel);

		back.setWidth("8vh");
		back.setHeight("8vh");
		reportHeaderPanel.setWidth("100%");
		reportHeaderPanel.setHeight("8vh");

		reportHeaderPanel.setCellVerticalAlignment(ILabel, null);

		back.addClickHandler(new BackClickHandler());

		RootPanel.get("Selection").add(reportHeaderPanel);

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
