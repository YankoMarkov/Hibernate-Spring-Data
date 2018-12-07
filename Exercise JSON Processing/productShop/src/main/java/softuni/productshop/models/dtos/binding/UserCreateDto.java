package softuni.productshop.models.dtos.binding;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

public class UserCreateDto {
	private String firstName;
	private String lastName;
	private Integer age;
	
	public UserCreateDto() {
	}
	
	public String getFirstName() {
		return firstName;
	}
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	@NotNull(message = "Last name cannot be empty")
	@Length(min = 3, message = "Last name must be minimum 3 symbols")
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
}
