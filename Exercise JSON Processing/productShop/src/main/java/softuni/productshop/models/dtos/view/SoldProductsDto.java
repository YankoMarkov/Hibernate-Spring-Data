package softuni.productshop.models.dtos.view;

import java.util.List;

public class SoldProductsDto {
	private Integer count;
	private List<ProductDto> productDtos;
	
	public SoldProductsDto() {
	}
	
	public Integer getCount() {
		return count;
	}
	
	public void setCount(Integer count) {
		this.count = count;
	}
	
	public List<ProductDto> getProductDtos() {
		return productDtos;
	}
	
	public void setProductDtos(List<ProductDto> productDtos) {
		this.productDtos = productDtos;
	}
}
