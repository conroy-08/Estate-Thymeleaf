package com.estate.builder;

public class CustomerSearchBuilder {
    private String fullName;
    private String email;
    private String phone;
    private Long staffId;


    public String getFullName() {
        return fullName;
    }

    public String getEmail() {
        return email;
    }
    public String getPhoneNumber() {
        return phone;
    }

    public Long getStaffId() {
        return staffId;
    }

    public CustomerSearchBuilder(Builder builder) {
        this.fullName = builder.fullName;
        this.email = builder.email;
        this.phone = builder.phone;
        this.staffId = builder.staffId;

    }

    public static class Builder {
        private String fullName;
        private String email;
        private String phone;
        private Long staffId;

        public Builder setFullName(String fullName) {
            this.fullName = fullName;
            return this;
        }
        public Builder setEmail(String email) {
            this.email = email;
            return this;
        }
        public Builder setPhoneNumber(String phone) {
            this.phone = phone;
            return this;
        }
        public Builder setStaffId(Long staffId) {
            this.staffId = staffId;
            return this;
        }
        public CustomerSearchBuilder build() {
            return new CustomerSearchBuilder(this);
        }

    }
}
