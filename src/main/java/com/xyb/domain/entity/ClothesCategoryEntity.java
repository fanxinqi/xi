package com.xyb.domain.entity;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "clothes_category")
public class ClothesCategoryEntity {

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
    @ApiModelProperty(required = true)
    @NotNull(message = "价格不能为空")
    private float price;

    @Column(name = "des")
    private float des;

    @Column(name = "create_time")
    @ApiModelProperty(required = true)
    @NotNull(message = "请添加建立的时间")
    private long createTime;

    @Column(name = "previewUrls")
    private String previewUrls;

    public String getPreviewUrls() {
        return previewUrls;
    }

    public void setPreviewUrls(String previewUrls) {
        this.previewUrls = previewUrls;
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

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public float getDes() {
        return des;
    }

    public void setDes(float des) {
        this.des = des;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }
}
