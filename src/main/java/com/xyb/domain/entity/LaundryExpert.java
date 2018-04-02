package com.xyb.domain.entity;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "laundry_expert")
public class LaundryExpert {

    @Id
    @GeneratedValue
    @Column(name = "id")
    @ApiModelProperty("id")
    private Long id;
    @Column(name = "name")
    @ApiModelProperty(required = true)
    @NotNull(message = "姓名不能为空")

    private String name;
    @Column(name = "head_url")
    private String headUrl;
    @Column(name = "phone")
    @ApiModelProperty(required = true)
    @NotNull(message = "请填写联系方式")
    private String phone;
    @Column(name = "des")
    private String des;
    @Column(name = "id_num")
    private String idNum;
    @Column(name = "birthday")
    @ApiModelProperty("birthday")
    private long birthday;
    @Column(name = "create_time")
    @ApiModelProperty(required = true)
    @NotNull(message = "请添加建立的时间")
    private long createTime;
    @Column(name = "address")
    private String address;
    @Column(name = "working_years")
    @ApiModelProperty(required = true)
    @NotNull(message = "请填写工作年限")
    private long workingYears;
    @Column(name = "level_id")
    @ApiModelProperty(required = true)
    private long levelId;
    @Column(name = "store_id")
    @ApiModelProperty(required = true)
    private long storeId;
    @Column(name = "professional_des")
    private String professionalDes;


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

    public String getHeadUrl() {
        return headUrl;
    }

    public void setHeadUrl(String headUrl) {
        this.headUrl = headUrl;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public String getIdNum() {
        return idNum;
    }

    public void setIdNum(String idNum) {
        this.idNum = idNum;
    }

    public long getBirthday() {
        return birthday;
    }

    public void setBirthday(long birthday) {
        this.birthday = birthday;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public long getWorkingYears() {
        return workingYears;
    }

    public void setWorkingYears(long workingYears) {
        this.workingYears = workingYears;
    }

    public long getLevelId() {
        return levelId;
    }

    public void setLevelId(long levelId) {
        this.levelId = levelId;
    }

    public long getStoreId() {
        return storeId;
    }

    public void setStoreId(long storeId) {
        this.storeId = storeId;
    }

    public String getProfessionalDes() {
        return professionalDes;
    }

    public void setProfessionalDes(String professionalDes) {
        this.professionalDes = professionalDes;
    }
}
