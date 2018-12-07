package softuni.productshop.models.dtos.view;

import javax.xml.bind.annotation.*;
import java.math.BigDecimal;

@XmlRootElement(name = "category")
@XmlAccessorType(XmlAccessType.FIELD)
public class CategoryByProductCountDto {
	
	@XmlAttribute(name = "name")
	private String category;
	
	@XmlElement(name = "products-count")
	private Integer productsCount;
	
	@XmlElement(name = "average-price")
	private BigDecimal averagePrice;
	
	@XmlElement(name = "total-revenue")
	private BigDecimal totalRevenue;
	
	public CategoryByProductCountDto() {
	}
	
	public String getCategory() {
		return category;
	}
	
	public void setCategory(String category) {
		this.category = category;
	}
	
	public Integer getProductsCount() {
		return productsCount;
	}
	
	public void setProductsCount(Integer productsCount) {
		this.productsCount = productsCount;
	}
	
	public BigDecimal getAveragePrice() {
		return averagePrice;
	}
	
	public void setAveragePrice(BigDecimal averagePrice) {
		this.averagePrice = averagePrice;
	}
	
	public BigDecimal getTotalRevenue() {
		return totalRevenue;
	}
	
	public void setTotalRevenue(BigDecimal totalRevenue) {
		this.totalRevenue = totalRevenue;
	}
}
