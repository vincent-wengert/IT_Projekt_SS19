package de.hdm.softwarepraktikum.shared.bo;

import java.util.Date;
import com.google.gwt.user.client.rpc.IsSerializable;

import de.hdm.thies.bankProjekt.shared.bo.BusinessObject;

public abstract class BusinessObject implements IsSerializable{

	private static final long serialVersionUID = 1L;
	private int bO_ID = 0;
	private Date creationdate;
	
	
	
	public int getBO_ID() {
		return bO_ID;
	}
	public void setBO_ID(int bO_ID) {
		this.bO_ID = bO_ID;
	}
	
	
	public Date getCreationdate() {
		return creationdate;
	}
	public void setCreationdate(Date creationdate) {
		this.creationdate = creationdate;
	}
	
	// Rückgabe Name + ID als String
	public String toString() {
		return this.getClass().getName() + "#" + this.bO_ID;
	}
	

	
	//Prüfen ob gleiches Objekt anhand der ID
	public boolean equals(Object obj) {
		if (obj != null && obj instanceof BusinessObject) {
		      BusinessObject bo = (BusinessObject) obj;
		      try {
		        if (bo.getBO_ID() == this.bO_ID)
		          return true;
		      }
		      catch (IllegalArgumentException e) {
		       
		        return false;
		      }
		}
	}
	
}
