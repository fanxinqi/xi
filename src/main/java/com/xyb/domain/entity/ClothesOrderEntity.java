package com.xyb.domain.entity;

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
    @Column(name = "price")
    @ApiModelProperty(required = true)
    @NotNull(message = "价格不能为空")
    private float price;
    @Column(name = "create_time")
    @ApiModelProperty(required = true)
    @NotNull(message = "请添加建立的时间")
    private long createTime;
    @Column(name = "preview_urls")
    @ApiModelProperty(required = true)
    @NotNull(message = "请上传图片")
    private String previewUrls;
    @Column(name = "phone")
    @ApiModelProperty(required = true)
    @NotNull(message = "请输入手机号")
    private String phone;
    @Column(name = "storage_num")
    @ApiModelProperty(required = true)
    @NotNull(message = "请输入货架号")
    private String storageNum;
    @ManyToMany(cascade = {CascadeType.REFRESH}, fetch = FetchType.EAGER)
    @JoinTable(name = "order_clothes_category", joinColumns = {@JoinColumn(name = "order_id")}, inverseJoinColumns = {@JoinColumn(name = "clothes_category_id")})
    private Set<ClothesCategoryEntity> clothesCategoryEntitySet;
    @ManyToMany(cascade = {CascadeType.REFRESH}, fetch = FetchType.EAGER)
    @JoinTable(name = "order_clothes_state", joinColumns = {@JoinColumn(name = "order_id")}, inverseJoinColumns = {@JoinColumn(name = "clothes_state_id")})
    private Set<DictionaryEntity> orderClothesStateEntitySet;
    @ApiModelProperty(required = true)
    @NotNull(message = "请选择支付方式")
    @Column(name = "payment_entity_set")
    @ManyToMany(cascade = {CascadeType.REFRESH}, fetch = FetchType.EAGER)
    @JoinTable(name = "order_pay_type", joinColumns = {@JoinColumn(name = "order_id")}, inverseJoinColumns = {@JoinColumn(name = "pay_type_id")})
    private Set<PaymentEntity> paymentEntitySet;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public String getPreviewUrls() {
        return previewUrls;
    }

    public void setPreviewUrls(String previewUrls) {
        this.previewUrls = previewUrls;
    }

    public Set<ClothesCategoryEntity> getClothesCategoryEntitySet() {
        return clothesCategoryEntitySet;
    }

    public void setClothesCategoryEntitySet(Set<ClothesCategoryEntity> clothesCategoryEntitySet) {
        this.clothesCategoryEntitySet = clothesCategoryEntitySet;
    }

    public Set<DictionaryEntity> getOrderClothesStateEntitySet() {
        return orderClothesStateEntitySet;
    }

    public void setOrderClothesStateEntitySet(Set<DictionaryEntity> orderClothesStateEntitySet) {
        this.orderClothesStateEntitySet = orderClothesStateEntitySet;
    }

    public Set<PaymentEntity> getPaymentEntitySet() {
        return paymentEntitySet;
    }

    public void setPaymentEntitySet(Set<PaymentEntity> paymentEntitySet) {
        this.paymentEntitySet = paymentEntitySet;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getStorageNum() {
        return storageNum;
    }

    public void setStorageNum(String storageNum) {
        this.storageNum = storageNum;
    }
}
