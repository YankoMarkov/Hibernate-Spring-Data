package app.entities;

import javax.persistence.*;
import java.util.Set;

@Entity(name = "histories")
public class History {
	private int id;
	private Set<Visitation> visitations;
	private Set<Diagnose> diagnoses;
	private Set<Medicament> medicaments;
	
	public History() {
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	@OneToMany(mappedBy = "history")
	public Set<Visitation> getVisitations() {
		return visitations;
	}
	
	public void setVisitations(Set<Visitation> visitations) {
		this.visitations = visitations;
	}
	
	@OneToMany(mappedBy = "history")
	public Set<Diagnose> getDiagnoses() {
		return diagnoses;
	}
	
	public void setDiagnoses(Set<Diagnose> diagnoses) {
		this.diagnoses = diagnoses;
	}
	
	@OneToMany(mappedBy = "history")
	public Set<Medicament> getMedicaments() {
		return medicaments;
	}
	
	public void setMedicaments(Set<Medicament> medicaments) {
		this.medicaments = medicaments;
	}
}
