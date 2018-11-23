package softuni.bookshop.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import softuni.bookshop.models.Author;

import java.util.List;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {
	
	List<Author> findAllByBooksIsNotNull();
	
	Author findFirstByFirstNameAndLastName(String firstName, String lastName);
}
