package de.hdm.softwarepraktikum.shared.report;

import java.io.Serializable;

/** Original-Kommentar
 * Spalte eines <code>Row</code>-Objekts. <code>Column</code>-Objekte
 * implementieren das <code>Serializable</code>-Interface und können daher als
 * Kopie z.B. vom Server an den Client übertragen werden.
 * 
 * @see Row
 * @author Luca Randecker
 */
public class Column implements Serializable {

  private static final long serialVersionUID = 1L;

  /** Original-Kommentar
   * Der Wert eines Spaltenobjekts entspricht dem Zelleneintrag einer Tabelle.
   * In dieser Realisierung handelt es sich um einen einfachen textuellen Wert.
   */
  private String value = "";

  /** Original-Kommentar
   * <p>
   * Serialisierbare Klassen, die mittels GWT-RPC transportiert werden sollen,
   * müssen einen No-Argument-Konstruktor besitzen. Ist kein Konstruktor
   * explizit angegeben, so existiert in Java-Klassen der Default-Konstruktor,
   * der dem No-Argument-Konstruktor entspricht.
   * </p>
   * <p>
   * Besitzt eine Klasse mind. einen explizit implementierten Konstruktor, so
   * gelten nur diese explizit implementierten Konstruktoren. Der
   * Default-Konstruktor gilt dann nicht. Wenn wir in einer solchen Situation
   * aber dennoch einen No-Argument-Konstruktor benötigen, müssen wir diesen wie
   * in diesem Beispiel explizit implementieren.
   * </p>
   * 
   * @see #Column(String)
   * @see SimpleParagraph#SimpleParagraph()
   */
  public Column() {
  }

  /** Original-Kommentar
   * Konstruktor, der die Angabe eines Werts (Spalteneintrag) erzwingt.
   * 
   * @param s der Wert, der durch das Column-Objekt dargestellt werden soll.
   * @see #Column()
   */
  public Column(String s) {
    this.value = s;
  }

  /** Original-Kommentar
   * Auslesen des Spaltenwerts.
   * 
   * @return der Eintrag als String
   */
  public String getValue() {
    return value;
  }

  /** Original-Kommentar
   * Überschreiben des aktuellen Spaltenwerts.
   * 
   * @param value neuer Spaltenwert
   */
  public void setValue(String value) {
    this.value = value;
  }

  /** Original-Kommentar
   * Umwandeln des <code>Column</code>-Objekts in einen String.
   * 
   * @see java.lang.Object
   */
  public String toString() {
    return this.value;
  }
}
