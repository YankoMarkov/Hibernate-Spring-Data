package softuni.cardealer.models.entities;

import softuni.cardealer.models.enums.Discount;

import javax.persistence.*;

@Entity(name = "sales")
public class Sale extends BaseEntity {
	private Discount discount;
	private Car car;
	private Customer customer;
	
	public Sale() {
	}
	
	@Column(name = "discount")
	@Enumerated(value = EnumType.STRING)
	public Discount getDiscount() {
		return discount;
	}
	
	public void setDiscount(Discount discount) {
		this.discount = discount;
	}
	
	@OneToOne
	@JoinColumn(name = "car_id", referencedColumnName = "id")
	public Car getCar() {
		return car;
	}
	
	public void setCar(Car car) {
		this.car = car;
	}
	
	@ManyToOne
	@JoinColumn(name = "customer_id", referencedColumnName = "id")
	public Customer getCustomer() {
		return customer;
	}
	
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
}
