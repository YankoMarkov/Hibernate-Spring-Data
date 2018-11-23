package softuni.gamestore.models.dtos.binding;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class GameEditDto {
	private Long id;
	private BigDecimal price;
	private Double size;
	
	public GameEditDto() {
	}
	
	public GameEditDto(Long id, BigDecimal price, Double size) {
		setId(id);
		setPrice(price);
		setSize(size);
	}
	
	@NotNull
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
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
}
