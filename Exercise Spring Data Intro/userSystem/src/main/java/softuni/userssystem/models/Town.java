package softuni.userssystem.models;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Town extends BaseEntity {
	private String name;
	private String country;
	private Set<User> usersBornTown;
	private Set<User> usersLivingTown;
	
	public Town() {
	}
	
	@Column(name = "name", nullable = false)
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	@Column(name = "country", nullable = false)
	public String getCountry() {
		return country;
	}
	
	public void setCountry(String country) {
		this.country = country;
	}
	
	@OneToMany(mappedBy = "bornTown", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	public Set<User> getUsersBornTown() {
		return usersBornTown;
	}
	
	public void setUsersBornTown(Set<User> usersBornTown) {
		this.usersBornTown = usersBornTown;
	}
	
	@OneToMany(mappedBy = "currentlyLivingTown", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	public Set<User> getUsersLivingTown() {
		return usersLivingTown;
	}
	
	public void setUsersLivingTown(Set<User> usersLivingTown) {
		this.usersLivingTown = usersLivingTown;
	}
}
