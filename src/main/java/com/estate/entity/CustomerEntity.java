package com.estate.entity;



import com.estate.enums.Gender;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

@Entity
@Table(name = "customer")

public class CustomerEntity extends BaseEntity{
      
	@Column(name="fullname")
	private String fullName;
	
	@Column(name ="phone")
	private String phone;
	
	@Column(name="email")
	private String email;

	@Column(name="demand")
	private String demand;

	@Column(name="note")
	private String note;

	@Column(name="company")
	private String company;

	@Column(name = "status", nullable = false)
	private String status;

	@Column(name = "thumbnail")
	private String thumbnail;

	@Column(name = "address")
	private String address;

	@Enumerated(EnumType.STRING)
	private Gender gender;

	@ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE , CascadeType.PERSIST })
	@JoinTable(name = "assignmentCustomer",
			joinColumns = @JoinColumn(name = "customer_id", nullable = false),
			inverseJoinColumns = @JoinColumn(name = "user_id", nullable = false)
	)
	private List<UserEntity> staffs = new ArrayList<>();

	@OneToMany(mappedBy ="customer" , cascade = {CascadeType.MERGE , CascadeType.PERSIST , CascadeType.REMOVE})
	private List<TransactionEntity> transaction = new ArrayList<>();

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getDemand() {
		return demand;
	}

	public void setDemand(String demand) {
		this.demand = demand;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<UserEntity> getStaffs() {
		return staffs;
	}

	public void setStaffs(List<UserEntity> staffs) {
		this.staffs = staffs;
	}

	public List<TransactionEntity> getTransaction() {
		return transaction;
	}

	public void setTransaction(List<TransactionEntity> transaction) {
		this.transaction = transaction;
	}

	public String getThumbnail() {
		return thumbnail;
	}

	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}
}
