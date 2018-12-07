package softuni.cardealer.models.dtos.views;

public class LocalSupplierDto {
	private Long id;
	private String name;
	private Integer partsCount;
	
	public LocalSupplierDto() {
	}
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public Integer getPartsCount() {
		return partsCount;
	}
	
	public void setPartsCount(Integer partsCount) {
		this.partsCount = partsCount;
	}
}
