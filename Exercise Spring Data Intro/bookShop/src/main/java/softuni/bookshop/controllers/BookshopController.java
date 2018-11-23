package softuni.bookshop.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Controller;
import softuni.bookshop.services.AuthorService;
import softuni.bookshop.services.BookService;
import softuni.bookshop.services.CategoryService;

import java.time.LocalDate;

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
		System.out.println(this.bookService.getAllBooksTitleAfterDate(LocalDate.parse("2000-12-31")));
		System.out.println(this.bookService.getAllAuthorsWithBooksBeforeDate(LocalDate.parse("1990-01-01")));
		System.out.println(this.authorService.getAllAuthorsWithBooksCount());
		System.out.println(this.bookService.getAllBooksByAuthor("George", "Powell"));
	}
}
