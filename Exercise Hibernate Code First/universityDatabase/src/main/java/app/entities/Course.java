package app.entities;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

@Entity(name = "courses")
public class Course {
	private int id;
	private String name;
	private String description;
	private LocalDate startDate;
	private LocalDate endDate;
	private double credits;
	private Teacher teacher;
	private Set<Students> students;
	
	public Course() {
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	@Column(name = "course_name")
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	@Column(name = "description")
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	@Column(name = "start_date")
	public LocalDate getStartDate() {
		return startDate;
	}
	
	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}
	
	@Column(name = "end_date")
	public LocalDate getEndDate() {
		return endDate;
	}
	
	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}
	
	@Column(name = "credits")
	public double getCredits() {
		return credits;
	}
	
	public void setCredits(double credits) {
		this.credits = credits;
	}
	
	@ManyToOne
	@JoinColumn(name = "teacher_id", referencedColumnName = "id")
	public Teacher getTeacher() {
		return teacher;
	}
	
	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}
	
	@ManyToMany
	@JoinTable(name = "coures_students",
	joinColumns = @JoinColumn(name = "course_id", referencedColumnName = "id"),
	inverseJoinColumns = @JoinColumn(name = "student_id", referencedColumnName = "id"))
	public Set<Students> getStudents() {
		return students;
	}
	
	public void setStudents(Set<Students> students) {
		this.students = students;
	}
}
