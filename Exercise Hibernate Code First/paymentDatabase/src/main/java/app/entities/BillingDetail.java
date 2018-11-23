package app.entities;

import javax.persistence.*;

@Entity(name = "billing_details")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class BillingDetail {
	private int id;
	private String number;
	private User owner;
	
	public BillingDetail() {
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	@Column(name = "number")
	public String getNumber() {
		return number;
	}
	
	public void setNumber(String number) {
		this.number = number;
	}
	
	@ManyToOne
	@JoinColumn(name = "owner_id", referencedColumnName = "id")
	public User getOwner() {
		return owner;
	}
	
	public void setOwner(User owner) {
		this.owner = owner;
	}
}
