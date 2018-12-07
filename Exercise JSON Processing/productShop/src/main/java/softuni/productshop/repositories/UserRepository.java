package softuni.productshop.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import softuni.productshop.models.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
	
	boolean existsByFirstNameAndLastName(String firstName, String lastName);

//	List<User> findAllBySoldGreaterThan(int number);

//	@Query("SELECT u FROM User AS u WHERE COUNT(u.sold) > 0")
//	List<User> getAllUsersWithSoldProducts();
}
