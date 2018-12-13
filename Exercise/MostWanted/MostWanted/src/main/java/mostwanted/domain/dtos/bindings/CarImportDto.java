package mostwanted.domain.dtos.bindings;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

public class CarImportDto {
	private String brand;
	private String model;
	private BigDecimal price;
	private Integer yearOfProduction;
	private String racerName;
	
	@NotNull
	public String getBrand() {
		return brand;
	}
	
	public void setBrand(String brand) {
		this.brand = brand;
	}
	
	@NotNull
	public String getModel() {
		return model;
	}
	
	public void setModel(String model) {
		this.model = model;
	}
	
	public BigDecimal getPrice() {
		return price;
	}
	
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	
	@NotNull
	@Positive
	public Integer getYearOfProduction() {
		return yearOfProduction;
	}
	
	public void setYearOfProduction(Integer yearOfProduction) {
		this.yearOfProduction = yearOfProduction;
	}
	
	public String getRacerName() {
		return racerName;
	}
	
	public void setRacerName(String racerName) {
		this.racerName = racerName;
	}
}
