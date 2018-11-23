package softuni.gamestore.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import softuni.gamestore.models.entities.LoggedInUser;

@Repository
public interface LoggedInUserRepository extends JpaRepository<LoggedInUser, Long> {
}
