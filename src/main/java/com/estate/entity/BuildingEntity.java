package com.estate.entity;



import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;


@Entity
@Table(name = "building")

public class BuildingEntity extends BaseEntity {

    @Column(name = "name")
    private String name;
    
    @Column(name = "street")
    private String street;
    
    @Column(name = "ward")
    private String ward;   

    @Column(name = "buildingarea")
    private Integer buildingArea;
    
    @Column(name = "district")
    private String district;
    
    @Column(name = "structure")
    private String structure;
      
    @Column(name = "numberofbasement")
    private Integer numberOfBasement;
    
    @Column(name = "direction")
    private String direction;
    
    @Column(name = "level")
    private String level;
    
    @Column(name = "rentprice")
    private Integer rentPrice;
    
    @Column(name = "rentpricedescription")
    private String rentPriceDescription;  
    
    @Column(name = "servicefee")
    private String serviceFee;  
    
    @Column(name = "carfee")
    private String carFee;  
    
    @Column(name = "motofee")
    private String motoFee;  
    
    @Column(name = "overtimefee")
    private String overtimeFee;  
    
    @Column(name = "electricfee")
    private String electricFee;  
    
    @Column(name = "deposit")
    private String deposit;  
    
    @Column(name = "payment")
    private String payMent;  
    
    @Column(name = "renttime")
    private String rentTime; 
    
    @Column(name = "type")
    private String type; 
    
    @Column(name = "thumbnail")
    private String thumbnail; 
    
    @Column(name = "managername")
	private String managerName;
	
	@Column(name = "managerphone")
	private String managerPhone;

	@ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE , CascadeType.PERSIST  })
	@JoinTable(name = "assignmentBuilding",
			joinColumns = @JoinColumn(name = "building_id"),
			inverseJoinColumns = @JoinColumn(name = "user_id")
	)
	private List<UserEntity> staffs = new ArrayList<>();

    @OneToMany(mappedBy ="building", cascade = {CascadeType.MERGE , CascadeType.PERSIST , CascadeType.REMOVE})
	private List<RentAreaEntity> rentAreas = new ArrayList<>();


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getWard() {
        return ward;
    }

    public void setWard(String ward) {
        this.ward = ward;
    }

    public Integer getBuildingArea() {
        return buildingArea;
    }

    public void setBuildingArea(Integer buildingArea) {
        this.buildingArea = buildingArea;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getStructure() {
        return structure;
    }

    public void setStructure(String structure) {
        this.structure = structure;
    }

    public Integer getNumberOfBasement() {
        return numberOfBasement;
    }

    public void setNumberOfBasement(Integer numberOfBasement) {
        this.numberOfBasement = numberOfBasement;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public Integer getRentPrice() {
        return rentPrice;
    }

    public void setRentPrice(Integer rentPrice) {
        this.rentPrice = rentPrice;
    }

    public String getRentPriceDescription() {
        return rentPriceDescription;
    }

    public void setRentPriceDescription(String rentPriceDescription) {
        this.rentPriceDescription = rentPriceDescription;
    }

    public String getServiceFee() {
        return serviceFee;
    }

    public void setServiceFee(String serviceFee) {
        this.serviceFee = serviceFee;
    }

    public String getCarFee() {
        return carFee;
    }

    public void setCarFee(String carFee) {
        this.carFee = carFee;
    }

    public String getMotoFee() {
        return motoFee;
    }

    public void setMotoFee(String motoFee) {
        this.motoFee = motoFee;
    }

    public String getOvertimeFee() {
        return overtimeFee;
    }

    public void setOvertimeFee(String overtimeFee) {
        this.overtimeFee = overtimeFee;
    }

    public String getElectricFee() {
        return electricFee;
    }

    public void setElectricFee(String electricFee) {
        this.electricFee = electricFee;
    }

    public String getDeposit() {
        return deposit;
    }

    public void setDeposit(String deposit) {
        this.deposit = deposit;
    }

    public String getPayMent() {
        return payMent;
    }

    public void setPayMent(String payMent) {
        this.payMent = payMent;
    }

    public String getRentTime() {
        return rentTime;
    }

    public void setRentTime(String rentTime) {
        this.rentTime = rentTime;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getManagerName() {
        return managerName;
    }

    public void setManagerName(String managerName) {
        this.managerName = managerName;
    }

    public String getManagerPhone() {
        return managerPhone;
    }

    public void setManagerPhone(String managerPhone) {
        this.managerPhone = managerPhone;
    }

    public List<UserEntity> getStaffs() {
        return staffs;
    }

    public void setStaffs(List<UserEntity> staffs) {
        this.staffs = staffs;
    }

    public List<RentAreaEntity> getRentAreas() {
        return rentAreas;
    }

    public void setRentAreas(List<RentAreaEntity> rentAreas) {
        this.rentAreas = rentAreas;
    }
}
