package de.hdm.softwarepraktikum.shared.report;

import java.util.ArrayList;

public abstract class SimpleReport extends Report {

	/**Original-Kommentar
	 * <p>
	 * Ein einfacher Report, der neben den Informationen der Superklasse <code>
	 * Report</code> eine Tabelle mit "Positionsdaten" aufweist. Die Tabelle greift
	 * auf zwei Hilfsklassen namens <code>Row</code> und <code>Column</code> zurück.
	 * </p>
	 * <p>
	 * Die Positionsdaten sind vergleichbar mit der Liste der Bestellpositionen
	 * eines Bestellscheins. Dort werden in eine Tabelle zeilenweise Eintragung z.B.
	 * bzgl. Artikelnummer, Artikelbezeichnung, Menge, Preis vorgenommen.
	 * </p>
	 * @see Row
	 * @see Column
	 * @author Luca Randecker
	 * @version 1.0
	 */


		private static final long serialVersionUID = 1L;

		
	    /**
	     * Default Konstruktor
	     */
	    public SimpleReport() {
	    }
		

	    /**Original-Kommentar
	     * Tabelle mit Positionsdaten. Die Tabelle wird zeilenweise in diesem
	     * <code>Vector</code> abgelegt.
	     */
	    private ArrayList<Row> table = new ArrayList<Row>();


	    /**Original-Kommentar
	     * Hinzufügen einer Zeile.
	     * 
	     * @param r Die Row wird übergeben.
	     */
	    public void addRow(Row r) {
	       
	    	this.table.add(r);
	        
	    }

	    
	    /**Original-Kommentar
	     * Entfernen einer Zeile.
	     * 
	     * @param r Die Zeile wird übergeben.
	     */
	    public void removeRow(Row r) {
	       
	    	this.table.remove(r);
	        
	    }

	    /**Original-Kommentar
	     * Auslesen sämtlicher Positionsdaten.
	     * 
	     * @return Die Rows werden zurückgegeben.
	     */
	    public ArrayList<Row> getRows() {
	        return this.table;
	    }
	}
