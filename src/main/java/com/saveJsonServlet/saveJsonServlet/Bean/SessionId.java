package com.saveJsonServlet.saveJsonServlet.Bean;
//
//import jakarta.persistence.Entity;
//import jakarta.persistence.Id;
//
//@Entity
//public class TransactioId {
// 
//	@Id
//	private String trasactionID;
//
//    public TransactioId() {}
//
//    public TransactioId(String transactionId) {
//        this.trasactionID = transactionId;
//    }
//
//	public String getTrasactionID() {
//		return trasactionID;
//	}
//
//	public void setTrasactionID(String trasactionID) {
//		this.trasactionID = trasactionID;
//	}
//	
//	
//	
//	
//}
//transactio_id

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import java.time.LocalDateTime;

@Entity
public class SessionId {

    @Id
    private String transactionID;

    private String username;

    private boolean active;

    private LocalDateTime createdAt;

    public SessionId() {}

    public SessionId(String transactionID, String username, boolean active, LocalDateTime createdAt) {
        this.transactionID = transactionID;
        this.username = username;
        this.active = active;
        this.createdAt = createdAt;
    }

	public String getTransactionID() {
		return transactionID;
	}

	public void setTransactionID(String transactionID) {
		this.transactionID = transactionID;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

    // Getters and setters...
    
}
//session_id