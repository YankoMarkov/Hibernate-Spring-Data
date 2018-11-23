package softuni.bookshop.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Controller;
import softuni.bookshop.services.AuthorService;
import softuni.bookshop.services.BookService;
import softuni.bookshop.services.CategoryService;

@Controller
public class BookshopController implements CommandLineRunner {
	private final AuthorService authorService;
	private final CategoryService categoryService;
	private final BookService bookService;
	
	@Autowired
	public BookshopController(AuthorService authorService, CategoryService categoryService, BookService bookService) {
		this.authorService = authorService;
		this.categoryService = categoryService;
		this.bookService = bookService;
	}
	
	@Override
	public void run(String... args) throws Exception {
		this.authorService.seedAuthors();
		this.categoryService.seedCategories();
		this.bookService.seedBooks();
//		System.out.println(this.bookService.getAllBooksByAgeRestriction());
//		System.out.println(this.bookService.getAllBooksWithCopiesLessThen());
//		System.out.println(this.bookService.getAllBooksByPrice());
//		System.out.println(this.bookService.getAllBooksByReleaseDateIsNot());
//		System.out.println(this.bookService.getAllBooksByDateBefore());
//		System.out.println(this.authorService.getAllAuthorsFirstNameEndsWith());
//		System.out.println(this.bookService.getAllBooksContainsWordInTitle());
//		System.out.println(this.bookService.getAllBooksByAuthorFirstName());
//		System.out.println(this.bookService.getAllBooksByGivenSizeTitle());
//		System.out.println(this.authorService.getAllAuthorsWithBookCopies());
//		System.out.println(this.bookService.getAllBooksByGivenTitle());
//		System.out.println(this.bookService.booksCopiesAfterDate());
		System.out.println(this.bookService.removeBooksByCopies());
		
	}
}
