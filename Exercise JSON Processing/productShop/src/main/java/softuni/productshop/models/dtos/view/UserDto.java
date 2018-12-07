package softuni.productshop.models.dtos.view;

public class UserDto {
	private String firstName;
	private String lastName;
	private Integer age;
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
