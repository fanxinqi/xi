package com.xyb.domain.entity;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "member_Category")
public class MemberCategoryEntity  {

    @Id
    @GeneratedValue
    @Column(name = "id")
    @ApiModelProperty("id")
    private Long id;

    @Column(name = "name")
    @ApiModelProperty(required = true)
    @NotNull(message = "类型名字")
    private String name;

    @Column(name = "des")
    @NotNull(message = "描述")
    private String des;

    @Column(name = "active_time")
    @ApiModelProperty(required = true)
    @NotNull(message = "请生成会员到期时间")
    private long activeTime;

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

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public long getActiveTime() {
        return activeTime;
    }

    public void setActiveTime(long activeTime) {
        this.activeTime = activeTime;
    }
}
