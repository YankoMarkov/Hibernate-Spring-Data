package softuni.productshop.models.dtos.view;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "users")
@XmlAccessorType(XmlAccessType.FIELD)
public class SellerRootDto {
	
	@XmlElement(name = "user")
	private List<SellerDto> sellerDtos;
	
	public SellerRootDto() {
		this.sellerDtos = new ArrayList<>();
	}
	
	public List<SellerDto> getSellerDtos() {
		return sellerDtos;
	}
	
	public void setSellerDtos(List<SellerDto> sellerDtos) {
		this.sellerDtos = sellerDtos;
	}
}
