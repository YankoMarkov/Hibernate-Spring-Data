package softuni.userssystem.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Controller;
import softuni.userssystem.services.UserService;

import java.time.LocalDate;

@Controller
public class UserSystemController implements CommandLineRunner {
	private final UserService userService;
	
	@Autowired
	public UserSystemController(UserService userService) {
		this.userService = userService;
	}
	
	@Override
	public void run(String... args) throws Exception {
		this.userService.seedUsers();
		System.out.println(this.userService.getAllUserWithEmailProvider("gmail.com"));
		System.out.println(this.userService.getAllUsersBeforeLastloggedInDate(LocalDate.parse("2004-10-12")));
	}
}
