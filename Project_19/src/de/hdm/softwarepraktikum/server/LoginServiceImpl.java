package de.hdm.softwarepraktikum.server;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import de.hdm.softwarepraktikum.server.db.PersonMapper;
import de.hdm.softwarepraktikum.shared.LoginService;
import de.hdm.softwarepraktikum.shared.bo.Person;

/**
 * Servlet, das den Login Ueber das Google User Service API verwaltet.
 *
 * 
 * @author Vincent Wengert
 * @version 1.0
 */

public class LoginServiceImpl extends RemoteServiceServlet implements LoginService{

    private static final long serialVersionUID = 1L;

    
    /**
     * Hier wird die login Methode implementiert.
     * Diese prueft ob ein <code>Person<code> dem System bekannt ist.
     * Ist dies der Fall, werden die <code>Person<code> Attribute fuer das Objekt gesetzt.
     * Ansonsten wird ein neuer Datensatz in die Datenbank geschrieben und der <code>Person<code> eingeloggt.
     * Ist der <code>Person<code> nicht in mit seinem Google Account eingeloggt, 
     * wird ein LoginLink fuer das GoogleUserServiceAPI erstellt.
     * 
     * @param requestUri die Domain der Startseite
     * @return neue oder eingeloggte Person
     */
    public Person login(String requestUri) {
    	UserService userService = UserServiceFactory.getUserService();
    	User googleUser = userService.getCurrentUser();
    	Person p = new Person();
    	/**
         * Wenn der <code>Person<code> mit seinem Google Account eingeloggt ist, wird Ueberprueft, 
         * ob dieser dem Shoppinglist System bekannt ist.
         * 
         */
		if (googleUser != null) {
			Person existingP =  PersonMapper.personMapper().findByGmail(googleUser.getEmail());
			
			/**
			 * Falls der <code>Person<code> dem System bekannt ist, wird dieser eingeloggt.
			 */
			if(existingP!=null) {
				
				existingP.setLoggedIn(true);
				existingP.setLogoutUrl(userService.createLogoutURL(requestUri));
	
				return existingP; 
			}
			
			 /**
			  * Falls der <code>Person<code> sich zum ersten Mal am System anmeldet, 
			  * wird ein neuer Datensatz in die Datenbank geschrieben.
			  */
			p.setLoggedIn(true);
			p.setLogoutUrl(userService.createLogoutURL(requestUri));
			p.setGmail(googleUser.getEmail());
			PersonMapper.personMapper().insert(p);
		
			return p;
			
			}	

		p.setLoginUrl(userService.createLoginURL(requestUri));
		
		return p;
		

    }
}
