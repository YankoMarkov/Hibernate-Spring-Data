package softuni.cardealer.models.entities;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "cars")
public class Car extends BaseEntity {
	private String make;
	private String model;
	private Long travelledDistance;
	private Set<Part> parts;
	
	public Car() {
		this.parts = new HashSet<>();
	}
	
	@Column(name = "make", nullable = false)
	public String getMake() {
		return make;
	}
	
	public void setMake(String make) {
		this.make = make;
	}
	
	@Column(name = "model", nullable = false)
	public String getModel() {
		return model;
	}
	
	public void setModel(String model) {
		this.model = model;
	}
	
	@Column(name = "travelled_distance", nullable = false)
	public Long getTravelledDistance() {
		return travelledDistance;
	}
	
	public void setTravelledDistance(Long travelledDistance) {
		this.travelledDistance = travelledDistance;
	}
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "parts_cars",
			joinColumns = @JoinColumn(name = "car_id", referencedColumnName = "id"),
			inverseJoinColumns = @JoinColumn(name = "part_id", referencedColumnName = "id"))
	public Set<Part> getParts() {
		return parts;
	}
	
	public void setParts(Set<Part> parts) {
		this.parts = parts;
	}
}
