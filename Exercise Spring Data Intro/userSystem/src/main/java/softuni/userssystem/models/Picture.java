package softuni.userssystem.models;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "pictures")
public class Picture extends BaseEntity {
	private String title;
	private String caption;
	private String filePath;
	private Set<User> owners;
	private Set<Album> albums;
	
	public Picture() {
		albums = new HashSet<>();
	}
	
	@Column(name = "title", nullable = false)
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	@Column(name = "caption")
	public String getCaption() {
		return caption;
	}
	
	public void setCaption(String caption) {
		this.caption = caption;
	}
	
	@Column(name = "file_path", nullable = false)
	public String getFilePath() {
		return filePath;
	}
	
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	
	@OneToMany(mappedBy = "profilePicture", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	public Set<User> getOwners() {
		return owners;
	}
	
	public void setOwners(Set<User> owners) {
		this.owners = owners;
	}
	
	@ManyToMany(mappedBy = "pictures", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	public Set<Album> getAlbums() {
		return albums;
	}
	
	public void setAlbums(Set<Album> albums) {
		this.albums = albums;
	}
}
