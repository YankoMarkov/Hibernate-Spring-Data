package alararestaurant.domain.dtos.bindings.order;

import alararestaurant.domain.enums.OrderType;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "order")
@XmlAccessorType(XmlAccessType.FIELD)
public class OrderImportDto {
	
	@XmlElement(name = "customer")
	private String customer;
	
	@XmlElement(name = "employee")
	private String employee;
	
	@XmlElement(name = "date-time")
	private String dateTime;
	
	@XmlElement(name = "type")
	private OrderType type;
	
	@XmlElement(name = "items")
	private ItemRootDto itemRootDto;
	
	@NotNull
	public String getCustomer() {
		return customer;
	}
	
	public void setCustomer(String customer) {
		this.customer = customer;
	}
	
	@NotNull
	public String getEmployee() {
		return employee;
	}
	
	public void setEmployee(String employee) {
		this.employee = employee;
	}
	
	@NotNull
	public String getDateTime() {
		return dateTime;
	}
	
	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}
	
	@NotNull
	@Enumerated(EnumType.STRING)
	public OrderType getType() {
		return type;
	}
	
	public void setType(OrderType type) {
		this.type = type;
	}
	
	public ItemRootDto getItemRootDto() {
		return itemRootDto;
	}
	
	public void setItemRootDto(ItemRootDto itemRootDto) {
		this.itemRootDto = itemRootDto;
	}
}
