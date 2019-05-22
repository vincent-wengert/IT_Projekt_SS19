package de.hdm.softwarepraktikum.shared.report;

import java.io.Serializable;
import java.util.Date;

public class Report implements Serializable {
	
	/** Original-Kommentar
	 * <p>
	 * Basisklasse aller Reports. Reports sind als <code>Serializable</code>
	 * deklariert, damit sie von dem Server an den Client gesendet werden können.
	 * Der Zugriff auf Reports erfolgt also nach deren Bereitstellung lokal auf dem
	 * Client.
	 * </p>
	 * <p>
	 * Ein Report besitzt eine Reihe von Standardelementen. Sie werden mittels
	 * Attributen modelliert und dort dokumentiert.
	 * </p>
	 * 
	 * @see Report
	 * @author Luca Randecker
	 */
	
	  private static final long serialVersionUID = 1L;

	  /** Original-Kommentar
	   * Kopfdaten des Berichts.
	   */
	  private Paragraph headerData = null;

	  /** Original-Kommentar
	   * Jeder Bericht kann einen individuellen Titel besitzen.
	   */
	  private String title = "Report";

	  /** Original-Kommentar
	   * Datum der Erstellung des Berichts.
	   */
	  private Date created = new Date();

	private Paragraph imprint;

	  /** Original-Kommentar
	   * Auslesen des Impressums.
	   * 
	   * @return Text des Impressums
	   */
	  public Paragraph getImprint() {
		return headerData;
	     
	  }

	  /** Original-Kommentar
	   * Setzen des Impressums.
	   * 
	   * @param imprint Text des Impressums
	   */
	  public void setImprint(Paragraph imprint) {
	    this.imprint = imprint;
	  }

	  /** Original-Kommentar
	   * Auslesen der Kopfdaten.
	   * 
	   * @return Text der Kopfdaten.
	   */
	  public Paragraph getHeaderData() {
	    return this.headerData;
	  }

	  /** Original-Kommentar
	   * Setzen der Kopfdaten.
	   * 
	   * @param headerData Text der Kopfdaten.
	   */
	  public void setHeaderData(Paragraph headerData) {
	    this.headerData = headerData;
	  }

	  /** Original-Kommentar
	   * Auslesen des Berichtstitels.
	   * 
	   * @return Titeltext
	   */
	  public String getTitle() {
	    return this.title;
	  }

	  /** Original-Kommentar
	   * Setzen des Berichtstitels.
	   * 
	   * @param title Titeltext
	   */
	  public void setTitle(String title) {
	    this.title = title;
	  }

	  /** Original-Kommentar
	   * Auslesen des Erstellungsdatums.
	   * 
	   * @return Datum der Erstellung des Berichts
	   */
	  public Date getCreated() {
	    return this.created;
	  }

	  /** Original-Kommentar
	   * Setzen des Erstellungsdatums. <b>Hinweis:</b> Der Aufruf dieser Methoden
	   * ist nicht unbedingt erforderlich, da jeder Report bei seiner Erstellung
	   * automatisch den aktuellen Zeitpunkt festhält.
	   * 
	   * @param created Zeitpunkt der Erstellung
	   */
	  public void setCreated(Date created) {
	    this.created = created;
	  }
	
	
	
	

}
