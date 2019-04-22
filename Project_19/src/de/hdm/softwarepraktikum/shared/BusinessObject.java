package de.hdm.softwarepraktikum.shared;

import java.util.Date;
import com.google.gwt.user.client.rpc.IsSerializable;

public abstract class BusinessObject implements IsSerializable{

	private static final long serialVersionUID = 1L;
	private int BO_ID = 0;
	private Date creationdate;
	
	
	
	public int getBO_ID() {
		return BO_ID;
	}
	public void setBO_ID(int bO_ID) {
		BO_ID = bO_ID;
	}
	
	
	public Date getCreationdate() {
		return creationdate;
	}
	public void setCreationdate(Date creationdate) {
		this.creationdate = creationdate;
	}
	

	
}
