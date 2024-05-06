package com.employee.timetrack.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;


@Entity
@Table(name = "associate")
public class Associate {
	 @Id
	 @GeneratedValue(strategy = GenerationType.SEQUENCE)
	 @Column(name = "associate_id", unique = true, nullable = false)
	 private Long id;
	
	@NotNull(message="Username is required")
	@Pattern(regexp = "^[a-zA-Z\\s.]+$", message = "Please enter valid username (Numbers can not be used)")
	private String username;
	
	
	@NotNull(message = "Password is required")
	@Size(min = 8, message = "Password must be at least 8 characters long")
	@Pattern.List({
	    @Pattern(regexp = ".*[a-z].*", message = "Password must contain at least one lowercase letter"),
	    @Pattern(regexp = ".*[A-Z].*", message = "Password must contain at least one uppercase letter")
	})
	private String password;
	private String email;
	
	public Associate() {
		
	}

	public Associate(long id, String username, String password, String email) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.email = email;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	@Override
	public String toString() {
		return "Associate [id=" + id + ", username=" + username + ", password=" + password + ", email=" + email + "]";
	}
}
