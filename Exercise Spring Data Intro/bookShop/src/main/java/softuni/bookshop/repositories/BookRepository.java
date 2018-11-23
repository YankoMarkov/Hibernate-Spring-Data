package softuni.bookshop.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import softuni.bookshop.models.Author;
import softuni.bookshop.models.Book;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
	
	List<Book> findAllByReleaseDateAfter(LocalDate date);
	
	List<Book> findAllByReleaseDateBefore(LocalDate date);
	
	List<Book> findAllByAuthorEqualsOrderByReleaseDateDescTitleAsc(Author author);
}
