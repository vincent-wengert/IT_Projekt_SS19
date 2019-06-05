package de.hdm.softwarepraktikum.shared;

import com.google.gwt.user.client.rpc.AsyncCallback;
import de.hdm.softwarepraktikum.shared.bo.Person;

/**
 * Asynchrones Interface für den Login
 * 
 * @author Vincent Wengert
 * @version 1.0
 * @see LoginService
 */
public interface LoginAsync {

	void login(String requestUri, AsyncCallback<Person> asyncCallback);

}