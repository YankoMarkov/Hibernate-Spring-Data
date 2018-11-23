package softuni.bookshop.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import softuni.bookshop.enums.AgeRestriction;
import softuni.bookshop.enums.EditionType;
import softuni.bookshop.models.Author;
import softuni.bookshop.models.Book;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
	
	List<Book> findAllByAgeRestrictionEquals(AgeRestriction ageRestriction);
	
	List<Book> findAllByEditionTypeEqualsAndCopiesIsLessThan(EditionType editionType, int copies);
	
	List<Book> findAllByPriceIsLessThanOrPriceIsGreaterThan(BigDecimal min, BigDecimal max);
	
	List<Book> findAllByReleaseDateIsNotNull();
	
	List<Book> findAllByReleaseDateBefore(LocalDate date);
	
	List<Book> findAllByTitleIsNotNull();
	
	List<Book> findAllByAuthorIsNotNull();
	
	@Query("SELECT b FROM softuni.bookshop.models.Book AS b WHERE TRIM(b.title) = :title")
	Book findBy(@Param("title") String title);
	
	List<Book> findAllByReleaseDateAfter(LocalDate date);
	
	@Modifying
	Integer deleteAllByCopiesLessThan(Integer number);
}
