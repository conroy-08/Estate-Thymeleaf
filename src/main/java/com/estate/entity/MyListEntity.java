
package com.estate.entity;

import javax.persistence.*;

@Entity
@Table(name = "mylist")
public class MyListEntity extends  BaseEntity{

    @Column(name = "buildingid")
    private Long buildingid;

    @Column(name = "userid")
    private Long userId;

    public Long getBuildingid() {
        return buildingid;
    }

    public void setBuildingid(Long buildingid) {
        this.buildingid = buildingid;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
