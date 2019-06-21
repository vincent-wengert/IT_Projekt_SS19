package de.hdm.softwarepraktikum.shared.report;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.IsSerializable;

public class Row implements IsSerializable {
	
	/**
	   * 
	   */
	  private static final long serialVersionUID = 1L;
	  
		/**
		 * Default Konstruktor.
		 */
		public Row() {

		}
	  
	  
	  /**
	   * Dies ist der Speicherplatz für die Spalten der Zeile.
	   */
	  private ArrayList<Column> columns = new ArrayList<Column>();

	  /**
	   * An dieser Stelle werden Spalten hinzugefügt.
	   * 
	   * @param c das Spaltenobjekt
	   */
	  public void addColumn(Column c) {
	    this.columns.add(c);
	  }

	  /**
	   * Hier wrden Spalten entfern.
	   * 
	   * @param c das zu entfernende Spaltenobjekt
	   */
	  public void removeColumn(Column c) {
	    this.columns.remove(c);
	  }

	  /**
	   * Spalten wrrden an dieser Stelle ausgelesen.
	   * 
	   * @return <code>Vector</code>-Objekts mit sämtlichen Spalten
	   */
	  public ArrayList<Column> getColumns() {
	    return this.columns;
	  }

	  /**
	   * Die Anzahl der Spalten wird hier ausgelesen.
	   * 
	   * @return int Die Anzahl der Spalten wird zurückgegeben.
	   */
	  public int getColumnsSize() {
	    return this.columns.size();
	  }

	  /**
	   * Hier wird ein einzelnes Spalten-Objekt ausgelesen.
	   * 
	   * @param i der Index der auszulesenden Spalte (0 <= i < n), mit n = Anzahl
	   *          der Spalten.
	   * @return das gewünschte Spaltenobjekt.
	   */
	  public Column getColumnByIndex(int i) {
	    return this.columns.get(i);
	  }

}
