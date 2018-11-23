package app.entities;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

@Entity(name = "patients")
public class Patient {
	private int id;
	private String firstName;
	private String lastName;
	private String address;
	private String email;
	private LocalDate dateOfBirth;
	private String picture;
	private boolean hasMedicalInsurance;
	private Set<Visitation> visitations;
	private Set<Diagnose> diagnoses;
	private Set<Medicament> medicaments;
	
	public Patient() {
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	@Column(name = "first_name")
	public String getFirstName() {
		return firstName;
	}
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	@Column(name = "last_name")
	public String getLastName() {
		return lastName;
	}
	
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	@Column(name = "address")
	public String getAddress() {
		return address;
	}
	
	public void setAddress(String address) {
		this.address = address;
	}
	
	@Column(name = "email")
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	@Column(name = "date_of_birth")
	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}
	
	public void setDateOfBirth(LocalDate dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	
	@Column(name = "picture")
	public String getPicture() {
		return picture;
	}
	
	public void setPicture(String picture) {
		this.picture = picture;
	}
	
	@Column(name = "has_medical_insurance")
	public boolean isHasMedicalInsurance() {
		return hasMedicalInsurance;
	}
	
	public void setHasMedicalInsurance(boolean hasMedicalInsurance) {
		this.hasMedicalInsurance = hasMedicalInsurance;
	}
	
	@ManyToMany(mappedBy = "patients")
	public Set<Visitation> getVisitations() {
		return visitations;
	}
	
	public void setVisitations(Set<Visitation> visitations) {
		this.visitations = visitations;
	}
	
	@ManyToMany(mappedBy = "patients")
	public Set<Diagnose> getDiagnoses() {
		return diagnoses;
	}
	
	public void setDiagnoses(Set<Diagnose> diagnoses) {
		this.diagnoses = diagnoses;
	}
	
	@ManyToMany(mappedBy = "patients")
	public Set<Medicament> getMedicaments() {
		return medicaments;
	}
	
	public void setMedicaments(Set<Medicament> medicaments) {
		this.medicaments = medicaments;
	}
}
