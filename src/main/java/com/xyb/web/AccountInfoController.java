package com.xyb.web;

import com.xyb.domain.entity.AccountInfoEntity;
import com.xyb.exception.RestInfo;
import com.xyb.service.AccountInfoService;
import com.xyb.utils.TokenUtils;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/account_info")
public class AccountInfoController {
    @Autowired
    private AccountInfoService infoService;
    @ApiOperation(value="登录接口", notes="post请求")
    @PostMapping(value = "/login")
    public RestInfo loginWithPassword(@RequestParam(value = "user_name", required = true) String user_name, @RequestParam(value = "password", required = true) String password) {
        AccountInfoEntity entity = new AccountInfoEntity();
        RestInfo restInfo = new RestInfo<String>();
        AccountInfoEntity dbEntity = infoService.findByName(user_name);
        if (dbEntity != null) {
            if (dbEntity.getPassWord().equals(password)) {
                dbEntity.setToken(TokenUtils.createJwtToken(user_name));
                infoService.save(dbEntity);
                restInfo = new RestInfo(dbEntity);
                restInfo.setMessage("登录成功");
            } else {
                restInfo.setMessage("用户名或者密码错误");
                restInfo.setCode(RestInfo.ERROR);
            }

        } else {
            restInfo.setMessage("用户名或者密码错误");
            restInfo.setCode(RestInfo.ERROR);
        }
        return restInfo;
    }
}
