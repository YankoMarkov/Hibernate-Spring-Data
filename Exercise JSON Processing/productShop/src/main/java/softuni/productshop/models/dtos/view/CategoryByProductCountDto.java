package softuni.productshop.models.dtos.view;

import java.math.BigDecimal;

public class CategoryByProductCountDto {
	private String category;
	private Integer productsCount;
	private BigDecimal averagePrice;
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
