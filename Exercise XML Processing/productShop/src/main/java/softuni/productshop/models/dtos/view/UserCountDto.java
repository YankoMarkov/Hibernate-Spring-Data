package softuni.productshop.models.dtos.view;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "users")
@XmlAccessorType(XmlAccessType.FIELD)
public class UserCountDto {
	
	@XmlAttribute(name = "count")
	private Integer UsersCount;
	
	@XmlElement(name = "user")
	private List<UserDto> userDtos;
	
	public UserCountDto() {
		this.userDtos = new ArrayList<>();
	}
	
	public Integer getUsersCount() {
		return UsersCount;
	}
	
	public void setUsersCount(Integer usersCount) {
		UsersCount = usersCount;
	}
	
	public List<UserDto> getUserDtos() {
		return userDtos;
	}
	
	public void setUserDtos(List<UserDto> userDtos) {
		this.userDtos = userDtos;
	}
}
