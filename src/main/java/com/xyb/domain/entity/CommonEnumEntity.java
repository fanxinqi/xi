package com.xyb.domain.entity;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Entity
@Table(name = "common_emum")
public class CommonEnumEntity {
    @Id
    @GeneratedValue
    @Column(name = "id")
    @ApiModelProperty("id")
    private long id;
    @Column(name = "name")
    private String name;
    @Column(name = "type")
    @ApiModelProperty(required = true)
    @NotNull(message = "请选择type")
    private int type;
    @Column(name = "create_time")
    private long createTime;
    public FileEntity getImageEntity() {
        return imageEntity;
    }
    public void setImageEntity(FileEntity imageEntity) {
        this.imageEntity = imageEntity;
    }
    @ManyToOne(cascade = {CascadeType.REFRESH}, fetch = FetchType.EAGER)
    @JoinTable(name = "payment_category_file_res", joinColumns = {@JoinColumn(name = "payment_category_id")}, inverseJoinColumns = {@JoinColumn(name = "file_res_id")})
    private FileEntity imageEntity;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = System.currentTimeMillis();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
