package softuni.gamestore.services;

import softuni.gamestore.models.dtos.binding.GameAddDto;
import softuni.gamestore.models.dtos.binding.GameEditDto;

public interface GameService {
	
	String addGame(GameAddDto gameAddDto);
	
	String editGame(GameEditDto gameEditDto);
	
	String deleteGame(Long gameId);
	
	String allGames();
	
	String detailsGame(String title);
}
