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
    private long createTime;
    @ManyToMany(cascade = {CascadeType.REFRESH}, fetch = FetchType.EAGER)
    @JoinTable(name = "store_laundry_expert", joinColumns = {@JoinColumn(name = "store_id")}, inverseJoinColumns = {@JoinColumn(name = "laundry_expert_id")})
    private Set<ClothesOrderEntity> storeLaundryExpertSet;
    @Column(name = "des")
    private String des;
    @Column(name = "storage_num")
    private int storageNum;
    @ManyToOne(cascade = {CascadeType.REFRESH}, fetch = FetchType.EAGER)
    @JoinTable(name = "store_file_res", joinColumns = {@JoinColumn(name = "store_id")}, inverseJoinColumns = {@JoinColumn(name = "file_res_id")})
    private FileEntity imageEntity;
    public Set<ClothesOrderEntity> getStoreLaundryExpertSet() {
        return storeLaundryExpertSet;
    }
    public int getStorageNum() {
        return storageNum;
    }
    public void setStorageNum(int storageNum) {
        this.storageNum = storageNum;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public FileEntity getImageEntity() {
        return imageEntity;
    }

    public void setImageEntity(FileEntity imageEntity) {
        this.imageEntity = imageEntity;
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


    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = System.currentTimeMillis();
    }

}
