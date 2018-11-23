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
import java.util.*;
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
	public String getAllBooksByAgeRestriction() {
		Scanner scan = new Scanner(System.in);
		String input = scan.nextLine().toUpperCase();
		
		AgeRestriction ageRestriction = AgeRestriction.valueOf(input);
		List<Book> books = this.bookRepository.findAllByAgeRestrictionEquals(ageRestriction);
		
		List<String> booksByAgeRestriction = books.stream()
				.map(a -> String.format("%s", a.getTitle()))
				.collect(Collectors.toList());
		
		return String.join(System.lineSeparator(), booksByAgeRestriction);
	}
	
	@Override
	public String getAllBooksWithCopiesLessThen() {
		EditionType editionType = EditionType.valueOf("GOLD");
		int copies = 5000;
		
		List<Book> books = this.bookRepository.findAllByEditionTypeEqualsAndCopiesIsLessThan(editionType, copies);
		
		List<String> booksWithCopiesLessThen = books.stream()
				.map(a -> String.format("%s", a.getTitle()))
				.collect(Collectors.toList());
		
		return String.join(System.lineSeparator(), booksWithCopiesLessThen);
	}
	
	@Override
	public String getAllBooksByPrice() {
		BigDecimal min = new BigDecimal(5);
		BigDecimal max = new BigDecimal(40);
		
		List<Book> books = this.bookRepository.findAllByPriceIsLessThanOrPriceIsGreaterThan(min, max);
		
		List<String> booksByPrice = books.stream()
				.map(a -> String.format("%s - $%.2f", a.getTitle(), a.getPrice()))
				.collect(Collectors.toList());
		
		return String.join(System.lineSeparator(), booksByPrice);
	}
	
	@Override
	public String getAllBooksByReleaseDateIsNot() {
		Scanner scan = new Scanner(System.in);
		int input = Integer.valueOf(scan.nextLine());
		
		List<Book> books = this.bookRepository.findAllByReleaseDateIsNotNull();
		
		List<String> booksNotContainsYear = books.stream()
				.filter(a -> a.getReleaseDate().getYear() != input)
				.map(a -> String.format("%s", a.getTitle()))
				.collect(Collectors.toList());
		
		return String.join(System.lineSeparator(), booksNotContainsYear);
	}
	
	@Override
	public String getAllBooksByDateBefore() {
		Scanner scan = new Scanner(System.in);
		String input = scan.nextLine();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		LocalDate date = LocalDate.parse(input, formatter);
		
		List<Book> books = this.bookRepository.findAllByReleaseDateBefore(date);
		
		List<String> booksByDateBefore = books.stream()
				.map(a -> String.format("%s %s %.2f", a.getTitle(), a.getEditionType(), a.getPrice()))
				.collect(Collectors.toList());
		
		return String.join(System.lineSeparator(), booksByDateBefore);
	}
	
	@Override
	public String getAllBooksContainsWordInTitle() {
		Scanner scan = new Scanner(System.in);
		String input = scan.nextLine().toLowerCase();
		
		List<Book> books = this.bookRepository.findAllByTitleIsNotNull();
		
		List<String> booksContainsWordInTitle = books.stream()
				.filter(a -> a.getTitle().toLowerCase().contains(input))
				.map(a -> String.format("%s", a.getTitle()))
				.collect(Collectors.toList());
		
		return String.join(System.lineSeparator(), booksContainsWordInTitle);
	}
	
	@Override
	public String getAllBooksByAuthorFirstName() {
		Scanner scan = new Scanner(System.in);
		String input = scan.nextLine().toLowerCase();
		
		List<Book> books = this.bookRepository.findAllByAuthorIsNotNull();
		
		List<String> booksByAuthorFirstName = books.stream()
				.filter(a -> a.getAuthor().getLastName().toLowerCase().startsWith(input))
				.map(a -> String.format("%s", a.getTitle()))
				.collect(Collectors.toList());
		
		return String.join(System.lineSeparator(), booksByAuthorFirstName);
	}
	
	@Override
	public String getAllBooksByGivenSizeTitle() {
		Scanner scan = new Scanner(System.in);
		int input = Integer.valueOf(scan.nextLine());
		
		List<Book> books = this.bookRepository.findAllByTitleIsNotNull();
		
		List<Book> booksCount = books.stream()
				.filter(a -> a.getTitle().length() > input)
				.collect(Collectors.toList());
		
		return String.format("%d", booksCount.size());
	}
	
	@Override
	public String getAllBooksByGivenTitle() {
		Scanner scan = new Scanner(System.in);
		String input = scan.nextLine().trim();
		
		Book book = this.bookRepository.findBy(input);
		
		return String.format("%s %s %s %.2f",
				book.getTitle(),
				book.getEditionType(),
				book.getAgeRestriction(),
				book.getPrice());
	}
	
	@Override
	public String booksCopiesAfterDate() {
		Scanner scan = new Scanner(System.in);
		String inputDate = scan.nextLine();
		int inputNumber = Integer.valueOf(scan.nextLine());
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yyyy");
		LocalDate date = LocalDate.parse(inputDate, formatter);
		
		List<Book> books = this.bookRepository.findAllByReleaseDateAfter(date);
		
		int result = books.size() * inputNumber;
		
		return String.format("%d", result);
	}
	
	@Override
	public String removeBooksByCopies() {
		Scanner scan = new Scanner(System.in);
		int input = Integer.valueOf(scan.nextLine());
		
		Integer booksCount = this.bookRepository.deleteAllByCopiesLessThan(input);
		
		return String.format("%d books were deleted", booksCount);
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
