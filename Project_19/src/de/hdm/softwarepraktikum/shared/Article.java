package de.hdm.softwarepraktikum.shared;

/*
 * Neben get und set Name/Unit wurden keine weiteren Methoden deklariert, da die Articleklasse von ihrer
 * Superklasse BusinessObject erbt, in der die nötigen Methoden bereits implementiert sind.
 */

public class Article extends BusinessObject {
	
	private String name;
	private enum Einheit{Stueck,Kilogramm,Gramm};
	private static final long serialVersionUID = 1L;
}
