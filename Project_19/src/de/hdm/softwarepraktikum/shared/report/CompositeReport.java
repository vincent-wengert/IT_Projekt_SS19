package de.hdm.softwarepraktikum.shared.report;

import java.util.ArrayList;
import java.io.Serializable;

public class CompositeReport extends Report implements Serializable {
	

	/**
   * 
   */
  private static final long serialVersionUID = 1L;

  /** Original-Kommentar
	 * Die Menge der Teil-Reports.
	 */
	private ArrayList<Report> subReports = new ArrayList<Report>();

	/** Original-Kommentar
	 * Hinzufügen eines Teil-Reports.
	 * @param r der hinzuzufügende Teil-Report.
	 */
	public void addSubReport(Report r) {
		this.subReports.add(r);
	}

	/** Original-Kommentar
	 * Entfernen eines Teil-Reports.
	 * @param r der zu entfernende Teil-Report.
	 */
	public void removeSubReport(Report r) {
		this.subReports.remove(r);
	}

	/** Original-Kommentar
	 * Auslesen der Anzahl von Teil-Reports.
	 * @return int Anzahl der Teil-Reports.
	 */
	public int getNumSubReports() {
		return this.subReports.size();
	}

	/**
	 * Auslesen eines einzelnen Teil-Reports.
	 * @param i Poistion des Teil-Reports wird übergeben.
	 * 
	 * @return Position des Teil-Reports wird zurückgegeben.
	 */	
	public Report getSubReportAt(int i) {
		return this.subReports.get(i);
	}

}
