package softuni.userssystem.models;

import net.bytebuddy.implementation.bind.annotation.Default;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Entity(name = "users")
public class User extends BaseEntity {
	private String firstName;
	private String lastName;
	private Integer age;
	private String username;
	private String password;
	private String email;
	private Picture profilePicture;
	private LocalDate registeredOn;
	private LocalDate lastTimeLoggedIn;
	private Town bornTown;
	private Town currentlyLivingTown;
	private Boolean isDeleted;
	private Set<User> friends;
	private Set<Album> albums;
	private String fullName;
	
	public User() {
		this.friends = new HashSet<>();
		this.albums = new HashSet<>();
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
	
	@Column(name = "age")
	public Integer getAge() {
		return age;
	}
	
	public void setAge(Integer age) {
		this.age = age;
	}
	
	@Column(name = "username", nullable = false)
	public String getUsername() {
		return username;
	}
	
	@Size(min = 4, max = 30)
	public void setUsername(String username) {
//		if (username.length() < 4 || username.length() > 30) {
//			throw new IllegalArgumentException("username should be between 4 and 30 symbols");
//		}
		this.username = username;
	}
	
	@Column(name = "password", nullable = false)
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		Pattern patternLowercase = Pattern.compile("[a-z]");
		Pattern patternUppercase = Pattern.compile("[A-Z]");
		Pattern patternDigit = Pattern.compile("[0-9]");
		Pattern patternSymbol = Pattern.compile("[!@#$%^&*()_+<>?]");
		Matcher matcherLowercase = patternLowercase.matcher(password);
		Matcher matcherUppercase = patternUppercase.matcher(password);
		Matcher matcherDigit = patternDigit.matcher(password);
		Matcher matcherSymbol = patternSymbol.matcher(password);
		
		if (password.length() < 1 || password.length() > 50) {
			throw new IllegalArgumentException("password should be between 6 and 50 symbols");
		}
		if (!matcherLowercase.find()) {
			throw new IllegalArgumentException("password should contain lowercase letter");
		}
		if (!matcherUppercase.find()) {
			throw new IllegalArgumentException("password should contain uppercase letter");
		}
		if (!matcherDigit.find()) {
			throw new IllegalArgumentException("password should contain number");
		}
		if (!matcherSymbol.find()) {
			throw new IllegalArgumentException("password should contain one of (!, @, #, $, %, ^, &, *, (, ), _, +, <, >, ?) symbols");
		}
		this.password = password;
	}
	
	@Column(name = "email", nullable = false)
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		Pattern pattern = Pattern.compile("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}");
		Matcher matcher = pattern.matcher(email);
		
		if (!matcher.find()) {
			throw new IllegalArgumentException("wrong email");
		}
		this.email = email;
	}
	
	@ManyToOne
	@JoinColumn(name = "profile_picture_id", referencedColumnName = "id")
	public Picture getProfilePicture() {
		return profilePicture;
	}
	
	public void setProfilePicture(Picture profilePicture) {
		this.profilePicture = profilePicture;
	}
	
	@Column(name = "registered_on")
	public LocalDate getRegisteredOn() {
		return registeredOn;
	}
	
	public void setRegisteredOn(LocalDate registeredOn) {
		this.registeredOn = registeredOn;
	}
	
	@Column(name = "last_time_logged_in")
	public LocalDate getLastTimeLoggedIn() {
		return lastTimeLoggedIn;
	}
	
	public void setLastTimeLoggedIn(LocalDate lastTimeLoggedIn) {
		this.lastTimeLoggedIn = lastTimeLoggedIn;
	}
	
	@ManyToOne
	@JoinColumn(name = "born_town_id", referencedColumnName = "id")
	public Town getBornTown() {
		return bornTown;
	}
	
	public void setBornTown(Town bornTown) {
		this.bornTown = bornTown;
	}
	
	@ManyToOne
	@JoinColumn(name = "currently_living_town_id", referencedColumnName = "id")
	public Town getCurrentlyLivingTown() {
		return currentlyLivingTown;
	}
	
	public void setCurrentlyLivingTown(Town currentlyLivingTown) {
		this.currentlyLivingTown = currentlyLivingTown;
	}
	
	@Column(name = "is_deleted", nullable = false)
	public Boolean getDeleted() {
		return isDeleted;
	}
	
	public void setDeleted(Boolean deleted) {
		isDeleted = deleted;
	}
	
	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinTable(name = "users_users",
			joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
			inverseJoinColumns = @JoinColumn(name = "friend_id", referencedColumnName = "id"))
	public Set<User> getFriends() {
		return friends;
	}
	
	public void setFriends(Set<User> friends) {
		this.friends = friends;
	}
	
	@OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	public Set<Album> getAlbums() {
		return albums;
	}
	
	public void setAlbums(Set<Album> albums) {
		this.albums = albums;
	}
	
	@Transient
	public String getFullName() {
		return fullName;
	}
	
	private void setFullName() {
		this.fullName = String.format("%s %s", this.getFirstName(), this.getLastName());
	}
}
