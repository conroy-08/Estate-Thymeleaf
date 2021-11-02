package com.estate.dto.response;



public class StaffReponseDTO {

    private Long staffId;
    private String fullName;
    private String buildingChecked;
    private String customerChecked;

    public Long getStaffId() {
        return staffId;
    }

    public void setStaffId(Long staffId) {
        this.staffId = staffId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getBuildingChecked() {
        return buildingChecked;
    }

    public void setBuildingChecked(String buildingChecked) {
        this.buildingChecked = buildingChecked;
    }

    public String getCustomerChecked() {
        return customerChecked;
    }

    public void setCustomerChecked(String customerChecked) {
        this.customerChecked = customerChecked;
    }
}
