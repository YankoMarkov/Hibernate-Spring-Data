package softuni.cardealer.models.dtos.bindings;

public class CustomerCreateDto {
	private String name;
	private String birthDate;
	private boolean isYoungDriver;
	
	public CustomerCreateDto() {
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getBirthDate() {
		return birthDate;
	}
	
	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
	}
	
	public boolean isYoungDriver() {
		return isYoungDriver;
	}
	
	public void setYoungDriver(boolean youngDriver) {
		isYoungDriver = youngDriver;
	}
}
