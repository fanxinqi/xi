package com.xyb.domain.entity;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import com.xyb.domain.entity.MemberCategoryEntity;
//字典模型

@Entity
@Table(name = "dictionary")
public class DictionaryEntity {

    @Id
    @GeneratedValue
    @Column(name = "id")
    @ApiModelProperty("id")
    private Long id;

    @Column(name = "parentId")
    @ApiModelProperty("parentId")
    private Long parentId;

    @Column(name = "name")
    @ApiModelProperty(value = "姓名", allowEmptyValue = false, readOnly = false)
    @NotNull(message = "名字不能为空")
    private String name;


    @Column(name = "des")
    @ApiModelProperty("描述")
    private String des;


    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
