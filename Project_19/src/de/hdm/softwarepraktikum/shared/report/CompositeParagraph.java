package de.hdm.softwarepraktikum.shared.report;

import java.io.Serializable;
import java.util.ArrayList;

/** Original-Kommentar
 * Diese Klasse stellt eine Menge einzelner Absätze (
 * <code>SimpleParagraph</code>-Objekte) dar. Diese werden als Unterabschnitte
 * in einem <code>Vector</code> abgelegt verwaltet.
 * 
 * @author Thies
 */
public class CompositeParagraph extends Paragraph implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  /** Original-Kommentar
   * Speicherort der Unterabschnitte.
   */
  private ArrayList<SimpleParagraph> subParagraphs = new ArrayList<SimpleParagraph>();

  /** Original-Kommentar
   * Einen Unterabschnitt hinzufügen.
   * 
   * @param p der hinzuzufügende Unterabschnitt.
   */
  public void addSubParagraph(SimpleParagraph p) {
    this.subParagraphs.add(p);
  }

  /** Original-Kommentar
   * Einen Unterabschnitt entfernen.
   * 
   * @param p der zu entfernende Unterabschnitt.
   */
  public void removeSubParagraph(SimpleParagraph p) {
    this.subParagraphs.remove(p);
  }

  /**
   * Auslesen sämtlicher Unterabschnitte.
   * 
   * @return <code>ArrayList</code>, der sämtliche Unterabschnitte enthält.
   */
  public ArrayList<SimpleParagraph> getSubParagraphs() {
    return this.subParagraphs;
  }

  /** Original-Kommentar
   * Auslesen der Anzahl der Unterabschnitte.
   * 
   * @return Anzahl der Unterabschnitte
   */
  public int getNumParagraphs() {
    return this.subParagraphs.size();
  }

  /** Original-Kommentar
   * Auslesen eines einzelnen Unterabschnitts.
   * 
   * @param i der Index des gewünschten Unterabschnitts (0 <= i <n), mit n =
   *          Anzahl der Unterabschnitte.
   * 
   * @return der gewünschte Unterabschnitt.
   */
  public SimpleParagraph getParagraphAt(int i) {
    return this.subParagraphs.get(i);
  }

  /** Original-Kommentar
   * Umwandeln eines <code>CompositeParagraph</code> in einen
   * <code>String</code>.
   */
  public String toString() {
    
	 /*
     * Ein leerer Buffer wird angelegt, in den wir sukzessive sämtliche
     * String-Repräsentationen der Unterabschnitte eintragen.
     */
    StringBuffer result = new StringBuffer();

    // Hier geht eine for-Schleife alle Unterabschnitte durch
    for (int i = 0; i < this.subParagraphs.size(); i++) {
    	SimpleParagraph p = this.subParagraphs.get(i);

      /*
       * Nachdem der jeweilige Unterabschnitt in einen Stringgewandelt wurde,
       * wird er an den StringBuffer gehängt.
       */
      result.append(p.toString() + "\n");
    }

    /*
     * Nun wird der Buffer in String umgewandelt und zurückgegeben.
     */
    return result.toString();
  }
}
