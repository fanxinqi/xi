package com.xyb.web;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.xyb.annotation.LoginRequired;
import com.xyb.domain.entity.AccountInfoEntity;
import com.xyb.domain.entity.ClothesOrderEntity;
import com.xyb.domain.entity.RoleInfoEntity;
import com.xyb.domain.entity.StoreEntity;
import com.xyb.exception.RestInfo;
import com.xyb.service.AccountInfoService;
import com.xyb.service.RoleService;
import com.xyb.service.StoreService;
import com.xyb.utils.JWTUtil;
import com.xyb.utils.StringUtils;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/accountInfo")
public class AccountInfoController {
    @Autowired
    private AccountInfoService infoService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private StoreService storeService;
    @ApiOperation(value = "登录接口", notes = "post请求")
    @PostMapping(value = "/login")
    public RestInfo loginWithPassword(@RequestParam(value = "userName") String userName, @RequestParam(value = "password") String password) {
        RestInfo restInfo = new RestInfo<String>();
        AccountInfoEntity dbEntity = infoService.findByName(userName);
        if (dbEntity != null) {
            if (dbEntity.getPassWord().equals(password)) {
                dbEntity.setToken(JWTUtil.sign(userName, password));
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

    @ApiOperation(value = "账号列表", notes = "账号列表")
    @GetMapping(value = "/list")
    public RestInfo list(@RequestParam(value = "userName",required = false) String userName, @PageableDefault(page = 0, size = 10, sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable) {
        //定义JSON
        JSONObject resultJson = new JSONObject();
        try{
            if(pageable.getPageNumber()==0){
                resultJson.put("roleList",roleService.findAll());
                resultJson.put("storeList",storeService.findAll());
            }
            if (userName != null) {
                resultJson.put("userList",infoService.findByName(userName, pageable));
            } else {
               resultJson.put("userList",infoService.findAll(pageable));
            }
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
        return new RestInfo(resultJson);
    }
    @LoginRequired
    @PostMapping(value = "/save")
    public RestInfo saveAccountInfo(@RequestParam(value = "name") String name,
                                    @RequestParam(value = "passWord") String passWord,
                                    @RequestParam(value = "roleId",required = false,defaultValue = "0") long roleId,
                                    @RequestParam(value = "storeId",required = false,defaultValue = "0") long storeId,
                                    @RequestParam(value = "id",required = false, defaultValue = "0") long id
                                    ) {

        AccountInfoEntity targetSaveModel = new AccountInfoEntity();

        if ( id > 0) {
            targetSaveModel.setId(id);
        }
        if (!StringUtils.isBlank(name)) {
            targetSaveModel.setName(name);
        }
        if (!StringUtils.isBlank(passWord)) {
            targetSaveModel.setPassWord(passWord);
        }
        if (storeId > 0) {
            targetSaveModel.setStoreId(storeId);
        }
        if (roleId > 0) {
            targetSaveModel.setRoleId(roleId);
        }
        return new RestInfo(infoService.save(targetSaveModel));
    }

}
