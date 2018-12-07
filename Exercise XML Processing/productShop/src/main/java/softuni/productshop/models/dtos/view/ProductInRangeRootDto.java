package softuni.productshop.models.dtos.view;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "products")
@XmlAccessorType(XmlAccessType.FIELD)
public class ProductInRangeRootDto {
	
	@XmlElement(name = "product")
	private List<ProductInRangeDto> productInRangeDtos;
	
	public ProductInRangeRootDto() {
		this.productInRangeDtos = new ArrayList<>();
	}
	
	public List<ProductInRangeDto> getProductInRangeDtos() {
		return productInRangeDtos;
	}
	
	public void setProductInRangeDtos(List<ProductInRangeDto> productInRangeDtos) {
		this.productInRangeDtos = productInRangeDtos;
	}
}
