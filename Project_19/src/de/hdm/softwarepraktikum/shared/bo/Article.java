package de.hdm.softwarepraktikum.shared.bo;

/*
 * Neben get und set Name/Unit wurden keine weiteren Methoden deklariert, da die Articleklasse von ihrer
 * Superklasse BusinessObject erbt, in der die nötigen Methoden bereits implementiert sind.
 */

public class Article extends BusinessObject {
	
	private String name;
	private static final long serialVersionUID = 1L;
}
