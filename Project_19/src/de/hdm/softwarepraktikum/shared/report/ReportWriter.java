package de.hdm.softwarepraktikum.shared.report;

/** Original-Kommentar
 * <p>
 * Diese Klasse wird benötigt, um auf dem Client die ihm vom Server zur
 * Verfügung gestellten <code>Report</code>-Objekte in ein menschenlesbares
 * Format zu überführen.
 * </p>
 * <p>
 * Das Zielformat kann prinzipiell beliebig sein. Methoden zum Auslesen der in
 * das Zielformat überführten Information wird den Subklassen überlassen. In
 * dieser Klasse werden die Signaturen der Methoden deklariert, die für die
 * Prozessierung der Quellinformation zuständig sind.
 * </p>
 * 
 * @author Luca Randecker
 */

public  abstract class ReportWriter {
	
	/**
	   * Übersetzen eines <code>ItemsByGroupReport</code> in das
	   * Zielformat.
	   * 
	   * @param i Report dr zu übersetzen ist
	   */
	  public abstract void process(ItemsByGroupReport i);

	  /**
	   * Übersetzen eines <code>ItemsByPersonReport</code> in das
	   * Zielformat.
	   * 
	   * @param i Report der zu übersetzen ist
	   */
	  public abstract void process(ItemsByPersonReport i);

}
