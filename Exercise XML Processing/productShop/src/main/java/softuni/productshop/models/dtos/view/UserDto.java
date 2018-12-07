package softuni.productshop.models.dtos.view;

import javax.xml.bind.annotation.*;

@XmlRootElement(name = "user")
@XmlAccessorType(XmlAccessType.FIELD)
public class UserDto {
	
	@XmlAttribute(name = "first-name")
	private String firstName;
	
	@XmlAttribute(name = "last-name")
	private String lastName;
	
	@XmlAttribute(name = "age")
	private Integer age;
	
	@XmlElement(name = "sold-products")
	private SoldProductsDto soldProductsDto;
	
	public UserDto() {
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
	
	public Integer getAge() {
		return age;
	}
	
	public void setAge(Integer age) {
		this.age = age;
	}
	
	public SoldProductsDto getSoldProductsDto() {
		return soldProductsDto;
	}
	
	public void setSoldProductsDto(SoldProductsDto soldProductsDto) {
		this.soldProductsDto = soldProductsDto;
	}
}
