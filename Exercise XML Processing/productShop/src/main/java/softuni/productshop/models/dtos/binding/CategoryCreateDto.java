package softuni.productshop.models.dtos.binding;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "categories")
@XmlAccessorType(XmlAccessType.FIELD)
public class CategoryCreateDto {
	
	@XmlElement(name = "category")
	private CategoryImportDto[] categoryImportDtos;
	
	public CategoryCreateDto() {
	}
	
	public CategoryImportDto[] getCategoryImportDtos() {
		return categoryImportDtos;
	}
	
	public void setCategoryImportDtos(CategoryImportDto[] categoryImportDtos) {
		this.categoryImportDtos = categoryImportDtos;
	}
}
