package softuni.productshop.models.dtos.view;

import java.util.List;

public class UserCountDto {
	private Integer UsersCount;
	private List<UserDto> userDtos;
	
	public UserCountDto() {
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
