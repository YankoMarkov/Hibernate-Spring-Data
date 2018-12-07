package mostwanted.domain.dtos.bindings;

import javax.validation.constraints.NotNull;

public class TownImportDto {
	private String name;
	
	@NotNull
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
}
