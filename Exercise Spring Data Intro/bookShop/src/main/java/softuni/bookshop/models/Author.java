package softuni.bookshop.models;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "authors")
public class Author extends BaseEntity {
	private String firstName;
	private String lastName;
	private Set<Book> books;
	
	public Author() {
		books = new HashSet<>();
	}
	
	@Column(name = "first_name")
	public String getFirstName() {
		return firstName;
	}
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	@Column(name = "last_name", nullable = false)
	public String getLastName() {
		return lastName;
	}
	
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	@OneToMany(mappedBy = "author", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	public Set<Book> getBooks() {
		return books;
	}
	
	public void setBooks(Set<Book> books) {
		this.books = books;
	}
}
