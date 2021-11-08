package com.estate.entity;



import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name ="transaction")

public class TransactionEntity extends BaseEntity{
	
	@Column(name ="code")
	private String code;
	
	@Column(name ="note")
	private String note;

	
	@ManyToOne
	@JoinColumn(name="customer_id", nullable = false)
	private CustomerEntity customer;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public CustomerEntity getCustomer() {
		return customer;
	}

	public void setCustomer(CustomerEntity customer) {
		this.customer = customer;
	}
}
