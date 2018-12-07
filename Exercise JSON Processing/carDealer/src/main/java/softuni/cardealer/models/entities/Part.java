package softuni.cardealer.models.entities;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "parts")
public class Part extends BaseEntity {
	private String name;
	private BigDecimal price;
	private Long quantity;
	private Set<Car> cars;
	private Supplier supplier;
	
	public Part() {
		this.cars = new HashSet<>();
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
	
	@Column(name = "quantity", nullable = false)
	public Long getQuantity() {
		return quantity;
	}
	
	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}
	
	@ManyToMany(mappedBy = "parts")
	public Set<Car> getCars() {
		return cars;
	}
	
	public void setCars(Set<Car> cars) {
		this.cars = cars;
	}
	
	@ManyToOne
	@JoinColumn(name = "supplier_id", referencedColumnName = "id")
	public Supplier getSupplier() {
		return supplier;
	}
	
	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
	}
}
