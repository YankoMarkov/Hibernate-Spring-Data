package mostwanted.domain.dtos.bindings;

import javax.validation.constraints.NotNull;

public class DistrictImportDto {
	private String name;
	private String townName;
	
	@NotNull
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getTownName() {
		return townName;
	}
	
	public void setTownName(String townName) {
		this.townName = townName;
	}
}
