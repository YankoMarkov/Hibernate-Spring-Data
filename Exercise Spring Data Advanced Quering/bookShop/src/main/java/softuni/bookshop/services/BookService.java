package softuni.bookshop.services;

import java.io.IOException;

public interface BookService {
	
	void seedBooks() throws IOException;
	
	String getAllBooksByAgeRestriction();
	
	String getAllBooksWithCopiesLessThen();
	
	String getAllBooksByPrice();
	
	String getAllBooksByReleaseDateIsNot();
	
	String getAllBooksByDateBefore();
	
	String getAllBooksContainsWordInTitle();
	
	String getAllBooksByAuthorFirstName();
	
	String getAllBooksByGivenSizeTitle();
	
	String getAllBooksByGivenTitle();
	
	String booksCopiesAfterDate();
	
	String removeBooksByCopies();
}
