package softuni.gamestore.models.dtos.binding;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class UserLoginDto {
	private String email;
	private String password;
	
	public UserLoginDto() {
	}
	
	public UserLoginDto(String email, String password) {
		setEmail(email);
		setPassword(password);
	}
	
	@NotNull
	@Pattern(regexp = "[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}", message = "Invalid email")
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	@NotNull
	@Pattern(regexp = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?\\d)[a-zA-Z0-9#$%&_]{6,}$", message = "Password must be at least 6 symbols and must contain at least 1 uppercase, 1 lowercase letter and 1 digit")
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
}
