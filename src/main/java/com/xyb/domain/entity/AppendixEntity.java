package com.xyb.domain.entity;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "appendix_entity")
public class AppendixEntity {
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
    @ManyToOne(cascade = {CascadeType.REFRESH}, fetch = FetchType.EAGER)
    @JoinTable(name = "appendix_file_res", joinColumns = {@JoinColumn(name = "appendix_id")}, inverseJoinColumns = {@JoinColumn(name = "file_res_id")})
    private FileEntity imageEntity;
    @ManyToOne(cascade = {CascadeType.REFRESH}, fetch = FetchType.EAGER)
    @JoinTable(name = "appendix_state", joinColumns = {@JoinColumn(name = "appendix_id")}, inverseJoinColumns = {@JoinColumn(name = "enum_id")})
    private CommonEnumEntity stateEntity;
    @ManyToOne(cascade = {CascadeType.REFRESH}, fetch = FetchType.EAGER)
    @JoinTable(name = "appendix_storage", joinColumns = {@JoinColumn(name = "appendix_id")}, inverseJoinColumns = {@JoinColumn(name = "enum_id")})
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

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public FileEntity getImageEntity() {
        return imageEntity;
    }

    public void setImageEntity(FileEntity imageEntity) {
        this.imageEntity = imageEntity;
    }

    public CommonEnumEntity getStateEntity() {
        return stateEntity;
    }

    public void setStateEntity(CommonEnumEntity stateEntity) {
        this.stateEntity = stateEntity;
    }

    public StorageEntity getStorageEntity() {
        return storageEntity;
    }

    public void setStorageEntity(StorageEntity storageEntity) {
        this.storageEntity = storageEntity;
    }
}
