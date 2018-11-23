package app.entities;

import javax.persistence.*;
import java.util.Set;

@Entity(name = "medicaments")
public class Medicament {
	private int id;
	private String name;
	private Set<Patient> patients;
	private History history;
	
	public Medicament() {
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
	
	@ManyToMany
	@JoinTable(name = "medicaments_patients",
			joinColumns = @JoinColumn(name = "medicament_id", referencedColumnName = "id"),
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
