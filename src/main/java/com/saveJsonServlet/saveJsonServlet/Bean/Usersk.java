package com.saveJsonServlet.saveJsonServlet.Bean;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "usersk")  // Optional: Ensures table name is explicitly set
public class Usersk {

    @Id
    private int id;

    private String username;
    private String password;
    private String status;
    private Integer countOfLogin;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Integer getCountOfLogin() {
		return countOfLogin;
	}
	public void setCountOfLogin(Integer countOfLogin) {
		this.countOfLogin = countOfLogin;
	}

   
}
