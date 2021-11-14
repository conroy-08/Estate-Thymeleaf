package com.estate.enums;

public enum DistrictsEnum {
    Q_HoangMai("Quận Hoàng Mai"),
    Q_LongBien("Quận Long Biên"),
    Q_ThanhXuan("Quận Thanh Xuân"),
    Q_BacTuLiem("Quận Bắc Từ Liêm"),
    Q_NamTuLiem("Quận Nam Từ Liêm"),
    Q_BaDinh("Quận Ba Đình"),
    Q_CauGiay("Quận Cầu Giấy"),
    Q_DongDa("Quận Đống Đa"),
    Q_HaiBaTrung("Quận Hai Bà Trưng"),
    Q_HoanKiem("Quận Hoàn Kiếm"),
    Q_HaDong("Quận Hà Đông"),
    Q_TayHo("Quận Tây Hồ");
    private final String districtValue;

    DistrictsEnum(String districtValue) {
        this.districtValue = districtValue;
    }

    public String getDistrictValue() {
        return districtValue;
    }
}
