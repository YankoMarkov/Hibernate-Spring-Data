package softuni.productshop.models.dtos.binding;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

public class CategoryCreateDto {
	private String name;
	
	public CategoryCreateDto() {
	}
	
	@NotNull(message = "Name cannot be null")
	@Length(min = 3, max = 15, message = "Length must be between 3 and 15 symbols")
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
}
