package de.hdm.softwarepraktikum.client.gui.report;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;

import de.hdm.softwarepraktikum.client.ClientsideSettings;
import de.hdm.softwarepraktikum.shared.ReportGeneratorAsync;

public class ReportbyPerson {
	
	//private User u = CurrentReportUser.getUser();
	private ReportGeneratorAsync report = ClientsideSettings.getReportGenerator();

	private ReportEntry reportGenerator = new ReportEntry();
	private HorizontalPanel formHeaderPanel = new HorizontalPanel();
	
	private Label infoTitleLabel = new Label("Einkaufsstattistik");
	private Button backButton = new Button("");

	
}
	

		

