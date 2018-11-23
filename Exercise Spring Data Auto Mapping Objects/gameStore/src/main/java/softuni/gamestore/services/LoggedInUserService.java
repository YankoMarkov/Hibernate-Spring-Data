package softuni.gamestore.services;

public interface LoggedInUserService {
	
	void addLoggedInUser(Long userId);
	
	void removeLoggedInUser(Long userId);
}
