package softuni.cardealer.models.dtos.bindings;

public class CarCreateDto {
	private String make;
	private String model;
	private Long travelledDistance;
	
	public CarCreateDto() {
	}
	
	public String getMake() {
		return make;
	}
	
	public void setMake(String make) {
		this.make = make;
	}
	
	public String getModel() {
		return model;
	}
	
	public void setModel(String model) {
		this.model = model;
	}
	
	public Long getTravelledDistance() {
		return travelledDistance;
	}
	
	public void setTravelledDistance(Long travelledDistance) {
		this.travelledDistance = travelledDistance;
	}
}
