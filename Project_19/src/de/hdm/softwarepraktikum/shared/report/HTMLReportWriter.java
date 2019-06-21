package de.hdm.softwarepraktikum.shared.report;

import java.util.ArrayList;

import com.google.gwt.safehtml.shared.SafeHtml;

public class HTMLReportWriter extends ReportWriter {
	
	private static final long serialVersionUID = 1L;

	
	/**
	 * Default KonstruKtor
	 */
	public HTMLReportWriter() {
		
	}
	
    /**
     * Diese Variable wird mit dem Ergebnis einer Umwandlung (vgl.
     * process...-Methoden) belegt. Format: HTML-Text
     */
    private String reportText = "";

    
    /**
     * Hier wird reportText zurückgesetzt.
     */
    public void resetReportText() {
        
    	this.reportText = "";  
    }

	
    /**
     * Hier wird ein HTML-Header-Text erstellt.
     * 
     * @return Der Header wird als HTML-Format zurückgegeben.
     */
    public String getHeader() {
       
    	StringBuffer result = new StringBuffer();
    	
    	result.append("<html><head><title></title></head><body>");
    	return result.toString();
    	
    }
    
    
    /**
     * Hier wird der HTML-Trailer-Text produziert.
     * 
     * @return Der Trailer wird als HTML-Format zurückgegeben.
     */
    public String getTrailer() {
        
    	return "</body></html>";
    }
    
    
    /**
     * Auslesen des Ergebnisses der zuletzt aufgerufenen process...-Methode.
     * 
     * @return Der Reporttext wird als String im HTML-Format
     */
	public String getReportText() {
		// TODO Auto-generated method stub
		 return this.getHeader() + this.reportText + this.getTrailer();
	}
    

	@Override
	public void process(ItemsByGroupReport r) {
		// TODO Auto-generated method stub

    	//Löschen des Ergebnisses der vorherigen Prozessierung
		this.resetReportText();
		
		/**
		 * Schrittweise werden unsere Ergebnisse in diesen Buffer
		 * geschrieben
		 */
		StringBuffer result = new StringBuffer();
		
		/**
		 * Hier werden Schritt für Schritt die einzelnen Bestandteile des
		 * Reports ausgelesen und in HTML-Form übersetzt
		 */
		result.append("<h2 style='text-align:center;'>" + r.getTitle() + "</h2>");
		result.append("<h3 style='text-align:center;'>" + "Erstelldatum des Reports: " + r.getCreationDateString()+ "</h3>");
		result.append("<table style=\"width:1400px;border:1px solid black;border-collapse:collapse;margin: auto;\n" + 
				"\"><tr></table\"");

		/**
		 * Hier werden alle Reihen des Reports durchlaufen und formatiert. Dabei wird die Headline Row gesondert formatiert, 
		 * Zudem wird eine SubTable über die Methode processSubTable() in html formatiert.
		 *
		 */
		ArrayList<Row> rows =r.getRows();
		
		for (int i = 0; i < rows.size(); i++) {
		      Row row = rows.get(i);
		      result.append("<tr>");
		      for (int k = 0; k < row.getColumnsSize(); k++) {
		        if (i == 0) {
		        	  result.append("<td style=\"border:1px solid black; height:30px;font-size:14pt;padding-left:3px;background:#7e848e;font-weight:bold\">" 
    			              + row.getColumnByIndex(k)+ "</td>");

		        }
		        else {
		          if (i >=1) {
							result.append("<td style=\"background:#F2F3F4;border-left:1px solid black;border-top:1px solid black;padding-left:3px;\">"
	    			                + row.getColumnByIndex(k) + "</td>");
		          			}
		          else {
		            result.append("<td valign=\"top\">" + row.getColumnByIndex(k) + "</td>");
		          }
		        }
		      }
		      result.append("</tr>");
		    }

		    result.append("</table>");
		
			/**
			 * Schlussendlich Umwandlung des StringBuffers in einen String und dessen Zuweisung
			 * der reportText-Varibale. Anschließend wird das Ergebnis mittels
			 * der Methode getReportText() ausgelesen.
			 */
		this.reportText = result.toString();
	}
	
	

	@Override
	public void process(ItemsByPersonReport i) {
		// TODO Auto-generated method stub
		
	}




}
