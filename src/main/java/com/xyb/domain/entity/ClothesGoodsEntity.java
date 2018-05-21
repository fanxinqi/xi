package com.xyb.domain.entity;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Set;

@Entity
@Table(name = "clothes_goods")
public class ClothesGoodsEntity {
    @Id
    @GeneratedValue
    @Column(name = "id")
    @ApiModelProperty("id")
    private Long id;
    @Column(name = "name")
    @ApiModelProperty(required = true)
    @NotNull(message = "名称不能为空")
    private String name;
    @Column(name = "price")
    private float price;
    @Column(name = "des")
    private String des;
    @Column(name = "create_time")
    private long createTime;

    public FileEntity getImageEntity() {
        return imageEntity;
    }

    public void setImageEntity(FileEntity imageEntity) {
        this.imageEntity = imageEntity;
    }

    @ManyToOne(cascade = {CascadeType.REFRESH}, fetch = FetchType.EAGER)
    @JoinTable(name = "clothes_goods_file_res", joinColumns = {@JoinColumn(name = "clothes_goods_id")}, inverseJoinColumns = {@JoinColumn(name = "file_res_id")})
    private FileEntity imageEntity;

    @ManyToOne(cascade = {CascadeType.REFRESH}, fetch = FetchType.EAGER)
    @JoinTable(name = "goods_clothes_state", joinColumns = {@JoinColumn(name = "clothes_goods_id")}, inverseJoinColumns = {@JoinColumn(name = "enum_id")})
    private CommonEnumEntity stateEntity;
    @ManyToOne(cascade = {CascadeType.REFRESH}, fetch = FetchType.EAGER)
    @JoinTable(name = "goods_clothes_pay_type", joinColumns = {@JoinColumn(name = "clothes_goods_id")}, inverseJoinColumns = {@JoinColumn(name = "enum_id")})
    private CommonEnumEntity paymentEntity;

    @ManyToOne(cascade = {CascadeType.REFRESH}, fetch = FetchType.EAGER)
    @JoinTable(name = "goods_clothes_storage_state", joinColumns = {@JoinColumn(name = "clothes_goods_id")}, inverseJoinColumns = {@JoinColumn(name = "enum_id")})
    private StorageEntity storageEntity;

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

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public CommonEnumEntity getStateEntity() {
        return stateEntity;
    }

    public void setStateEntity(CommonEnumEntity stateEntity) {
        this.stateEntity = stateEntity;
    }

    public CommonEnumEntity getPaymentEntity() {
        return paymentEntity;
    }

    public void setPaymentEntity(CommonEnumEntity paymentEntity) {
        this.paymentEntity = paymentEntity;
    }

    public StorageEntity getStorageEntity() {
        return storageEntity;
    }

    public void setStorageEntity(StorageEntity storageEntity) {
        this.storageEntity = storageEntity;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = System.currentTimeMillis();
    }

}
