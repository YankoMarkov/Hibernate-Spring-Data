package softuni.productshop.models.dtos.binding;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "users")
@XmlAccessorType(XmlAccessType.FIELD)
public class UserCreateDto {
	
	@XmlElement(name = "user")
	private UserImportDto[] userImportDtos;
	
	public UserCreateDto() {
	}
	
	public UserImportDto[] getUserImportDtos() {
		return userImportDtos;
	}
	
	public void setUserImportDtos(UserImportDto[] userImportDtos) {
		this.userImportDtos = userImportDtos;
	}
}
