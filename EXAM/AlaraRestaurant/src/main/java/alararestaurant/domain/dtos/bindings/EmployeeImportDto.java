package alararestaurant.domain.dtos.bindings;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;

public class EmployeeImportDto {
	private String name;
	private Integer age;
	private String position;
	
	@NotNull
	@Length(min = 3, max = 30)
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	@NotNull
	@Range(min = 15, max = 80)
	public Integer getAge() {
		return age;
	}
	
	public void setAge(Integer age) {
		this.age = age;
	}
	
	@NotNull
	@Length(min = 3, max = 30)
	public String getPosition() {
		return position;
	}
	
	public void setPosition(String position) {
		this.position = position;
	}
}
