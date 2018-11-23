package softuni.userssystem.services;

import java.time.LocalDate;

public interface UserService {

	void seedUsers();
	
	String getAllUserWithEmailProvider(String emailProvider);
	
	String getAllUsersBeforeLastloggedInDate(LocalDate date);
}
