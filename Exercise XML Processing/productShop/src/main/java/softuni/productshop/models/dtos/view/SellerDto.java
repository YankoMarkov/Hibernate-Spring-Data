package softuni.productshop.models.dtos.view;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "user")
@XmlAccessorType(XmlAccessType.FIELD)
public class SellerDto {
	
	@XmlAttribute(name = "first_name")
	private String firstName;
	
	@XmlAttribute(name = "last_name")
	private String lastName;
	
	@XmlElement(name = "sold_products")
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
