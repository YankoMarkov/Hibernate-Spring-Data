package app.entities;

import app.orm.annotations.Column;
import app.orm.annotations.Id;

import java.util.Date;

public final class User {
	
	@Id(name = "id")
	private int id;
	
	@Column(name = "username")
	private String username;
	
	@Column(name = "age")
	private int age;
	
//	@Column(name = "registration_date")
//	private Date registrationDate;
	
	public User() {
	}
	
	public User(int id, String username, int age) {
		setId(id);
		setUsername(username);
		setAge(age);
		//setRegistrationDate(registrationDate);
	}
	
//	public User(String username, int age, Date registrationDate) {
//		this(0, username, age, registrationDate);
//	}
	
	public User(String username, int age) {
		this(0, username, age);
	}
	
	private int getId() {
		return id;
	}
	
	private void setId(int id) {
		this.id = id;
	}
	
	private String getUsername() {
		return username;
	}
	
	private void setUsername(String username) {
		this.username = username;
	}
	
	private int getAge() {
		return age;
	}
	
	private void setAge(int age) {
		this.age = age;
	}
	
//	private Date getRegistrationDate() {
//		return registrationDate;
//	}
//
//	private void setRegistrationDate(Date registrationDate) {
//		this.registrationDate = registrationDate;
//	}
	
	@Override
	public String toString() {
		return String.format("%d | %s | %d",
				getId(), getUsername(), getAge());
	}
}
