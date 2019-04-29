package de.hdm.softwarepraktikum.shared.report;

import java.util.ArrayList;

public class HTMLReportWriter extends ReportWriter {
	
	/**
	 *  Der <code>ReportWriter</code> formatiert Reports mittels HTML. Das im
	 * Zielformat vorliegende Ergebnis wird in der Variable reportText
	 * abgelegt und kann nach Aufruf der entsprechenden Prozessierungsmethode mit
	 * getReportText() ausgelesen werden.
	 * 
	 * @author Luca Randecker
	 * @version 1.0
	 */
	
	private static final long serialVersionUID = 1L;
	private String reportText;

}
