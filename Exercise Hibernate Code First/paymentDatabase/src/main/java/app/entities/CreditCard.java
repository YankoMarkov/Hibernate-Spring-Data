package app.entities;

import javax.persistence.*;
import java.time.LocalDate;

@Entity(name = "credit_cards")
public class CreditCard extends BillingDetail {
	private int id;
	private String cardType;
	private LocalDate expirationMonth;
	private LocalDate expirationYear;
	
	public CreditCard() {
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	@Column(name = "card_type")
	public String getCardType() {
		return cardType;
	}
	
	public void setCardType(String cardType) {
		this.cardType = cardType;
	}
	
	@Column(name = "expiration_month")
	public LocalDate getExpirationMonth() {
		return expirationMonth;
	}
	
	public void setExpirationMonth(LocalDate expirationMonth) {
		this.expirationMonth = expirationMonth;
	}
	
	@Column(name = "expiration_year")
	public LocalDate getExpirationYear() {
		return expirationYear;
	}
	
	public void setExpirationYear(LocalDate expirationYear) {
		this.expirationYear = expirationYear;
	}
}
