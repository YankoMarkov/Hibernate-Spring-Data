package softuni.bookshop.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.bookshop.models.Author;
import softuni.bookshop.repositories.AuthorRepository;
import softuni.bookshop.util.FileUtil;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

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
	public String getAllAuthorsWithBooksCount() {
		List<Author> authors = this.authorRepository.findAllByBooksIsNotNull();
		
		List<String> authorsWithBooksCount = authors.stream()
				.distinct()
				.sorted((a, b) -> Integer.compare(b.getBooks().size(), a.getBooks().size()))
				.map(a -> String.format("%s %s %d", a.getFirstName(), a.getLastName(), a.getBooks().size()))
				.collect(Collectors.toList());
		
		return String.join(System.lineSeparator(), authorsWithBooksCount);
	}
}
