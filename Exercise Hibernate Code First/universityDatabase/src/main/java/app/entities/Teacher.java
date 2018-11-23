package app.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.Set;

@Entity(name = "teachers")
public class Teacher extends Person {
	
	private String email;
	private double salaryPerHour;
	private Set<Course> courses;
	
	public Teacher() {
	}
	
	@Column(name = "email")
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	@Column(name = "salary_per_hour")
	public Double getSalaryPerHour() {
		return salaryPerHour;
	}
	
	public void setSalaryPerHour(Double salaryPerHour) {
		this.salaryPerHour = salaryPerHour;
	}
	
	@OneToMany(mappedBy = "teacher")
	public Set<Course> getCourses() {
		return courses;
	}
	
	public void setCourses(Set<Course> courses) {
		this.courses = courses;
	}
}
