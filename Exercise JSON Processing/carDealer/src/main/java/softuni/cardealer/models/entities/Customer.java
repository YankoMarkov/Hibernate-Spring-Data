package softuni.cardealer.models.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "customers")
public class Customer extends BaseEntity {
	private String name;
	private LocalDate birthDate;
	private boolean isYoungDriver;
	private Set<Sale> sales;
	
	public Customer() {
		this.sales = new HashSet<>();
	}
	
	@Column(name = "name", nullable = false)
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	@Column(name = "birth_date", nullable = false)
	public LocalDate getBirthDate() {
		return birthDate;
	}
	
	public void setBirthDate(LocalDate birthDate) {
		this.birthDate = birthDate;
	}
	
	@Column(name = "is_young_driver", nullable = false)
	public boolean isYoungDriver() {
		return isYoungDriver;
	}
	
	public void setYoungDriver(boolean youngDriver) {
		isYoungDriver = youngDriver;
	}
	
	@OneToMany(mappedBy = "customer", fetch = FetchType.EAGER)
	public Set<Sale> getSales() {
		return sales;
	}
	
	public void setSales(Set<Sale> sales) {
		this.sales = sales;
	}
}
