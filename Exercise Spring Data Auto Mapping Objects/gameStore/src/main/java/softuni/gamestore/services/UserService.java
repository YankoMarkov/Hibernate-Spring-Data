package softuni.gamestore.services;

import softuni.gamestore.models.dtos.binding.UserLoginDto;
import softuni.gamestore.models.dtos.binding.UserLogoutDto;
import softuni.gamestore.models.dtos.binding.UserRegisterDto;

public interface UserService {
	
	String registerUser(UserRegisterDto userRegisterDto);
	
	String loginUser(UserLoginDto userLoginDto);
	
	String logoutUser(UserLogoutDto userlogoutDto);
	
	String userGames();
}
