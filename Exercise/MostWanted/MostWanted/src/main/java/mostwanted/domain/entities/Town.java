package mostwanted.domain.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.Set;

@Entity(name = "towns")
public class Town extends BaseEntity {
	private String name;
	private Set<Racer> racers;
	private Set<District> districts;
	
	@Column(name = "name", nullable = false, unique = true)
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	@OneToMany(mappedBy = "homeTown")
	public Set<Racer> getRacers() {
		return racers;
	}
	
	public void setRacers(Set<Racer> racers) {
		this.racers = racers;
	}
	
	@OneToMany(mappedBy = "town")
	public Set<District> getDistricts() {
		return districts;
	}
	
	public void setDistricts(Set<District> districts) {
		this.districts = districts;
	}
}
