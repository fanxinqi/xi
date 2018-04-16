package com.xyb.web;

import com.xyb.annotation.LoginRequired;
import com.xyb.common.TokenVerify;
import com.xyb.domain.entity.AccountInfoEntity;
import com.xyb.domain.entity.ClothesOrderEntity;
import com.xyb.domain.entity.RoleInfoEntity;
import com.xyb.domain.entity.StoreEntity;
import com.xyb.exception.AuthorityException;
import com.xyb.exception.MyException;
import com.xyb.exception.RestInfo;
import com.xyb.service.AccountInfoService;
import com.xyb.service.ClothesOrderService;
import com.xyb.service.StoreService;
import com.xyb.utils.JWTUtil;
import org.apache.shiro.authc.AuthenticationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static com.xyb.constants.Constants.ADMIN_ROL;
import static com.xyb.constants.Constants.HEADER_TOKEN;
import static com.xyb.constants.Constants.STORE_ROL;

@RestController
@RequestMapping("/clothes_order")
public class ClothesOrderController {
    @Autowired
    private ClothesOrderService clothesOrderService;
    @Autowired
    private AccountInfoService infoService;
    @Autowired
    private StoreService storeService;
    @Autowired
    private TokenVerify tokenVerify;


    //    @LoginRequired
    @GetMapping("/list")
    public RestInfo getClothesOrderList(@RequestHeader(value = HEADER_TOKEN,required = false) String token, @PageableDefault(page = 0, size = 10, sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable) {
        AccountInfoEntity entity = null;
        if (token != null) {
            entity = tokenVerify.getUserInfoByToken(token);
            if (entity != null) {
                String roleName = tokenVerify.getRoleNameByUser(entity);
                if (ADMIN_ROL.equals(roleName)) {
                    return new RestInfo(clothesOrderService.findAll(pageable));
                } else if (STORE_ROL.equals(roleName)) {
                    if (entity.getStoreId() <= 0) {
                        throw new MyException("您还没有对应的店铺信息");
                    }
                    return new RestInfo(clothesOrderService.findByStoreId(entity.getStoreId(), pageable));
                }
            }
        }
        throw new AuthorityException("您还没有相应的权限");
    }

    @LoginRequired
    @GetMapping(value = "/get_by_id")
    public RestInfo getClothesOrderById(@RequestParam(value = "id", required = true) long id) {
        // 处理"/clotheCategory/{id}"的GET请求，用来获取url中id值的User信息
        // url中的id可通过@PathVariable绑定到函数的参数中
        return new RestInfo<>(clothesOrderService.findById(id));
    }

    @LoginRequired
    @PostMapping(value = "/save")
    public RestInfo saveOrUpdateClothesCategory(@RequestHeader(value =HEADER_TOKEN,required = false) String token, @RequestBody ClothesOrderEntity clothesOrderEntity) {
        return new RestInfo<>(clothesOrderService.save(clothesOrderEntity));
    }

    @PostMapping(value = "/delete_by_id")
    public RestInfo deleteClothesCategory(@RequestParam(value = "id", required = true) long id) {
        clothesOrderService.deleteById(id);
        return new RestInfo<>();
    }

    @PostMapping(value = "/search_by_phone")
    public RestInfo getClothesCategoryByName(@RequestHeader(value =HEADER_TOKEN,required = false) String token, @RequestParam(value = "phone", required = true) String phone, @PageableDefault(page = 0, size = 10, sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable) {
        AccountInfoEntity entity = null;
        if (token != null) {
            entity = tokenVerify.getUserInfoByToken(token);
            if (entity != null) {
                String roleName = tokenVerify.getRoleNameByUser(entity);
                if (ADMIN_ROL.equals(roleName)) {
                    return new RestInfo(clothesOrderService.findByPhone(phone, pageable));
                } else if (STORE_ROL.equals(roleName)) {
                    if (entity.getStoreId() <= 0) {
                        throw new MyException("您还没有对应的店铺信息");
                    }
                    return new RestInfo(clothesOrderService.findByStoreIdAndPhone(entity.getStoreId(), phone, pageable));
                }
            }
        }
        throw new AuthorityException("您还没有相应的权限");
    }

}
