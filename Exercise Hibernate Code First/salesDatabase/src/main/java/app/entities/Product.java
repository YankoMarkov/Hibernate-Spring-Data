package app.entities;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Set;

@Entity
@Table(name = "products")
public class Product {
	private int id;
	private String name;
	private double quantity;
	private BigDecimal price;
	private Set<Sale> sales;
	
	public Product() {
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	@Column(name = "name", length = 50)
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	@Column(name = "quantity")
	public double getQuantity() {
		return quantity;
	}
	
	public void setQuantity(double quantity) {
		this.quantity = quantity;
	}
	
	@Column(name = "price")
	public BigDecimal getPrice() {
		return price;
	}
	
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	
	@OneToMany(targetEntity = Sale.class, mappedBy = "product")
	public Set<Sale> getSales() {
		return sales;
	}
	
	public void setSales(Set<Sale> sales) {
		this.sales = sales;
	}
}
