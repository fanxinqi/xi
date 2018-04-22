package com.xyb.domain.entity;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Entity
@Table(name = "clothes_category")
public class ClothesCategoryEntity {
    @Id
    @GeneratedValue
    @Column(name = "id")
    @ApiModelProperty("id")
    private Long id;
    @Column(name = "parent_id")
    @ApiModelProperty(required = true)
    @NotNull(message = "父类id不能为空")
    private long parentId;
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
    @ManyToMany(cascade = {CascadeType.REFRESH}, fetch = FetchType.EAGER)
    @JoinTable(name = "category_parent_children", joinColumns = {@JoinColumn(name = "c_id")}, inverseJoinColumns = {@JoinColumn(name = "p_id")})
    private Set<ClothesCategoryEntity> childrenClothesCategoryEntitySet;

    public FileEntity getImageEntity() {
        return ImageEntity;
    }

    public void setImageEntity(FileEntity imageEntity) {
        ImageEntity = imageEntity;
    }

    @ManyToOne(cascade = {CascadeType.REFRESH}, fetch = FetchType.EAGER)
    @JoinTable(name = "clothes_category_file_res", joinColumns = {@JoinColumn(name = "clothes_category_id")}, inverseJoinColumns = {@JoinColumn(name = "file_res_id")})
    private FileEntity ImageEntity;


    public Set<ClothesCategoryEntity> getChildrenClothesCategoryEntitySet() {
        return childrenClothesCategoryEntitySet;
    }

    public void setChildrenClothesCategoryEntitySet(Set<ClothesCategoryEntity> childrenClothesCategoryEntitySet) {
        this.childrenClothesCategoryEntitySet = childrenClothesCategoryEntitySet;
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
        this.createTime = System.currentTimeMillis();
    }

    public long getParentId() {
        return parentId;
    }

    public void setParentId(long parentId) {
        this.parentId = parentId;
    }
}
