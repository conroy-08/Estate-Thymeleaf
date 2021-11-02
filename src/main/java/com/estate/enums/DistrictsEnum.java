package com.estate.enums;

public enum DistrictsEnum {
    Q_1("Quận 1"),
    Q_2("Quận 2"),
    Q_3("Quận 3"),
    Q_4("Quận 4"),
    Q_5("Quận 5");

    private final String districtValue;

    DistrictsEnum(String districtValue) {
        this.districtValue = districtValue;
    }

    public String getDistrictValue() {
        return districtValue;
    }
}
