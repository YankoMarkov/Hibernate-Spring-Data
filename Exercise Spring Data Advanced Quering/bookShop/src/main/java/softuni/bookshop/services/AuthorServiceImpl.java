package softuni.bookshop.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.bookshop.models.Author;
import softuni.bookshop.models.Book;
import softuni.bookshop.repositories.AuthorRepository;
import softuni.bookshop.util.FileUtil;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toMap;

@Service
public class AuthorServiceImpl implements AuthorService {
	private final File authorsFilePath = new File("src/main/resources/files/authors.txt");
	
	private final AuthorRepository authorRepository;
	private final FileUtil fileUtil;
	
	@Autowired
	public AuthorServiceImpl(AuthorRepository authorRepository, FileUtil fileUtil) {
		this.authorRepository = authorRepository;
		this.fileUtil = fileUtil;
	}
	
	@Override
	public void seedAuthors() throws IOException {
		if (this.authorRepository.count() != 0) {
			return;
		}
		String[] authorFileContent = this.fileUtil.getFileContent(authorsFilePath);
		
		for (String authors : authorFileContent) {
			String[] names = authors.split("\\s+");
			
			Author author = new Author();
			author.setFirstName(names[0]);
			author.setLastName(names[1]);
			this.authorRepository.saveAndFlush(author);
		}
	}
	
	@Override
	public String getAllAuthorsFirstNameEndsWith() {
		Scanner scan = new Scanner(System.in);
		String input = scan.nextLine().toLowerCase();
		
		List<Author> authors = this.authorRepository.findAllByFirstNameEndsWith(input);
		
		List<String> authorsFirstNameEndsWith = authors.stream()
				.map(a -> String.format("%s %s", a.getFirstName(), a.getLastName()))
				.collect(Collectors.toList());
		
		return String.join(System.lineSeparator(), authorsFirstNameEndsWith);
	}
	
	@Override
	public String getAllAuthorsWithBookCopies() {
		List<Author> authors = this.authorRepository.findAllByBooksIsNotNull();
		
		Map<String, Integer> authorsWithBooksCopies = new HashMap<>();
		
		for (Author author : authors) {
			StringBuilder name = new StringBuilder();
			int count = 0;
			
			for (Book book : author.getBooks()) {
				count += book.getCopies();
			}
			name.append(author.getFirstName())
					.append(" ").append(author.getLastName());
			
			authorsWithBooksCopies.put(name.toString(), count);
		}
		List<String> result = new ArrayList<>();
		
		authorsWithBooksCopies = authorsWithBooksCopies.entrySet().stream()
				.sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
				.collect(toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2, LinkedHashMap::new));
		
		for (Map.Entry<String, Integer> stringIntegerEntry : authorsWithBooksCopies.entrySet()) {
			String res = String.format("%s - %d", stringIntegerEntry.getKey(), stringIntegerEntry.getValue());
			result.add(res);
		}
		
		return String.join(System.lineSeparator(), result);
	}
}
