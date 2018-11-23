package softuni.gamestore.models.entities;

import softuni.gamestore.models.enums.Role;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "users")
public class User extends BaseEntity {
	private String email;
	private String password;
	private String confirmPassword;
	private String fullName;
	private Set<Game> games;
	private Set<Order> orders;
	private Set<Game> ownedGames;
	private Role role;
	
	public User() {
		this.games = new HashSet<>();
		this.orders = new HashSet<>();
	}
	
	@Column(name = "email", unique = true, nullable = false)
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	@Column(name = "password", nullable = false)
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	@Transient
	public String getConfirmPassword() {
		return confirmPassword;
	}
	
	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}
	
	@Column(name = "full_name", nullable = false)
	public String getFullName() {
		return fullName;
	}
	
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	
	@ManyToMany
	@JoinTable(name = "users_games",
			joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
			inverseJoinColumns = @JoinColumn(name = "game_id", referencedColumnName = "id"))
	public Set<Game> getGames() {
		return games;
	}
	
	public void setGames(Set<Game> games) {
		this.games = games;
	}
	
	@OneToMany(mappedBy = "buyer", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	public Set<Order> getOrders() {
		return orders;
	}
	
	public void setOrders(Set<Order> orders) {
		this.orders = orders;
	}
	
	@OneToMany
	@JoinColumn(name = "game_id", referencedColumnName = "id")
	public Set<Game> getOwnedGames() {
		return ownedGames;
	}
	
	public void setOwnedGames(Set<Game> ownedGames) {
		this.ownedGames = ownedGames;
	}
	
	@Enumerated(value = EnumType.STRING)
	@Column(name = "role")
	public Role getRole() {
		return role;
	}
	
	public void setRole(Role role) {
		this.role = role;
	}
}
