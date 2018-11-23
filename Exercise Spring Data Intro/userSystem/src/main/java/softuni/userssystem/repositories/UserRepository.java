package softuni.userssystem.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import softuni.userssystem.models.User;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	List<User> findAllByEmailEndsWith(String emailProvider);
	
	List<User> findAllByLastTimeLoggedInIsBefore(LocalDate date);
}
