package de.hdm.softwarepraktikum.client.gui.report;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;

import de.hdm.softwarepraktikum.client.ClientsideSettings;
import de.hdm.softwarepraktikum.client.gui.Notification;
import de.hdm.softwarepraktikum.shared.ReportGeneratorAsync;
import de.hdm.softwarepraktikum.shared.report.HTMLReportWriter;
import de.hdm.softwarepraktikum.shared.report.ItemsByPersonReport;

public class ReportbyPerson {
	
	//private person p = CurrentReportUser.getUser();
	private ReportGeneratorAsync report = ClientsideSettings.getReportGenerator();

	private ReportEntry reportGenerator = new ReportEntry();
	private HorizontalPanel ReportHeaderPanel = new HorizontalPanel();
	
	private Label ILabel = new Label("Einkaufsstattistik");
	private Button back = new Button("");
	
	
	public void onLoad() {

		//this.setWidth("100vw");
		
		
		back.setStylePrimaryName("back");
		ReportHeaderPanel.setStylePrimaryName("ReportHeaderPanel");
		ILabel.setStylePrimaryName("ILabel");

		ReportHeaderPanel.add(back);
		ReportHeaderPanel.add(ILabel);
		
		back.setWidth("8vh");
		back.setHeight("8vh");
		ReportHeaderPanel.setWidth("100%");
		ReportHeaderPanel.setHeight("8vh");
		
		ReportHeaderPanel.setCellVerticalAlignment(ILabel, null);
			
		back.addClickHandler(new BackClickHandler());
		
		RootPanel.get("Selection").add(ReportHeaderPanel);

		
		// Probleme mit Asynccallback
		//report.createUserStatisticsReport(p,new ReportbyPersonCallback() );
		
		//report.createUserStatisticsReport(p, Personstatisticcallback);

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


	private class Personstatisticcallback implements AsyncCallback<ItemsByPersonReport> {

		
		public void onFailure(Throwable caught) {
			Notification.show(caught.toString() + "Callback Fehler");
		}


		public void onSuccess(ItemsByPersonReport result) {
			HTMLReportWriter writer = new HTMLReportWriter();

			writer.process(result);
			
			HTML content = new HTML(writer.getReportText());

			RootPanel.get("Result").add(content);
		}
	}
	
}
	

		

