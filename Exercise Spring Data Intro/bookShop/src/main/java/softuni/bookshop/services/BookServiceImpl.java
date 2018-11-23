package softuni.bookshop.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import softuni.bookshop.enums.AgeRestriction;
import softuni.bookshop.enums.EditionType;
import softuni.bookshop.models.Author;
import softuni.bookshop.models.Book;
import softuni.bookshop.models.Category;
import softuni.bookshop.repositories.AuthorRepository;
import softuni.bookshop.repositories.BookRepository;
import softuni.bookshop.repositories.CategoryRepository;
import softuni.bookshop.util.FileUtil;

import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@Transactional
public class BookServiceImpl implements BookService {
	private final File booksFilePath = new File("src/main/resources/files/books.txt");
	
	private final BookRepository bookRepository;
	private final AuthorRepository authorRepository;
	private final CategoryRepository categoryRepository;
	private final FileUtil fileUtil;
	
	@Autowired
	public BookServiceImpl(BookRepository bookRepository, AuthorRepository authorRepository, CategoryRepository categoryRepository, FileUtil fileUtil) {
		this.bookRepository = bookRepository;
		this.authorRepository = authorRepository;
		this.categoryRepository = categoryRepository;
		this.fileUtil = fileUtil;
	}
	
	@Override
	public void seedBooks() throws IOException {
		if (this.bookRepository.count() != 0) {
			return;
		}
		String[] bookFileContent = this.fileUtil.getFileContent(booksFilePath);
		for (String books : bookFileContent) {
			String[] bookContent = books.split("\\s+");
			
			EditionType editionType = EditionType.values()[Integer.valueOf(bookContent[0])];
			Author author = getRandomAuthor();
			Set<Category> categories = getRandomCategories();
			DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("d/M/yyyy");
			LocalDate releaseDate = LocalDate.parse(bookContent[1], dateTimeFormatter);
			Integer copies = Integer.valueOf(bookContent[2]);
			BigDecimal price = new BigDecimal(bookContent[3]);
			AgeRestriction ageRestriction = AgeRestriction.values()[Integer.valueOf(bookContent[4])];
			StringBuilder titleBuilder = new StringBuilder();
			for (int i = 5; i < bookContent.length; i++) {
				titleBuilder.append(bookContent[i]).append(" ");
			}
			titleBuilder.delete(titleBuilder.lastIndexOf(" "), titleBuilder.lastIndexOf(" "));
			String title = titleBuilder.toString();
			
			Book book = new Book();
			book.setEditionType(editionType);
			book.setAuthor(author);
			book.setAgeRestriction(ageRestriction);
			book.setCopies(copies);
			book.setPrice(price);
			book.setReleaseDate(releaseDate);
			book.setTitle(title);
			book.setCategories(categories);
			this.bookRepository.saveAndFlush(book);
		}
	}
	
	@Override
	public String getAllBooksTitleAfterDate(LocalDate date) {
		List<Book> books = this.bookRepository.findAllByReleaseDateAfter(date);
		
		List<String> booksAfterDate = books.stream()
				.map(Book::getTitle)
				.collect(Collectors.toList());
		
		return String.join(System.lineSeparator(), booksAfterDate);
	}
	
	@Override
	public String getAllAuthorsWithBooksBeforeDate(LocalDate date) {
		List<Book> books = this.bookRepository.findAllByReleaseDateBefore(date);
		
		Set<String> authorsWithBooksBeforeDate = books.stream()
				.map(a -> String.format("%s %s", a.getAuthor().getFirstName(), a.getAuthor().getLastName()))
				.collect(Collectors.toSet());
		
		return String.join(System.lineSeparator(), authorsWithBooksBeforeDate);
	}
	
	@Override
	public String getAllBooksByAuthor(String firstName, String lastName) {
		Author author = this.authorRepository.findFirstByFirstNameAndLastName(firstName, lastName);
		List<Book> books = this.bookRepository.findAllByAuthorEqualsOrderByReleaseDateDescTitleAsc(author);
		
		
		List<String> booksByAuthor = books.stream()
				.map(a -> String.format("%s %s %d", a.getTitle(), a.getReleaseDate(), a.getCopies()))
				.collect(Collectors.toList());
		
		return String.join(System.lineSeparator(), booksByAuthor);
	}
	
	private Author getRandomAuthor() {
		Random random = new Random();
		long authorId = random.nextInt((int) this.authorRepository.count() - 1) + 1;
		
		return this.authorRepository.findById(authorId).orElse(null);
	}
	
	private Set<Category> getRandomCategories() {
		Set<Category> categories = new LinkedHashSet<>();
		Random random = new Random();
		int length = random.nextInt(5);
		
		for (int i = 0; i < length; i++) {
			Category category = getRandomCategory();
			categories.add(category);
		}
		return categories;
	}
	
	private Category getRandomCategory() {
		Random random = new Random();
		long categoryId = random.nextInt((int) (this.categoryRepository.count() - 1)) + 1;
		
		return this.categoryRepository.findById(categoryId).orElse(null);
	}
}
