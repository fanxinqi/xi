package com.xyb.domain.entity;

import com.xyb.utils.OrderIdGenerateUtils;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Entity
@Table(name = "clothes_order")
public class ClothesOrderEntity {
    @Id
    @GeneratedValue
    @Column(name = "id")
    @ApiModelProperty("id")
    private Long id;
    @Column(name = "create_time")
    private long createTime;
    @Column(name = "phone")
    @ApiModelProperty(required = true)
    @NotNull(message = "请输入手机号")
    private String phone;
    @Column(name = "storage_num")
    @ApiModelProperty(required = true)
    @NotNull(message = "请输入货架号")
    private int storageNum;
    @ManyToMany(cascade = {CascadeType.REFRESH}, fetch = FetchType.EAGER)
    @JoinTable(name = "order_clothes_category", joinColumns = {@JoinColumn(name = "order_id")}, inverseJoinColumns = {@JoinColumn(name = "clothes_category_id")})
    private Set<ClothesCategoryEntity> categoryEntitySet;
    @ManyToOne(cascade = {CascadeType.REFRESH}, fetch = FetchType.EAGER)
    @JoinTable(name = "order_clothes_state", joinColumns = {@JoinColumn(name = "order_id")}, inverseJoinColumns = {@JoinColumn(name = "clothes_state_id")})
    private DictionaryEntity stateEntity;
    @ManyToOne(cascade = {CascadeType.REFRESH}, fetch = FetchType.EAGER)
    @JoinTable(name = "order_pay_type", joinColumns = {@JoinColumn(name = "order_id")}, inverseJoinColumns = {@JoinColumn(name = "pay_type_id")})
    private CommonEnumEntity paymentEntity;
    @Column(name = "store_id")
    @ApiModelProperty(required = true)
    @NotNull(message = "请选择门店")
    @ManyToMany(cascade = {CascadeType.REFRESH}, fetch = FetchType.EAGER)
    @JoinTable(name = "order_file_res", joinColumns = {@JoinColumn(name = "order_id")}, inverseJoinColumns = {@JoinColumn(name = "file_res_id")})
    private Set<FileEntity> ImageSet;
    private long storeId;
    @Column(name = "order_id")
    @ApiModelProperty(required = true)
    @NotNull(message = "订单号不能为空")
    private String orderId;

    public void setCategoryEntitySet(Set<ClothesCategoryEntity> categoryEntitySet) {
        this.categoryEntitySet = categoryEntitySet;
    }

    public DictionaryEntity getStateEntity() {
        return stateEntity;
    }

    public void setStateEntity(DictionaryEntity stateEntity) {
        this.stateEntity = stateEntity;
    }

    public Set<FileEntity> getImageSet() {
        return ImageSet;
    }

    public void setImageSet(Set<FileEntity> imageSet) {
        ImageSet = imageSet;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = OrderIdGenerateUtils.generateOrderNum();
    }

    public long getStoreId() {
        return storeId;
    }

    public void setStoreId(long storeId) {
        this.storeId = storeId;
    }

    public Set<ClothesCategoryEntity> getCategoryEntitySet() {
        return categoryEntitySet;
    }

    public CommonEnumEntity getPaymentEntity() {
        return paymentEntity;
    }

    public void setPaymentEntity(CommonEnumEntity paymentEntity) {
        this.paymentEntity = paymentEntity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = System.currentTimeMillis();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getStorageNum() {
        return storageNum;
    }

    public void setStorageNum(int storageNum) {
        this.storageNum = storageNum;
    }
}
