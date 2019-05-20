package de.hdm.softwarepraktikum.shared.report;

import java.util.ArrayList;

/** Original-Kommentar
 * Diese Klasse ist eine spezielle Form der Klasse <code>Coloumn</code>, welche zusätzlich 
 * mehrere Rows besitzen kann. 
 * Diese Klasse ist notwendig zur Generierung eines SubTables.
 * 
 * @author Luca Randecker
 * @version 1.0
 * @see Column
 */

public class SubColumn extends Column {
	
	private static final long serialVersionUID = 1L;
	
	private ArrayList<Row> subRows = new ArrayList<Row>();
	 
	 /**
	  * default Constructor
	  */
	 public SubColumn(){
			
		}
	     
	 /** Hinzufügen einer Zeile.
	   * 
	   * @param r Die Row wird übergeben.
	   */
	public void addRow(Row r){
		this.subRows.add(r);
	}
	
      /** Auslesen sämtlicher Positionsdaten.
       * 
       * @return Die SubRows werden zurückgegeben.
       */
	public ArrayList<Row> getSubRows() {
		return subRows;
	}	
