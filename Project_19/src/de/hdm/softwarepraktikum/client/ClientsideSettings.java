package de.hdm.softwarepraktikum.client;

import java.util.logging.Logger;

import com.google.gwt.core.client.GWT;
import de.hdm.softwarepraktikum.shared.CommonSettings;
import de.hdm.softwarepraktikum.shared.Login;
import de.hdm.softwarepraktikum.shared.LoginAsync;
import de.hdm.softwarepraktikum.shared.ReportGenerator;
import de.hdm.softwarepraktikum.shared.ReportGeneratorAsync;
import de.hdm.softwarepraktikum.shared.ShoppingListAdministration;
import de.hdm.softwarepraktikum.shared.ShoppingListAdministrationAsync;

/**
 * Klasse die die Methoden zur Instanziierung <code>ShoppingListAdministrationAsync</code>,
 * <code>ReportGeneratorAsync</code> und <code>LoginServiceAsync</code> beinhalten.
 * 
 * @author VincentWengert
 * @version 1.0
 */

public class ClientsideSettings extends CommonSettings{

	    /**
	     * Remote Service Proxy zur Verbindungsaufnahme mit dem serverseitigen
	     * Dienst <code>ShoppingListAdministrationAsync</code>
	     */
	    private static	ShoppingListAdministrationAsync shoppinglistAdministration = null;

	    
	    /**
	     * Remote Service Proxy zur Verbindungsaufnahme mit dem serverseitigen
	     * Dienst <code>ReportGenerator</code>
	     */
	    private static ReportGeneratorAsync reportGenerator = null;

	    /**
	     * Remote Service Proxy zur Verbindungsaufnahme mit dem serverseitigen
	     * Dienst <code>LoginService</code>
	     */
	    private static LoginAsync loginService = null;

	    
	    /**
	     * Name des Client-seitigen Loggers.
	     */
	    private static final String LOGGER_NAME = "Shoppinglisttool Web Client";

	    
	    /**
	     * Instanz des Client-seitigen Loggers.
	     */
	    private static final Logger log = Logger.getLogger(LOGGER_NAME);

	   
	    /**
	     * Der zentrale Applikationslogger wird ausgelesen
	     * 
	     * @return die Logger-Instanz für die Server-Seite wird zurückgegeben
	     */
	    public static Logger getLogger() {
	       
	        return log;
	    }
	    
	    
	    /**
	     * Mit Hilfe dieser Methode wird eine LoginService-Instanz erstellt, vorausgesetzt 
	     * dass bisher keine besteht. Durch ein erneutes Aufrufen der Methode wird dann auch
	     * das bereits bestehende Objekt zurückgegeben.
	     * 
	     * @return eindeutige Instanz vom Typ <code>LoginServiceAsync</code>
	     */
	    public static LoginAsync getLoginService() {
	    	if(loginService == null){
	    		loginService = GWT.create(Login.class);
			}
			return loginService;
	    }

	    
	    /**
	     * Mit Hilfe dieser Methode wird eine ShoppingListAdministration-Instanz erstellt,
	     * vorausgesetzt dass bisher keine besteht. Durch ein erneutes Aufrufen der 
	     * Methode wird dann auch das bereits bestehende Objekt zurückgegeben. 
	     * 
	     * @return eindeutige Instanz vom Typ <code>ShoppingListAdministrationAsync</code>
	     */
	    public static ShoppingListAdministrationAsync getShoppinglistAdministration() {
	    	
	    	/*
	    	 * Sollte es bisher noch keine Instanz vom ShoppinglistAdministration bestehen, 
	    	 * wird eine instantiiert.
	    	 */
	    	if (shoppinglistAdministration == null){
	    		shoppinglistAdministration = GWT.create(ShoppingListAdministration.class);
			}
			
	    	//Der instantiierte ShoppinglistAdministration wird zurückgegeben
			return shoppinglistAdministration;
	        
	    }
	    
	    
	    /**
	     * Mit Hilfe dieser Methode wird eine ReportGenerator-Instanz erstellt, 
	     * vorausgesetzt dass bisher noch keine besteht. Durch ein erneuetes Aufrufen der
	     * Methode wird dann auch das bereits bestehende Objekt zurückgegeben. 
	     * 
	     * @return eindeutige Instanz vom Typ <code>ReportGeneratorAsync</code>
	     */
	    public static ReportGeneratorAsync getReportGenerator() {
    	
    	/*
	    	 * Sollte es bisher noch keine Instanz des ReportGenerators bestehen
	    	 * wird eine instantiiert. 
	    	 */
    	 if (reportGenerator == null) {
	    		 reportGenerator = GWT.create(ReportGenerator.class);
	    	 }
	    	 
	    	 // Der instantiierte ReportGenerator wird zurückgegeben
	        return reportGenerator;
    }
	    	 

}