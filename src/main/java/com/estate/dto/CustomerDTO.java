package com.estate.dto;


import com.estate.enums.Gender;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.List;

public class CustomerDTO extends AbstractDTO<CustomerDTO> {

    @NotBlank(message = "Vui lòng nhập họ tên . ")
    private String fullName;

    @NotBlank(message = "Vui lòng nhập số điện thoại.")
    private String phone;

    @NotBlank(message = "")
    @Email(message = "")
    private String email;

    private String company;
    private String note;
    private String demand;
    private String status;
    private Long staffId;
    private String staffName;
    private String address;
    private String thumbnail;
    private Gender gender;
    private List<TransactionDTO> transaction;


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

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getDemand() {
        return demand;
    }

    public void setDemand(String demand) {
        this.demand = demand;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getStaffId() {
        return staffId;
    }

    public void setStaffId(Long staffId) {
        this.staffId = staffId;
    }

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public List<TransactionDTO> getTransaction() {
        return transaction;
    }

    public void setTransaction(List<TransactionDTO> transaction) {
        this.transaction = transaction;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }
}
