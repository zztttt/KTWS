package com.ktws.Entity;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@Entity(name="user")
public class User {
	@Id  
    @GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	private String password;
	private String role;
	private String username;
	
	public void setId(int id) {
    	this.id = id;
    }
    public int getId() {
    	return id;
    }
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
}
