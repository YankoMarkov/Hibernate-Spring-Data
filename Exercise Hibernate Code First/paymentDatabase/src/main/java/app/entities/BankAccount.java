package app.entities;

import javax.persistence.*;

@Entity(name = "bank_accounts")
public class BankAccount extends BillingDetail {
	private int id;
	private String bankName;
	private String swiftCode;
	
	public BankAccount() {
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	@Column(name = "bank_name")
	public String getBankName() {
		return bankName;
	}
	
	public void setBankName(String bancName) {
		this.bankName = bancName;
	}
	
	@Column(name = "swift_code")
	public String getSwiftCode() {
		return swiftCode;
	}
	
	public void setSwiftCode(String swiftCode) {
		this.swiftCode = swiftCode;
	}
}
