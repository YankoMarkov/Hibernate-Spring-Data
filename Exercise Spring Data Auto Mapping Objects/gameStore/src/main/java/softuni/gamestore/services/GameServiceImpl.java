package softuni.gamestore.services;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import softuni.gamestore.models.dtos.binding.GameAddDto;
import softuni.gamestore.models.dtos.binding.GameEditDto;
import softuni.gamestore.models.dtos.view.GameAllDto;
import softuni.gamestore.models.dtos.view.GameDetailsDto;
import softuni.gamestore.models.entities.Game;
import softuni.gamestore.models.entities.LoggedInUser;
import softuni.gamestore.models.entities.User;
import softuni.gamestore.models.enums.Role;
import softuni.gamestore.repositories.GameRepository;
import softuni.gamestore.repositories.LoggedInUserRepository;
import softuni.gamestore.repositories.UserRepository;
import softuni.gamestore.utils.Validation;

import javax.validation.ConstraintViolation;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class GameServiceImpl implements GameService {
	private final GameRepository gameRepository;
	private final UserRepository userRepository;
	private final LoggedInUserRepository loggedInUserRepository;
	private ModelMapper modelMapper;
	
	@Autowired
	public GameServiceImpl(GameRepository gameRepository, UserRepository userRepository, LoggedInUserRepository loggedInUserRepository) {
		this.gameRepository = gameRepository;
		this.userRepository = userRepository;
		this.loggedInUserRepository = loggedInUserRepository;
		this.modelMapper = new ModelMapper();
	}
	
	@Override
	public String addGame(GameAddDto gameAddDto) {
		StringBuilder result = new StringBuilder();
		boolean isAdmin = false;
		List<LoggedInUser> loggedInUsers = this.loggedInUserRepository.findAll();
		if (loggedInUsers.isEmpty()) {
			result.append("No loggedIn users");
			return result.toString();
		}
		for (LoggedInUser loggedIn : loggedInUsers) {
			User user = this.userRepository.findById(loggedIn.getUserId()).orElse(null);
			if (user.getRole().equals(Role.ADMIN)) {
				isAdmin = true;
				break;
			}
		}
		if (!isAdmin) {
			result.append("Only admin user can add games");
			return result.toString();
		}
		Set<ConstraintViolation<GameAddDto>> violations = Validation.getValidator().validate(gameAddDto);
		
		if (violations.size() > 0) {
			for (ConstraintViolation<GameAddDto> violation : violations) {
				result.append(violation.getMessage());
			}
			return result.toString();
		}
		Game game = this.gameRepository.findByTitle(gameAddDto.getTitle());
		
		if (game != null) {
			result.append("Game already exist");
			return result.toString();
		}
		game = this.modelMapper.map(gameAddDto, Game.class);
		this.gameRepository.saveAndFlush(game);
		
		return String.format("Added %s", game.getTitle());
	}
	
	@Override
	public String editGame(GameEditDto gameEditDto) {
		StringBuilder result = new StringBuilder();
		boolean isAdmin = false;
		List<LoggedInUser> loggedInUsers = this.loggedInUserRepository.findAll();
		if (loggedInUsers.isEmpty()) {
			result.append("No loggedIn users");
			return result.toString();
		}
		for (LoggedInUser loggedIn : loggedInUsers) {
			User user = this.userRepository.findById(loggedIn.getUserId()).orElse(null);
			if (user.getRole().equals(Role.ADMIN)) {
				isAdmin = true;
				break;
			}
		}
		if (!isAdmin) {
			result.append("Only admin user can edit games");
			return result.toString();
		}
		Set<ConstraintViolation<GameEditDto>> violations = Validation.getValidator().validate(gameEditDto);
		
		if (violations.size() > 0) {
			for (ConstraintViolation<GameEditDto> violation : violations) {
				result.append(violation.getMessage());
			}
			return result.toString();
		}
		Game game = this.gameRepository.findById(gameEditDto.getId()).orElse(null);
		
		if (game == null) {
			result.append("Game don't exist");
			return result.toString();
		}
		game.setPrice(gameEditDto.getPrice());
		game.setSize(gameEditDto.getSize());
		this.gameRepository.saveAndFlush(game);
		return String.format("Edited %s", game.getTitle());
	}
	
	@Override
	@Modifying
	public String deleteGame(Long gameId) {
		StringBuilder result = new StringBuilder();
		boolean isAdmin = false;
		List<LoggedInUser> loggedInUsers = this.loggedInUserRepository.findAll();
		if (loggedInUsers.isEmpty()) {
			result.append("No loggedIn users");
			return result.toString();
		}
		for (LoggedInUser loggedIn : loggedInUsers) {
			User user = this.userRepository.findById(loggedIn.getUserId()).orElse(null);
			if (user.getRole().equals(Role.ADMIN)) {
				isAdmin = true;
				break;
			}
		}
		if (!isAdmin) {
			result.append("Only admin user can delete games");
			return result.toString();
		}
		Game game = this.gameRepository.findById(gameId).orElse(null);
		
		if (game == null) {
			result.append("Game don't exist");
			return result.toString();
		}
		this.gameRepository.deleteById(gameId);
		return String.format("Deleted %s", game.getTitle());
	}
	
	@Override
	public String allGames() {
		List<Game> games = this.gameRepository.findAll();
		
		if (games.isEmpty()) {
			return "Games don't exist";
		}
		List<GameAllDto> gamesAllDto = new ArrayList<>();
		
		for (Game game : games) {
			GameAllDto gameAllDto = this.modelMapper.map(game, GameAllDto.class);
			gamesAllDto.add(gameAllDto);
		}
		List<String> result = gamesAllDto.stream()
				.map(GameAllDto::toString)
				.collect(Collectors.toList());
		return String.join(System.lineSeparator(), result);
	}
	
	@Override
	public String detailsGame(String title) {
		Game game = this.gameRepository.findByTitle(title);
		
		if (game == null) {
			return "Game don't exist";
		}
		GameDetailsDto gameDetailsDto = this.modelMapper.map(game, GameDetailsDto.class);
		return gameDetailsDto.toString();
	}
}
