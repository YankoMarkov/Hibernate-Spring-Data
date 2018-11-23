package softuni.gamestore.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.gamestore.models.entities.LoggedInUser;
import softuni.gamestore.repositories.LoggedInUserRepository;

import java.util.List;

@Service
public class LoggedInUserServiceImpl implements LoggedInUserService {
	private final LoggedInUserRepository loggedInUserRepository;
	
	@Autowired
	public LoggedInUserServiceImpl(LoggedInUserRepository loggedInUserRepository) {
		this.loggedInUserRepository = loggedInUserRepository;
	}
	
	@Override
	public void addLoggedInUser(Long userId) {
		List<LoggedInUser> loggedInUsers = this.loggedInUserRepository.findAll();
		LoggedInUser loggedInUser = new LoggedInUser();
		
		if (loggedInUsers.isEmpty()) {
			loggedInUser.setUserId(userId);
			this.loggedInUserRepository.saveAndFlush(loggedInUser);
		} else {
			for (LoggedInUser loggedIn : loggedInUsers) {
				if (loggedIn.getUserId().equals(userId)) {
					return;
				}
			}
			loggedInUser.setUserId(userId);
			this.loggedInUserRepository.saveAndFlush(loggedInUser);
		}
	}
	
	@Override
	public void removeLoggedInUser(Long userId) {
		List<LoggedInUser> loggedInUsers = this.loggedInUserRepository.findAll();
		
		if (loggedInUsers.isEmpty()) {
			return;
		}
		for (LoggedInUser loggedInUser : loggedInUsers) {
			if (loggedInUser.getUserId().equals(userId)) {
				this.loggedInUserRepository.delete(loggedInUser);
			}
		}
	}
}
