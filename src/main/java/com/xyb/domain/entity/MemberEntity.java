package com.xyb.domain.entity;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;
@Entity
@Table(name = "member")
public class MemberEntity {
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
    private long createTime;

    @Column(name = "address")
    private String address;

    @Column(name = "member_category_id")
    @ApiModelProperty(required = true)
    @NotNull(message = "请选择会员类型")
    private long memberCategoryId;

    @Column(name = "store_id")
    @ApiModelProperty(required = true)
    @NotNull(message = "请选择门店")
    private long storeId;

    @Column(name = "remain_fee")
    private String remainFee;

    @ManyToMany(cascade = {CascadeType.REFRESH}, fetch = FetchType.EAGER)
    @JoinTable(name = "member_categorys", joinColumns = {@JoinColumn(name = "m_id")}, inverseJoinColumns = {@JoinColumn(name = "c_id")})
    private Set<MemberCategoryEntity> memberCategoryEntitySet;

    public Set<MemberCategoryEntity> getMemberCategoryEntity() {
        return memberCategoryEntitySet;
    }
    public void setMemberCategoryEntity(Set<MemberCategoryEntity> memberCategoryEntitySet) {
        this.memberCategoryEntitySet= memberCategoryEntitySet;
    }

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
        this.createTime = System.currentTimeMillis();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public long getMemberCategoryId() {
        return memberCategoryId;
    }

    public void setMemberCategoryId(long memberCategoryId) {
        this.memberCategoryId = memberCategoryId;
    }

    public long getStoreId() {
        return storeId;
    }

    public void setStoreId(long storeId) {
        this.storeId = storeId;
    }

    public String getRemainFee() {
        return remainFee;
    }

    public void setRemainFee(String remainFee) {
        this.remainFee = remainFee;
    }
}

