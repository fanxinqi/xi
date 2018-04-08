package com.xyb.domain.entity;

import io.swagger.annotations.ApiModelProperty;
import org.mapstruct.Mapper;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
@Entity
@Table(name = "account_info")
public class AccountInfoEntity {
    @Id
    @GeneratedValue
    @Column(name = "id")
    @ApiModelProperty("id")
    private Long id;
    @Column(name = "phone")
    @ApiModelProperty(required = true)
    @NotNull(message = "手机号不能为空")
    private String phone;
    @Column(name = "pass_word")
    @ApiModelProperty(required = true)
    @NotNull(message = "密码不能为空")
    private String passWord;
    @Column(name = "token")
    private String token;
    @Column(name = "nick_name")
    private String nickName;
    @Column(name = "head_url")
    private String headUrl;
    @Column(name = "age")
    private int age;
    @Column(name = "birthday")
    private long birthday;
    @Column(name = "store_id")
    @ApiModelProperty(required = true)
    @NotNull(message = "所属分店不能为空")
    private int storeId;
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getHeadUrl() {
        return headUrl;
    }

    public void setHeadUrl(String headUrl) {
        this.headUrl = headUrl;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public long getBirthday() {
        return birthday;
    }

    public void setBirthday(long birthday) {
        this.birthday = birthday;
    }

    public int getStoreId() {
        return storeId;
    }

    public void setStoreId(int storeId) {
        this.storeId = storeId;
    }
}
