package de.hdm.softwarepraktikum.shared;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import de.hdm.softwarepraktikum.shared.bo.Person;

/* Interface, welches den Login realisiert.
* 
* @author Vincent Wengert
* @version 1.0
* @see com.google.gwt.user.client.rpc.RemoteService
* @see LoginService
*/

@RemoteServiceRelativePath("login")
public interface LoginService extends RemoteService{
	
	 
	/**
     * @param requestUri 
     * @return
     */
	public Person login(String requestUri);
	
}
