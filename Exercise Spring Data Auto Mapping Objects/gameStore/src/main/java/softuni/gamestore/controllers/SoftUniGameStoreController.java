package softuni.gamestore.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Controller;
import softuni.gamestore.models.dtos.binding.*;
import softuni.gamestore.services.GameService;
import softuni.gamestore.services.OrderService;
import softuni.gamestore.services.UserService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

@Controller
public class SoftUniGameStoreController implements CommandLineRunner {
	private final UserService userService;
	private final GameService gameService;
	private final OrderService orderService;
	
	@Autowired
	public SoftUniGameStoreController(UserService userService, GameService gameService, OrderService orderService) {
		this.userService = userService;
		this.gameService = gameService;
		this.orderService = orderService;
	}
	
	@Override
	public void run(String... args) {
		Scanner scan = new Scanner(System.in);
		String inputs = null;
		
		while (!(inputs = scan.nextLine()).equals("")) {
			String[] input = inputs.split("\\|");
			
			switch (input[0]) {
				case "RegisterUser":
					String registerEmail = input[1];
					String registerPassword = input[2];
					String registerConfirmPass = input[3];
					String registerName = input[4];
					UserRegisterDto userRegisterDto = new UserRegisterDto(registerEmail, registerPassword, registerConfirmPass, registerName);
					System.out.println(this.userService.registerUser(userRegisterDto));
					break;
				case "LoginUser":
					String loginEmail = input[1];
					String loginPassword = input[2];
					UserLoginDto userLoginDto = new UserLoginDto(loginEmail, loginPassword);
					System.out.println(this.userService.loginUser(userLoginDto));
					break;
				case "LogoutUser":
					String logoutEmail = input[1];
					UserLogoutDto userLogoutDto = new UserLogoutDto(logoutEmail);
					System.out.println(this.userService.logoutUser(userLogoutDto));
					break;
				case "AddGame":
					String addTitle = input[1];
					BigDecimal addPrice = new BigDecimal(input[2]);
					Double addSize = Double.valueOf(input[3]);
					String addTrailer = input[4];
					String addImage = input[5];
					String addDescription = input[6];
					DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
					LocalDate addDate = LocalDate.parse(input[7], formatter);
					GameAddDto gameAddDto = new GameAddDto(addTitle, addPrice, addSize, addTrailer, addImage, addDescription, addDate);
					System.out.println(this.gameService.addGame(gameAddDto));
					break;
				case "EditGame":
					Long editId = Long.valueOf(input[1]);
					String price = input[2].substring(input[2].indexOf("=") + 1);
					BigDecimal editPrice = new BigDecimal(price);
					String size = input[3].substring(input[3].indexOf("=") + 1);
					Double editSize = Double.valueOf(size);
					GameEditDto gameEditDto = new GameEditDto(editId, editPrice, editSize);
					System.out.println(this.gameService.editGame(gameEditDto));
					break;
				case "DeleteGame":
					Long deleteId = Long.valueOf(input[1]);
					System.out.println(this.gameService.deleteGame(deleteId));
					break;
				case "AllGames":
					System.out.println(this.gameService.allGames());
					break;
				case "DetailsGame":
					String detailsTitle = input[1];
					System.out.println(this.gameService.detailsGame(detailsTitle));
					break;
				case "OwnedGames":
					System.out.println(this.userService.userGames());
					break;
				case "AddItem":
					String addItemTitle = input[1];
					System.out.println(this.orderService.addProduct(addItemTitle));
					break;
				case "RemoveItem":
					String removeItemTitle = input[1];
					System.out.println(this.orderService.removeProduct(removeItemTitle));
					break;
				case "BuyItem":
					System.out.println(this.orderService.addProductsToUser());
					break;
			}
		}
	}
}
