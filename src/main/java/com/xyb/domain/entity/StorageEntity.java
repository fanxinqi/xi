package com.xyb.domain.entity;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
@Entity
@Table(name = "storage")
public class StorageEntity {
    @Id
    @GeneratedValue
    @Column(name = "id")
    @ApiModelProperty("id")
    private Long id;
    @Column(name = "name")
    @ApiModelProperty(required = true)
    @NotNull(message = "名称不能为空")
    private String name;
    @ManyToOne(cascade = {CascadeType.REFRESH}, fetch = FetchType.EAGER)
    @JoinTable(name = "storage_state", joinColumns = {@JoinColumn(name = "storage_id")}, inverseJoinColumns = {@JoinColumn(name = "enum_id")})
    private CommonEnumEntity stateEntity;
    @Column(name = "store_id")
    @ApiModelProperty(required = true)
    @NotNull(message = "所属分店不能为空")
    private long storeId;
    @Column(name = "usable_state")
    @ApiModelProperty(required = true)
    @NotNull(message = "是否可用不能为空")
    private int usableState;

    public int getUsableState() {
        return usableState;
    }

    public void setUsableState(int usableState) {
        this.usableState = usableState;
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

    public CommonEnumEntity getStateEntity() {
        return stateEntity;
    }

    public void setStateEntity(CommonEnumEntity stateEntity) {
        this.stateEntity = stateEntity;
    }

    public long getStoreId() {
        return storeId;
    }

    public void setStoreId(long storeId) {
        this.storeId = storeId;
    }


}
