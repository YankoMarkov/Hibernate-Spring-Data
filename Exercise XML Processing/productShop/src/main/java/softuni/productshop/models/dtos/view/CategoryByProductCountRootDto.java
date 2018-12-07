package softuni.productshop.models.dtos.view;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "categories")
@XmlAccessorType(XmlAccessType.FIELD)
public class CategoryByProductCountRootDto {
	
	@XmlElement(name = "category")
	private List<CategoryByProductCountDto> categoryByProductCountDtos;
	
	public CategoryByProductCountRootDto() {
		this.categoryByProductCountDtos = new ArrayList<>();
	}
	
	public List<CategoryByProductCountDto> getCategoryByProductCountDtos() {
		return categoryByProductCountDtos;
	}
	
	public void setCategoryByProductCountDtos(List<CategoryByProductCountDto> categoryByProductCountDtos) {
		this.categoryByProductCountDtos = categoryByProductCountDtos;
	}
}
