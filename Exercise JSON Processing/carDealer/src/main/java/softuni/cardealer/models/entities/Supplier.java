package softuni.cardealer.models.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "suppliers")
public class Supplier extends BaseEntity {
	private String name;
	private boolean isImporter;
	private Set<Part> parts;
	
	public Supplier() {
		this.parts = new HashSet<>();
	}
	
	@Column(name = "name", nullable = false)
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	@Column(name = "is_importer", nullable = false)
	public boolean isImporter() {
		return isImporter;
	}
	
	public void setImporter(boolean importer) {
		isImporter = importer;
	}
	
	@OneToMany(mappedBy = "supplier", fetch = FetchType.EAGER)
	public Set<Part> getParts() {
		return parts;
	}
	
	public void setParts(Set<Part> parts) {
		this.parts = parts;
	}
}
