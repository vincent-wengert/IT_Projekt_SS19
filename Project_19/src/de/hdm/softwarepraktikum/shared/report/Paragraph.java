package de.hdm.softwarepraktikum.shared.report;

import java.io.Serializable;

/**
 * Reports müssen die Möglichkeit besitzen, Text strukturiert abzuspeichern.
 * Später kann dieser Text durch <code>ReportWriter</code> in unterschiedliche
 * Zielformate konvertiert wrrden. Die Verwendung der Klasse <code>String</code>
 * reicht in diesem Fall nicht aus, da allein das Hinzufügen eines Zeilenumbruchs zur
 * Markierung eines Absatzendes Kenntnisse über das Zielformat voraussetzt. Im
 * Falle einer rein textuellen Darstellung würde hierzu ein doppeltes "
 * <code>\n</code>" genügen. Bei dem Zielformat HTML müsste jedoch der gesamte
 * Absatz in entsprechendes Markup eingefügt werden.
 * <p>
 * 
 * <code>Paragraph</code> ist <code>Serializable</code>, so das Objekte dieser
 * Klasse durch das Netzwerk übertragbar sind.
 * 
 * @see Report
 * @author Luca Randecker
 */

public class Paragraph implements Serializable {
	
	/**
	   * 
	   */
	  private static final long serialVersionUID = 1L;
	
	

}
