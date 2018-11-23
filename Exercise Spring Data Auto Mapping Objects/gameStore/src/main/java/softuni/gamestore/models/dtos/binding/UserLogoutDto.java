package softuni.gamestore.models.dtos.binding;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class UserLogoutDto {
	private String email;
	
	public UserLogoutDto() {
	}
	
	public UserLogoutDto(String email) {
		setEmail(email);
	}
	
	@NotNull
	@Pattern(regexp = "[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}", message = "Invalid email")
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
}
