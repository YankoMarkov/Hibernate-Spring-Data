package alararestaurant.domain.dtos.bindings.order;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "orders")
@XmlAccessorType(XmlAccessType.FIELD)
public class OrderRootDto {
	
	@XmlElement(name = "order")
	private List<OrderImportDto> orderImportDtos;
	
	public OrderRootDto() {
		this.orderImportDtos = new ArrayList<>();
	}
	
	public List<OrderImportDto> getOrderImportDtos() {
		return orderImportDtos;
	}
	
	public void setOrderImportDtos(List<OrderImportDto> orderImportDtos) {
		this.orderImportDtos = orderImportDtos;
	}
}
