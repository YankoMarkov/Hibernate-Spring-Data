package softuni.gamestore.models.dtos.binding;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.math.BigDecimal;
import java.time.LocalDate;

public class GameAddDto {
	private String title;
	private BigDecimal price;
	private Double size;
	private String trailer;
	private String image;
	private String description;
	private LocalDate releaseDate;
	
	public GameAddDto() {
	}
	
	public GameAddDto(String title, BigDecimal price, Double size, String trailer, String image, String description, LocalDate releaseDate) {
		setTitle(title);
		setPrice(price);
		setSize(size);
		setTrailer(trailer);
		setImage(image);
		setDescription(description);
		setReleaseDate(releaseDate);
	}
	
	@NotNull
	@Pattern(regexp = "^[A-Z][a-zA-Z0-9 ]{3,100}$", message = "Invalid title")
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	@NotNull
	@Min(value = 0)
	@Digits(integer = 19, fraction = 2)
	public BigDecimal getPrice() {
		return price;
	}
	
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	
	@NotNull
	@Min(value = 0)
	@Digits(integer = 19, fraction = 1)
	public Double getSize() {
		return size;
	}
	
	public void setSize(Double size) {
		this.size = size;
	}
	
	@NotNull
	@Length(min = 11, max = 11)
	public String getTrailer() {
		return trailer;
	}
	
	public void setTrailer(String trailer) {
		this.trailer = trailer;
	}
	
	@Pattern(regexp = "(http(s)?:\\/\\/)?(.)+")
	public String getImage() {
		return image;
	}
	
	public void setImage(String image) {
		this.image = image;
	}
	
	@NotNull
	@Length(min = 20, message = "Description must by minimum 20 symbol")
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	@NotNull
	public LocalDate getReleaseDate() {
		return releaseDate;
	}
	
	public void setReleaseDate(LocalDate releaseDate) {
		this.releaseDate = releaseDate;
	}
}
