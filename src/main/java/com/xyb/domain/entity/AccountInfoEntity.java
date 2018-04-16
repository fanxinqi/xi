package com.xyb.domain.entity;

import io.swagger.annotations.ApiModelProperty;
import org.mapstruct.Mapper;

import javax.management.relation.Role;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Entity
@Table(name = "account_info")
public class AccountInfoEntity {
    @Id
    @GeneratedValue
    @Column(name = "id")
    @ApiModelProperty("id")
    private Long id;
    @Column(name = "name")
    @ApiModelProperty(required = true)
    @NotNull(message = "用户名号不能为空")
    private String name;
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
    private long storeId;

    public void setStoreId(long storeId) {
        this.storeId = storeId;
    }

    public long getRoleId() {
        return roleId;
    }

    public void setRoleId(long roleId) {
        this.roleId = roleId;
    }

    @Column(name = "role_id")
    @ApiModelProperty(required = true)
    @NotNull(message = "所属角色不能为空")
    private long roleId;
    @ManyToMany(cascade = {CascadeType.REFRESH}, fetch = FetchType.EAGER)
    @JoinTable(name = "account_role", joinColumns = {@JoinColumn(name = "account_id")}, inverseJoinColumns = {@JoinColumn(name = "role_id")})
    private Set<RoleInfoEntity> roleInfoEntitySet;
    @ManyToMany(cascade = {CascadeType.REFRESH}, fetch = FetchType.EAGER)
    @JoinTable(name = "account_permission", joinColumns = {@JoinColumn(name = "account_id")}, inverseJoinColumns = {@JoinColumn(name = "permission_id")})
    private Set<PermissionInfoEntity> permissionInfoEntitySet;

    public Set<PermissionInfoEntity> getPermissionInfoEntitySet() {
        return permissionInfoEntitySet;
    }
    public void setPermissionInfoEntitySet(Set<PermissionInfoEntity> permissionInfoEntitySet) {
        this.permissionInfoEntitySet = permissionInfoEntitySet;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<RoleInfoEntity> getRoleInfoEntitySet() {
        return roleInfoEntitySet;
    }

    public void setRoleInfoEntitySet(Set<RoleInfoEntity> roleInfoEntitySet) {
        this.roleInfoEntitySet = roleInfoEntitySet;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public long getStoreId() {
        return storeId;
    }

    public void setStoreId(int storeId) {
        this.storeId = storeId;
    }
}
