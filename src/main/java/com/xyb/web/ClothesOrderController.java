package com.xyb.web;

import com.xyb.annotation.LoginRequired;
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

    @LoginRequired
    @GetMapping("/list")
    public RestInfo getClothesCategoryList(@RequestHeader(HEADER_TOKEN) String token) {
        AccountInfoEntity entity = null;
        if (token != null) {
            entity = getUserInfoByToken(token);
            if (entity != null && entity.getRoleInfoEntitySet() != null) {
                for (RoleInfoEntity roleInfoEntity : entity.getRoleInfoEntitySet()) {
                    if (ADMIN_ROL.equals(roleInfoEntity.getName())) {
                        return new RestInfo(clothesOrderService.findAll());
                    } else if (STORE_ROL.equals(roleInfoEntity.getName())) {
                        if (entity.getStoreId() <= 0) {
                            throw new MyException("您还没有对应的店铺信息");
                        }
                        Optional<StoreEntity> optionalStoreEntity = storeService.findById(entity.getStoreId());
                        if (optionalStoreEntity != null) {
                            StoreEntity storeEntity = optionalStoreEntity.get();
                            return new RestInfo(storeEntity.getStoreOrderSet());
                        }
                    }
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
    public RestInfo saveOrUpdateClothesCategory(@RequestHeader(HEADER_TOKEN) String token,@RequestBody ClothesOrderEntity clothesOrderEntity) {
        AccountInfoEntity entity = null;
        if (token != null) {
            entity = getUserInfoByToken(token);
            if (entity != null && entity.getStoreId() >0) {
                for (RoleInfoEntity roleInfoEntity : entity.getRoleInfoEntitySet()) {
                    if (ADMIN_ROL.equals(roleInfoEntity.getName())) {
                        return new RestInfo(clothesOrderService.findAll());
                    } else if (STORE_ROL.equals(roleInfoEntity.getName())) {
                        if (entity.getStoreId() <= 0) {
                            throw new MyException("您还没有对应的店铺信息");
                        }
                        Optional<StoreEntity> optionalStoreEntity = storeService.findById(entity.getStoreId());
                        if (optionalStoreEntity != null) {
                            StoreEntity storeEntity = optionalStoreEntity.get();
                            return new RestInfo(storeEntity.getStoreOrderSet());
                        }
                    }
                }
            }
        }
        return new RestInfo<>(clothesOrderService.save(clothesOrderEntity));
    }

    @PostMapping(value = "/delete_by_id")
    public RestInfo deleteClothesCategory(@RequestParam(value = "id", required = true) long id) {
        clothesOrderService.deleteById(id);
        return new RestInfo<>();
    }

    @PostMapping(value = "/search_by_phone")
    public RestInfo getClothesCategoryByName(@RequestParam(value = "phone", required = true) String phone) {
        List<ClothesOrderEntity> list = clothesOrderService.findByPhone(phone);
        return new RestInfo<>(list);
    }

    private AccountInfoEntity getUserInfoByToken(String token) {
        // 解密获得username，用于和数据库进行对比
        String username = JWTUtil.getUsername(token);
        if (username == null) {
            throw new AuthenticationException("token invalid");
        }
        AccountInfoEntity userBean = infoService.findByName(username);
        return userBean;
    }
}
