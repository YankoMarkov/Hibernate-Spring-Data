package softuni.productshop.models.dtos.binding;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "category")
@XmlAccessorType(XmlAccessType.FIELD)
public class CategoryImportDto {
	
	@XmlElement(name = "name")
	private String name;
	
	public CategoryImportDto() {
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
