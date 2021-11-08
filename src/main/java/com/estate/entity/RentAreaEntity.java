package com.estate.entity;



import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name ="rentarea")

public class RentAreaEntity extends BaseEntity{
    
	@Column(name="value")
	private Float value;
	
	@ManyToOne()
	@JoinColumn(name="building_id", nullable = false)
	private BuildingEntity building;


	public Float getValue() {
		return value;
	}

	public void setValue(Float value) {
		this.value = value;
	}

	public BuildingEntity getBuilding() {
		return building;
	}

	public void setBuilding(BuildingEntity building) {
		this.building = building;
	}
}
