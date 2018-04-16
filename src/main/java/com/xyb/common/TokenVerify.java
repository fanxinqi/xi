package com.xyb.common;

import com.xyb.domain.entity.AccountInfoEntity;
import com.xyb.domain.entity.RoleInfoEntity;
import com.xyb.service.AccountInfoService;
import com.xyb.service.RoleService;
import com.xyb.utils.JWTUtil;
import org.apache.shiro.authc.AuthenticationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class TokenVerify {
    @Autowired
    private AccountInfoService infoService;
    @Autowired
    private RoleService roleService;

    public AccountInfoEntity getUserInfoByToken(String token) {
        // 解密获得username，用于和数据库进行对比
        String username = JWTUtil.getUsername(token);
        if (username == null) {
            throw new AuthenticationException("token invalid");
        }
        AccountInfoEntity userBean = infoService.findByName(username);
        return userBean;
    }

    public String getRoleNameByUser(AccountInfoEntity accountInfoEntity) {
        if (accountInfoEntity == null) {
            return null;
        }
        if (accountInfoEntity.getRoleId() > 0) {
            Optional<RoleInfoEntity> roleInfoEntityOptional = roleService.findById(accountInfoEntity.getRoleId());
            RoleInfoEntity roleInfoEntity = roleInfoEntityOptional.get();
            return roleInfoEntity.getName();
        }
        return null;

    }
}
