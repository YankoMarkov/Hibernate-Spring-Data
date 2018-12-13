package alararestaurant.domain.entities;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "items")
public class Item extends BaseEntity {
	private String name;
	private Category category;
	private BigDecimal price;
	private Set<OrderItem> orderItems;
	
	public Item() {
		this.orderItems = new HashSet<>();
	}
	
	@Column(name = "name", nullable = false, unique = true)
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	@ManyToOne
	@JoinColumn(name = "category_id", referencedColumnName = "id", nullable = false)
	public Category getCategory() {
		return category;
	}
	
	public void setCategory(Category category) {
		this.category = category;
	}
	
	@Column(name = "price", nullable = false)
	public BigDecimal getPrice() {
		return price;
	}
	
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	
	@OneToMany(mappedBy = "item")
	public Set<OrderItem> getOrderItems() {
		return orderItems;
	}
	
	public void setOrderItems(Set<OrderItem> orderItems) {
		this.orderItems = orderItems;
	}
}
