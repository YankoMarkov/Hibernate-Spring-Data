package softuni.bookshop.services;

import java.io.IOException;

public interface AuthorService {
	
	void seedAuthors() throws IOException;
	
	String getAllAuthorsFirstNameEndsWith();
	
	String getAllAuthorsWithBookCopies();
}
