package softuni.productshop.models.entities;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "users")
public class User extends BaseEntity {
	private String firstName;
	private String lastName;
	private Integer age;
	private Set<User> friends;
	private Set<Product> sold;
	private Set<Product> bought;
	
	public User() {
		this.friends = new HashSet<>();
		this.sold = new HashSet<>();
		this.bought = new HashSet<>();
	}
	
	@Column(name = "first_name")
	public String getFirstName() {
		return firstName;
	}
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	@Column(name = "last_name", nullable = false)
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
	
	@ManyToMany
	@JoinTable(name = "users_friends",
			joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
			inverseJoinColumns = @JoinColumn(name = "friend_id", referencedColumnName = "id"))
	public Set<User> getFriends() {
		return friends;
	}
	
	public void setFriends(Set<User> friends) {
		this.friends = friends;
	}
	
	@OneToMany(mappedBy = "seller", fetch = FetchType.EAGER)
	public Set<Product> getSold() {
		return sold;
	}
	
	public void setSold(Set<Product> sold) {
		this.sold = sold;
	}
	
	@OneToMany(mappedBy = "buyer")
	public Set<Product> getBought() {
		return bought;
	}
	
	public void setBought(Set<Product> bought) {
		this.bought = bought;
	}
}
