package softuni.productshop.models.dtos.binding;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

public class ProductCreateDto {
	private String name;
	private BigDecimal price;
	
	public ProductCreateDto() {
	}
	
	@NotNull(message = "Name must be not null")
	@Length(min = 3, message = "Name must be minimum 3 symbols")
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	@NotNull(message = "Price must be not null")
	@DecimalMin(value = "0.01", message = "Price must be positive number")
	public BigDecimal getPrice() {
		return price;
	}
	
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
}
