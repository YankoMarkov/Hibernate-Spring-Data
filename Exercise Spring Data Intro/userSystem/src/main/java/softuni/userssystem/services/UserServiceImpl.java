package softuni.userssystem.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import softuni.userssystem.models.User;
import softuni.userssystem.repositories.UserRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserServiceImpl implements UserService {
	
	private final UserRepository userRepository;
	
	@Autowired
	public UserServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	@Override
	public void seedUsers() {
		String[] emailDomain = new String[]{"gmail.com", "yahoo.co.uk", "abv.bg"};
		for (int i = 0; i < 10; i++) {
			String firstName = "Pesho" + i;
			String lastNAme = "Ivanov" + i;
			int age = 10 + i;
			String username = firstName + i;
			String password = i + lastNAme + "!";
			String email = String.format("%s%s@%s", firstName, lastNAme, emailDomain[i % emailDomain.length]);
			int profilePicture = i;
			LocalDate registrationOn = LocalDate.now();
			LocalDate lastTimeLoggedIn = LocalDate.now();
			int bornTown = i;
			int livingTown = i;
			
			User user = new User();
			user.setAge(age);
			user.setFirstName(firstName);
			user.setLastName(lastNAme);
			user.setPassword(password);
			user.setUsername(username);
			user.setEmail(email);
			user.setDeleted(false);
			user.setRegisteredOn(registrationOn);
			user.setLastTimeLoggedIn(lastTimeLoggedIn);
			this.userRepository.saveAndFlush(user);
		}
		// трябва да се вкарат подходящи дати за триенето по дати
	}
	
	@Override
	public String getAllUserWithEmailProvider(String emailProvider) {
		List<User> users = this.userRepository.findAllByEmailEndsWith(emailProvider);
		
		List<String> usersWithEmailProvider = users.stream()
				.map(a -> String.format("%s %s", a.getUsername(), a.getEmail()))
				.collect(Collectors.toList());
		
		return String.join(System.lineSeparator(), usersWithEmailProvider);
	}
	
	@Override
	public String getAllUsersBeforeLastloggedInDate(LocalDate date) {
		List<User> users = this.userRepository.findAllByLastTimeLoggedInIsBefore(date);
		
		users.forEach(user -> {
			user.setDeleted(true);
			this.userRepository.saveAndFlush(user);
		});
		if (users.size() > 0) {
			return String.format("%d users have been deleted", users.size());
		}
		return "No users have been deleted";
	}
}
