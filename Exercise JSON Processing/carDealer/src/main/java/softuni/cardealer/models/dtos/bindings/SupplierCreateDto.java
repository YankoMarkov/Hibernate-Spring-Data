package softuni.cardealer.models.dtos.bindings;

import javax.validation.constraints.NotNull;

public class SupplierCreateDto {
	private String name;
	private boolean isImporter;
	
	public SupplierCreateDto() {
	}
	
	@NotNull
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	@NotNull
	public boolean isImporter() {
		return isImporter;
	}
	
	public void setImporter(boolean importer) {
		isImporter = importer;
	}
}
