package alararestaurant.domain.entities;

import alararestaurant.domain.enums.OrderType;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "orders")
public class Order extends BaseEntity {
	private String customer;
	private LocalDateTime dateTime;
	private OrderType type;
	private Employee employee;
	private Set<OrderItem> orderItems;
	
	public Order() {
		this.orderItems = new HashSet<>();
	}
	
	@Column(name = "customer", nullable = false, columnDefinition = "text")
	public String getCustomer() {
		return customer;
	}
	
	public void setCustomer(String customer) {
		this.customer = customer;
	}
	
	@Column(name = "date_time", nullable = false)
	public LocalDateTime getDateTime() {
		return dateTime;
	}
	
	public void setDateTime(LocalDateTime dateTime) {
		this.dateTime = dateTime;
	}
	
	@Column(name = "type", nullable = false)
	@Enumerated(value = EnumType.STRING)
	public OrderType getType() {
		return type;
	}
	
	public void setType(OrderType type) {
		this.type = type;
	}
	
	@ManyToOne
	@JoinColumn(name = "employee_id", referencedColumnName = "id", nullable = false)
	public Employee getEmployee() {
		return employee;
	}
	
	public void setEmployee(Employee employee) {
		this.employee = employee;
	}
	
	@OneToMany(mappedBy = "order")
	public Set<OrderItem> getOrderItems() {
		return orderItems;
	}
	
	public void setOrderItems(Set<OrderItem> orderItems) {
		this.orderItems = orderItems;
	}
}
