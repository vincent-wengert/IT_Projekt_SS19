package de.hdm.softwarepraktikum.server;

import java.util.logging.Logger;

import de.hdm.softwarepraktikum.shared.CommonSettings;

/**
 * Klasse mit Eigenschaften und Diensten, die für alle Server-seitigen Klassen von
 * Bedeutung sind.
 * Durch diese Klasse wird eine Logging-Funktion unter Java unterstützt,
 * indem ein zentraler Applikationslogger realisiert wird. Diese Funktion kann 
 * mit Hilfe von <code>ServerSideSettings.getLogger()</code> aufgerufen werden.
 *
 */
public class ServersideSettings extends CommonSettings{
	
	private static final String LOGGER_NAME = "Shoppinglisttool Server";
    private static final Logger log = Logger.getLogger(LOGGER_NAME);

    
    /**
     * Auslesen des applikationsweiten zentralen Loggers.
     * @return Server-Seitige Logger-Instanz 
     */
    public static Logger getLogger() {
        return log;
    }

}
