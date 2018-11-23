package softuni.bookshop.services;

import java.io.IOException;
import java.time.LocalDate;

public interface BookService {
	
	void seedBooks() throws IOException;
	
	String getAllBooksTitleAfterDate(LocalDate date);
	
	String getAllAuthorsWithBooksBeforeDate(LocalDate date);
	
	String getAllBooksByAuthor(String firstName, String lastName);
}
