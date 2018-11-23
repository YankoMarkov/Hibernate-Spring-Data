package app.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import java.util.Set;

@Entity(name = "students")
public class Students extends Person {
	
	private double averageGrade;
	private Set<Course> attendance;
	
	public Students() {
	}
	
	@Column(name = "average_grade")
	public double getAverageGrade() {
		return averageGrade;
	}
	
	public void setAverageGrade(double averageGrade) {
		this.averageGrade = averageGrade;
	}
	
	@ManyToMany(mappedBy = "students")
	public Set<Course> getAttendance() {
		return attendance;
	}
	
	public void setAttendance(Set<Course> attendance) {
		this.attendance = attendance;
	}
}
