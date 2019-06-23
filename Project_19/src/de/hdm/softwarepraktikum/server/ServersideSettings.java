package de.hdm.softwarepraktikum.server;

import java.util.logging.Logger;

import de.hdm.softwarepraktikum.shared.CommonSettings;

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
