package softuni.gamestore.models.entities;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity(name = "logged_in_user")
public class LoggedInUser extends BaseEntity {
	private Long userId;
	
	public LoggedInUser() {
	}
	
	@Column(name = "user_id")
	public Long getUserId() {
		return userId;
	}
	
	public void setUserId(Long userId) {
		this.userId = userId;
	}
}
