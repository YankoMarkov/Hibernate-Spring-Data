package softuni.gamestore.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import softuni.gamestore.models.entities.Game;

@Repository
public interface GameRepository extends JpaRepository<Game, Long> {
	
	Game findByTitle(String title);
}
