package softuni.gamestore.models.dtos.binding;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class UserRegisterDto {
	private String email;
	private String password;
	private String confirmPassword;
	private String fullName;
	
	public UserRegisterDto() {
	}
	
	public UserRegisterDto(String email, String password, String confirmPassword, String fullName) {
		setEmail(email);
		setPassword(password);
		setConfirmPassword(confirmPassword);
		setFullName(fullName);
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
	
	@NotNull
	public String getConfirmPassword() {
		return confirmPassword;
	}
	
	public void setConfirmPassword(String confirmPassword) {
		if (!confirmPassword.equals(this.getPassword())) {
			throw new IllegalArgumentException("Password don't match");
		}
		this.confirmPassword = confirmPassword;
	}
	
	@NotNull(message = "Missing name")
	public String getFullName() {
		return fullName;
	}
	
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
}
