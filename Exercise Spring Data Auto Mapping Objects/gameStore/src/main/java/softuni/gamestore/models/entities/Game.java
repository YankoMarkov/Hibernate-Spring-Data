package softuni.gamestore.models.entities;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "games")
public class Game extends BaseEntity {
	private String title;
	private BigDecimal price;
	private Double size;
	private String trailer;
	private String image;
	private String description;
	private LocalDate releaseDate;
	private Set<User> users;
	private Set<Order> orders;
	
	public Game() {
		this.users = new HashSet<>();
		this.orders = new HashSet<>();
	}
	
	@Column(name = "title", nullable = false, unique = true)
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	@Column(name = "price", nullable = false)
	public BigDecimal getPrice() {
		return price;
	}
	
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	
	@Column(name = "size", nullable = false)
	public Double getSize() {
		return size;
	}
	
	public void setSize(Double size) {
		this.size = size;
	}
	
	@Column(name = "trailer", nullable = false)
	public String getTrailer() {
		return trailer;
	}
	
	public void setTrailer(String trailer) {
		this.trailer = trailer;
	}
	
	@Column(name = "image")
	public String getImage() {
		return image;
	}
	
	public void setImage(String image) {
		this.image = image;
	}
	
	@Column(name = "description", columnDefinition = "text", nullable = false)
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	@Column(name = "release_date", nullable = false)
	public LocalDate getReleaseDate() {
		return releaseDate;
	}
	
	public void setReleaseDate(LocalDate releaseDate) {
		this.releaseDate = releaseDate;
	}
	
	@ManyToMany(mappedBy = "games", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	public Set<User> getUsers() {
		return users;
	}
	
	public void setUsers(Set<User> users) {
		this.users = users;
	}
	
	@ManyToMany(mappedBy = "products", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	public Set<Order> getOrders() {
		return orders;
	}
	
	public void setOrders(Set<Order> orders) {
		this.orders = orders;
	}
}
