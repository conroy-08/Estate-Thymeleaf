package com.estate.enums;

public enum TransactionEnum {
    CUSTOMER_CARE("QUÁ TRÌNH CSKH"),
    GUIDE("Dẫn đi xem");

    private String value;

    TransactionEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
