package com.employee.timetrack.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Admin {
	@Id
	@Column(name = "admin_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int aid;
	@Column(name = "admin_name")
	private String name;
	@Column(name = "pwd")
	private String password;
	private String email;
	
	public Admin() {
		
	}

	public Admin(int aid, String name, String password, String email) {
		super();
		this.aid = aid;
		this.name = name;
		this.password = password;
		this.email = email;
	}

	public int getAid() {
		return aid;
	}

	public void setAid(int aid) {
		this.aid = aid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "Admin [aid=" + aid + ", name=" + name + ", password=" + password + ", email=" + email + "]";
	}

	
	
}