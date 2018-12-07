package mostwanted.domain.entities;

import javax.persistence.*;
import java.util.Set;

@Entity(name = "races")
public class Race extends BaseEntity {
	private int laps;
	private District district;
	private Set<RaceEntry> entries;
	
	@Column(name = "laps", nullable = false)
	public int getLaps() {
		return laps;
	}
	
	public void setLaps(int laps) {
		this.laps = laps;
	}
	
	@ManyToOne
	@JoinColumn(name = "district_id", referencedColumnName = "id", nullable = false)
	public District getDistrict() {
		return district;
	}
	
	public void setDistrict(District district) {
		this.district = district;
	}
	
	@OneToMany(mappedBy = "race")
	public Set<RaceEntry> getEntries() {
		return entries;
	}
	
	public void setEntries(Set<RaceEntry> entries) {
		this.entries = entries;
	}
}
