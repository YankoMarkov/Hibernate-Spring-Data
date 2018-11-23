package app.entities;

import javax.persistence.*;
import java.util.Set;

@Entity(name = "diagnoses")
public class Diagnose {
	private int id;
	private String name;
	private String comment;
	private Set<Patient> patients;
	private History history;
	
	public Diagnose() {
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	@Column(name = "name")
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	@Column(name = "comment")
	public String getComment() {
		return comment;
	}
	
	public void setComment(String comment) {
		this.comment = comment;
	}
	
	@ManyToMany
	@JoinTable(name = "diagnoses_patients",
			joinColumns = @JoinColumn(name = "diagnose_id", referencedColumnName = "id"),
			inverseJoinColumns = @JoinColumn(name = "patient_id", referencedColumnName = "id"))
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
