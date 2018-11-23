package softuni.userssystem.models;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "albums")
public class Album extends BaseEntity {
	private String name;
	private String background;
	private String color;
	private Boolean isPublic;
	private User owner;
	private Set<Picture> pictures;
	
	public Album() {
		this.pictures = new HashSet<>();
	}
	
	@Column(name = "name", nullable = false)
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	@Column(name = "background")
	public String getBackground() {
		return background;
	}
	
	public void setBackground(String background) {
		this.background = background;
	}
	
	@Column(name = "color")
	public String getColor() {
		return color;
	}
	
	public void setColor(String color) {
		this.color = color;
	}
	
	@Column(name = "is_public", nullable = false)
	public Boolean getPublic() {
		return isPublic;
	}
	
	public void setPublic(Boolean aPublic) {
		isPublic = aPublic;
	}
	
	@ManyToOne
	@JoinColumn(name = "user_id", referencedColumnName = "id")
	public User getOwner() {
		return owner;
	}
	
	public void setOwner(User owner) {
		this.owner = owner;
	}
	
	@ManyToMany
	@JoinTable(name = "albums_pictures",
			joinColumns = @JoinColumn(name = "album_id", referencedColumnName = "id"),
			inverseJoinColumns = @JoinColumn(name = "picture_id", referencedColumnName = "id"))
	public Set<Picture> getPictures() {
		return pictures;
	}
	
	public void setPictures(Set<Picture> pictures) {
		this.pictures = pictures;
	}
}
