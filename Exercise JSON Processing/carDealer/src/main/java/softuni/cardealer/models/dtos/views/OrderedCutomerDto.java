package softuni.cardealer.models.dtos.views;

import java.util.ArrayList;
import java.util.List;

public class OrderedCutomerDto {
	private Long id;
	private String name;
	private String birthDate;
	private Boolean isYoungDriver;
	private List<SaleDto> sales;
	
	public OrderedCutomerDto() {
		this.sales = new ArrayList<>();
	}
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
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
	
	public Boolean getYoungDriver() {
		return isYoungDriver;
	}
	
	public void setYoungDriver(Boolean youngDriver) {
		isYoungDriver = youngDriver;
	}
	
	public List<SaleDto> getSales() {
		return sales;
	}
	
	public void setSales(List<SaleDto> sales) {
		this.sales = sales;
	}
}
