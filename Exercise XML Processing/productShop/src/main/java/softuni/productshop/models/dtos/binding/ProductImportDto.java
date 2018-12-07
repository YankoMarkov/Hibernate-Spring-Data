package softuni.productshop.models.dtos.binding;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.math.BigDecimal;

@XmlRootElement(name = "product")
@XmlAccessorType(XmlAccessType.FIELD)
public class ProductImportDto {
	
	@XmlElement(name = "name")
	private String name;
	
	@XmlElement(name = "price")
	private BigDecimal price;
	
	public ProductImportDto() {
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
