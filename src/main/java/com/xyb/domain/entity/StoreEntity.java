package com.xyb.domain.entity;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;

@Entity
@Table(name = "store")
public class StoreEntity {

    @Id
    @GeneratedValue
    @Column(name = "id")
    @ApiModelProperty("id")
    private Long id;
    @Column(name = "name")
    @ApiModelProperty(required = true)
    @NotNull(message = "请输入门店名称")
    private String name;
    @Column(name = "address")
    @NotNull(message = "请输入门店地址")
    @ApiModelProperty(required = true)
    private String address;

    @Column(name = "laundry_expert_list")
    @NotNull(message = "请选择管理人员")
    @ApiModelProperty(required = true)
    private ArrayList<Long> laundryExpertId;

    @Column(name = "create_time")
    @NotNull(message = "请选择开店时间")
    @ApiModelProperty(required = true)
    private ArrayList<Long> createTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public ArrayList<Long> getLaundryExpertId() {
        return laundryExpertId;
    }

    public void setLaundryExpertId(ArrayList<Long> laundryExpertId) {
        this.laundryExpertId = laundryExpertId;
    }

    public ArrayList<Long> getCreateTime() {
        return createTime;
    }

    public void setCreateTime(ArrayList<Long> createTime) {
        this.createTime = createTime;
    }

}
