package softuni.productshop.models.entities;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "products")
public class Product extends BaseEntity {
	private String name;
	private BigDecimal price;
	private User seller;
	private User buyer;
	private Set<Category> categories;
	
	public Product() {
		this.categories = new HashSet<>();
	}
	
	@Column(name = "name", nullable = false)
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	@Column(name = "price", nullable = false)
	public BigDecimal getPrice() {
		return price;
	}
	
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	
	@ManyToOne
	@JoinColumn(name = "seller_id", referencedColumnName = "id")
	public User getSeller() {
		return seller;
	}
	
	public void setSeller(User seller) {
		this.seller = seller;
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
	@JoinTable(name = "categories_products",
			joinColumns = @JoinColumn(name = "product_id", referencedColumnName = "id"),
			inverseJoinColumns = @JoinColumn(name = "category_id", referencedColumnName = "id"))
	public Set<Category> getCategories() {
		return categories;
	}
	
	public void setCategories(Set<Category> categories) {
		this.categories = categories;
	}
}
