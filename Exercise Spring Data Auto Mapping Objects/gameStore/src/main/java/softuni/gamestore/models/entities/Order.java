package softuni.gamestore.models.entities;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "orders")
public class Order extends BaseEntity {
	private User buyer;
	private Set<Game> products;
	
	public Order() {
		this.products = new HashSet<>();
	}
	
	@ManyToOne
	@JoinColumn(name = "buyer_id", referencedColumnName = "id")
	public User getBuyer() {
		return buyer;
	}
	
	public void setBuyer(User buyer) {
		this.buyer = buyer;
	}
	
	@ManyToMany
	@JoinTable(name = "orders_games",
			joinColumns = @JoinColumn(name = "order_id", referencedColumnName = "id"),
			inverseJoinColumns = @JoinColumn(name = "game_id", referencedColumnName = "id"))
	public Set<Game> getProducts() {
		return products;
	}
	
	public void setProducts(Set<Game> products) {
		this.products = products;
	}
}
