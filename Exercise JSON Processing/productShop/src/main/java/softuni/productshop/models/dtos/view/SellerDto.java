package softuni.productshop.models.dtos.view;

import java.util.ArrayList;
import java.util.List;

public class SellerDto {
	private String firstName;
	private String lastName;
	private List<SoldProductDto> soldProductDtos;
	
	public SellerDto() {
		this.soldProductDtos = new ArrayList<>();
	}
	
	public String getFirstName() {
		return firstName;
	}
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	public String getLastName() {
		return lastName;
	}
	
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public List<SoldProductDto> getSoldProductDtos() {
		return soldProductDtos;
	}
	
	public void setSoldProductDtos(List<SoldProductDto> soldProductDtos) {
		this.soldProductDtos = soldProductDtos;
	}
}
