package app.entities;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

@Entity(name = "visitations")
public class Visitation {
	private int id;
	private LocalDate date;
	private String comment;
	private Set<Patient> patients;
	private History history;
	
	public Visitation() {
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	@Column(name = "date")
	public LocalDate getDate() {
		return date;
	}
	
	public void setDate(LocalDate date) {
		this.date = date;
	}
	
	@Column(name = "comment")
	public String getComment() {
		return comment;
	}
	
	public void setComment(String comment) {
		this.comment = comment;
	}
	
	@ManyToMany
	@JoinTable(name = "visitations_patients",
			joinColumns = @JoinColumn(name = "visitation_id", referencedColumnName = "id"),
			inverseJoinColumns = @JoinColumn(name = "patients_id", referencedColumnName = "id"))
	public Set<Patient> getPatients() {
		return patients;
	}
	
	public void setPatients(Set<Patient> patients) {
		this.patients = patients;
	}
	
	@ManyToOne
	@JoinColumn(name = "history_id", referencedColumnName = "id")
	public History getHistory() {
		return history;
	}
	
	public void setHistory(History history) {
		this.history = history;
	}
}
