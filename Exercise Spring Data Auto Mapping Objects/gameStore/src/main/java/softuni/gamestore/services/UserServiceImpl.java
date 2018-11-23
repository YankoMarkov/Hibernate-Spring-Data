package softuni.gamestore.services;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.gamestore.models.dtos.binding.UserLoginDto;
import softuni.gamestore.models.dtos.binding.UserLogoutDto;
import softuni.gamestore.models.dtos.binding.UserRegisterDto;
import softuni.gamestore.models.entities.LoggedInUser;
import softuni.gamestore.models.entities.User;
import softuni.gamestore.models.enums.Role;
import softuni.gamestore.repositories.LoggedInUserRepository;
import softuni.gamestore.repositories.UserRepository;
import softuni.gamestore.utils.Validation;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolation;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
	private final UserRepository userRepository;
	private final LoggedInUserService loggedInUserService;
	private final LoggedInUserRepository loggedInUserRepository;
	private ModelMapper modelMapper;
	
	@Autowired
	public UserServiceImpl(UserRepository userRepository, LoggedInUserService loggedInUserService, LoggedInUserRepository loggedInUserRepository) {
		this.userRepository = userRepository;
		this.loggedInUserService = loggedInUserService;
		this.loggedInUserRepository = loggedInUserRepository;
		this.modelMapper = new ModelMapper();
	}
	
	@Override
	public String registerUser(UserRegisterDto userRegisterDto) {
		StringBuilder result = new StringBuilder();
		Set<ConstraintViolation<UserRegisterDto>> violations = Validation.getValidator().validate(userRegisterDto);
		
		if (violations.size() > 0) {
			for (ConstraintViolation<UserRegisterDto> violation : violations) {
				result.append(violation.getMessage());
			}
			return result.toString();
		}
		User user = this.userRepository.findByEmail(userRegisterDto.getEmail());
		
		if (user != null) {
			result.append("User already exist");
			return result.toString();
		}
		user = this.modelMapper.map(userRegisterDto, User.class);
		
		if (this.userRepository.count() == 0) {
			user.setRole(Role.ADMIN);
		} else {
			user.setRole(Role.USER);
		}
		this.userRepository.saveAndFlush(user);
		return String.format("%s was registered", user.getFullName());
	}
	
	@Override
	public String loginUser(UserLoginDto userLoginDto) {
		StringBuilder result = new StringBuilder();
		Set<ConstraintViolation<UserLoginDto>> violations = Validation.getValidator().validate(userLoginDto);
		
		if (violations.size() > 0) {
			for (ConstraintViolation<UserLoginDto> violation : violations) {
				result.append(violation.getMessage());
			}
			return result.toString();
		}
		User user = this.userRepository.findByEmail(userLoginDto.getEmail());
		
		if (user == null) {
			result.append("User don't exist");
			return result.toString();
		} else if (!user.getEmail().equals(userLoginDto.getEmail())) {
			result.append("Wrong password");
			return result.toString();
		}
		this.loggedInUserService.addLoggedInUser(user.getId());
		return String.format("Successfully logged in %s", user.getFullName());
	}
	
	@Override
	public String logoutUser(UserLogoutDto userlogoutDto) {
		StringBuilder result = new StringBuilder();
		Set<ConstraintViolation<UserLogoutDto>> violations = Validation.getValidator().validate(userlogoutDto);
		
		if (violations.size() > 0) {
			for (ConstraintViolation<UserLogoutDto> violation : violations) {
				result.append(violation.getMessage());
			}
			return result.toString();
		}
		User user = this.userRepository.findByEmail(userlogoutDto.getEmail());
		
		if (user == null) {
			result.append("User don't exist");
			return result.toString();
		}
		this.loggedInUserService.removeLoggedInUser(user.getId());
		return String.format("User %s successfully logged out", user.getFullName());
	}
	
	@Override
	@Transactional
	public String userGames() {
		StringBuilder result = new StringBuilder();
		List<LoggedInUser> loggedInUsers = this.loggedInUserRepository.findAll();
		Long userId = loggedInUsers.get(loggedInUsers.size() - 1).getUserId();
		User user = this.userRepository.findById(userId).orElse(null);
		
		if (user == null) {
			result.append("No logged in user");
			return result.toString();
		}
		if (user.getOwnedGames().isEmpty()) {
			result.append("User don't have games");
			return result.toString();
		}
		List<String> usersGames = user.getOwnedGames().stream()
				.map(a -> String.format("%s", a.getTitle()))
				.collect(Collectors.toList());
		return String.join(System.lineSeparator(), usersGames);
	}
}
