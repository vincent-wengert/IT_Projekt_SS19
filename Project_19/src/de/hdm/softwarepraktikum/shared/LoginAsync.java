package de.hdm.softwarepraktikum.shared;

import com.google.gwt.user.client.rpc.AsyncCallback;
import de.hdm.softwarepraktikum.shared.bo.Person;

public interface LoginAsync {

	void login(String requestUri, AsyncCallback<Person> asyncCallback);
}
