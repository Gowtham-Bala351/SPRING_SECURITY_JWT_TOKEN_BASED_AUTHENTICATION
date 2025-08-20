package com.saveJsonServlet.saveJsonServlet.Bean;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;

@Entity
public class LogsEn {
	//logs_en
	@Id
	private String txn_id;
	
	
	private String id;
	
	@Lob
    private byte[] request;
	
    private String response;

	public String getTxn_id() {
		return txn_id;
	}

	public void setTxn_id(String txn_id) {
		this.txn_id = txn_id;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public byte[] getRequest() {
		return request;
	}

	public void setRequest(byte[] request) {
		this.request = request;
	}

	public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
	}

	
	
    
    
//logs_en

}
