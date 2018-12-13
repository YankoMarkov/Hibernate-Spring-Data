package alararestaurant.domain.dtos.bindings.order;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "items")
@XmlAccessorType(XmlAccessType.FIELD)
public class ItemRootDto {
	
	@XmlElement(name = "item")
	private List<ItemImportDto> itemImportDtos;
	
	public ItemRootDto() {
		this.itemImportDtos = new ArrayList<>();
	}
	
	public List<ItemImportDto> getItemImportDtos() {
		return itemImportDtos;
	}
	
	public void setItemImportDtos(List<ItemImportDto> itemImportDtos) {
		this.itemImportDtos = itemImportDtos;
	}
}
