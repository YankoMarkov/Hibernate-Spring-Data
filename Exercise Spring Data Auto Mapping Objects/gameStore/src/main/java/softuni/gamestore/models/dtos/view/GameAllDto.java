package softuni.gamestore.models.dtos.view;

import java.math.BigDecimal;

public class GameAllDto {
	private String title;
	private BigDecimal price;
	
	public GameAllDto() {
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public BigDecimal getPrice() {
		return price;
	}
	
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	
	@Override
	public String toString() {
		return String.format("%s %.2f", this.getTitle(), this.getPrice());
	}
}
