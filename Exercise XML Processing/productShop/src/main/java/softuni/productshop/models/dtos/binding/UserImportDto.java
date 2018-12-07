package softuni.productshop.models.dtos.binding;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "user")
@XmlAccessorType(XmlAccessType.FIELD)
public class UserImportDto {
	
	@XmlAttribute(name = "first-name")
	private String firstName;
	
	@XmlAttribute(name = "last-name")
	private String lastName;
	
	@XmlAttribute(name = "age")
	private Integer age;
	
	public UserImportDto() {
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
