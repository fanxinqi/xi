package com.xyb.domain.entity;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Set;

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
    @Column(name = "create_time")
    private Long createTime;
    @ManyToMany(cascade = {CascadeType.REFRESH}, fetch = FetchType.EAGER)
    @JoinTable(name = "store_member", joinColumns = {@JoinColumn(name = "store_id")}, inverseJoinColumns = {@JoinColumn(name = "member_id")})
    private Set<MemberEntity> storeMemberSet;
    @ManyToMany(cascade = {CascadeType.REFRESH}, fetch = FetchType.EAGER)
    @JoinTable(name = "store_order", joinColumns = {@JoinColumn(name = "store_id")}, inverseJoinColumns = {@JoinColumn(name = "order_id")})
    private Set<ClothesOrderEntity> storeOrderSet;
    @ManyToMany(cascade = {CascadeType.REFRESH}, fetch = FetchType.EAGER)
    @JoinTable(name = "store_laundry_expert", joinColumns = {@JoinColumn(name = "store_id")}, inverseJoinColumns = {@JoinColumn(name = "laundry_expert_id")})
    private Set<ClothesOrderEntity> storeLaundryExpertSet;

    public Set<MemberEntity> getStoreMemberSet() {
        return storeMemberSet;
    }

    public void setStoreMemberSet(Set<MemberEntity> storeMemberSet) {
        this.storeMemberSet = storeMemberSet;
    }

    public Set<ClothesOrderEntity> getStoreOrderSet() {
        return storeOrderSet;
    }

    public void setStoreOrderSet(Set<ClothesOrderEntity> storeOrderSet) {
        this.storeOrderSet = storeOrderSet;
    }

    public Set<ClothesOrderEntity> getStoreLaundryExpertSet() {
        return storeLaundryExpertSet;
    }

    public void setStoreLaundryExpertSet(Set<ClothesOrderEntity> storeLaundryExpertSet) {
        this.storeLaundryExpertSet = storeLaundryExpertSet;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }


    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = System.currentTimeMillis();
    }

}
