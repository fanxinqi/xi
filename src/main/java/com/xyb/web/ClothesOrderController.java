package com.xyb.web;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.xyb.annotation.LoginRequired;
import com.xyb.common.TokenVerify;
import com.xyb.domain.entity.*;
import com.xyb.exception.AuthorityException;
import com.xyb.exception.MyException;
import com.xyb.exception.RestInfo;
import com.xyb.service.AccountInfoService;
import com.xyb.service.ClothesOrderService;
import com.xyb.service.CommonEnumService;
import com.xyb.service.StoreService;
import com.xyb.utils.JWTUtil;
import com.xyb.utils.OrderIdGenerateUtils;
import com.xyb.utils.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static com.xyb.constants.Constants.*;

@RestController
@RequestMapping("/clothesOrder")
public class ClothesOrderController {
    @Autowired
    private ClothesOrderService clothesOrderService;
    @Autowired
    private TokenVerify tokenVerify;

    @Autowired
    private CommonEnumService paymentService;

    @LoginRequired
    @GetMapping("/list")
    public RestInfo getClothesOrderList(@RequestHeader(value = HEADER_TOKEN) String token, @PageableDefault(page = 0, size = 10, sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable) {
        AccountInfoEntity user = null;
        user = tokenVerify.getUserInfoByToken(token);
        if (user != null) {
            String roleName = tokenVerify.getRoleNameByUser(user);
            Page<ClothesOrderEntity> page = null;
            if (ADMIN_ROL.equals(roleName)) {
                page = clothesOrderService.findAll(pageable);
            } else if (STORE_ROL.equals(roleName)) {
                if (user.getStoreId() <= 0) {
                    throw new MyException("您还没有对应的店铺信息");
                }
                page = clothesOrderService.findByStoreId(user.getStoreId(), pageable);
            }
            if (page != null) {
                //定义JSON
                JSONObject jObject = new JSONObject();
                try {
                    jObject.put("orderList", page);
                    jObject.put("paymentList", paymentService.findByType(PAYMENT_TYPE));
                    jObject.put("stateList", paymentService.findByType(ORDER_STATE_TYPE));
                    return new RestInfo(jObject);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }
        throw new AuthorityException("您还没有相应的权限");
    }

    @LoginRequired
    @GetMapping(value = "/getById")
    public RestInfo getClothesOrderById(@RequestHeader(value = HEADER_TOKEN) String token, @RequestParam(value = "id", required = true) long id) {
        if (id <= 0) {
            throw new MyException("请传入正确的Id");
        }
        AccountInfoEntity user = null;
        user = tokenVerify.getUserInfoByToken(token);
        if (user != null) {
            ClothesOrderEntity clothesOrderEntity = null;
            String roleName = tokenVerify.getRoleNameByUser(user);
            if (ADMIN_ROL.equals(roleName)) {
                Optional<ClothesOrderEntity> clothesOrderEntityOptional = clothesOrderService.findById(id);
                clothesOrderEntity = clothesOrderEntityOptional == null ? null : clothesOrderEntityOptional.get();
            } else if (STORE_ROL.equals(roleName)) {
                if (user.getStoreId() <= 0) {
                    throw new MyException("您还没有对应的店铺信息");
                }
                clothesOrderEntity = clothesOrderService.findByStoreIdAndId(user.getStoreId(), id);
            }
            return new RestInfo(clothesOrderEntity);
        }
        throw new AuthorityException("您还没有相应的权限");
    }

    @LoginRequired
    @PostMapping(value = "/save")
    public RestInfo saveOrUpdateClothesCategory(@RequestHeader(value = HEADER_TOKEN) String token, @RequestBody ClothesOrderEntity orderEntity) {
        if (orderEntity == null || orderEntity.getCategoryEntitySet() == null || orderEntity.getCategoryEntitySet().size() <= 0) {
            throw new MyException("请传入相应的实体");
        }
        AccountInfoEntity user = null;
        user = tokenVerify.getUserInfoByToken(token);
        String roleName = tokenVerify.getRoleNameByUser(user);
        if (ADMIN_ROL.equals(roleName)) {
            if (orderEntity.getStoreId() <= 0) {
                throw new MyException("请选择您所加订单的门店");
            } else {
                orderEntity.setStorageNum(1);
                orderEntity.setOrderId(OrderIdGenerateUtils.generateOrderNum());
                orderEntity.setCreateTime(System.currentTimeMillis());
                orderEntity.setTotalNum(orderEntity.getCategoryEntitySet().size());
                float sumPrice = 0;
                for (ClothesCategoryEntity entity : orderEntity.getCategoryEntitySet()) {
                    sumPrice += entity.getPrice();
                }
                orderEntity.setTotalPrice(sumPrice);
                return new RestInfo(clothesOrderService.save(orderEntity));
            }
        } else if (STORE_ROL.equals(roleName)) {
            if (user.getStoreId() > 0) {
                orderEntity.setStoreId(user.getStoreId());
                orderEntity.setStorageNum(1);
                orderEntity.setOrderId(OrderIdGenerateUtils.generateOrderNum());
                orderEntity.setCreateTime(System.currentTimeMillis());
                orderEntity.setTotalNum(orderEntity.getCategoryEntitySet().size());
                float sumPrice = 0;
                for (ClothesCategoryEntity entity : orderEntity.getCategoryEntitySet()) {
                    sumPrice += entity.getPrice();
                }
                orderEntity.setTotalPrice(sumPrice);
                return new RestInfo(clothesOrderService.save(orderEntity));
            }
        }
        throw new MyException("操作失败");
    }

    @LoginRequired
    @PostMapping(value = "/update")
    public RestInfo updateOrder(@RequestHeader(value = HEADER_TOKEN) String token, @RequestBody ClothesOrderEntity orderEntity) {
        if (orderEntity == null || orderEntity.getId() <= 0) {
            throw new MyException("请传入更新的内容");
        }
        AccountInfoEntity user = null;
        user = tokenVerify.getUserInfoByToken(token);
        if (user != null) {
            String roleName = tokenVerify.getRoleNameByUser(user);
            ClothesOrderEntity clothesOrderEntity = null;
            if (ADMIN_ROL.equals(roleName)) {
                Optional<ClothesOrderEntity> orderEntityOptional = clothesOrderService.findById(orderEntity.getId());
                if (orderEntityOptional != null) {
                    clothesOrderEntity = orderEntityOptional.get();
                } else {
                    throw new MyException("您还没有该订单，还不能进行编辑");
                }

            } else if (STORE_ROL.equals(roleName)) {
                if (user.getStoreId() > 0) {
                    if (orderEntity.getId() > 0) {
                        clothesOrderEntity = clothesOrderService.findByStoreIdAndId(user.getStoreId(), orderEntity.getId());
                    }
                }
            }
            if (clothesOrderEntity != null) {
                return new RestInfo(clothesOrderService.save(updateOrder(clothesOrderEntity, orderEntity)));
            } else {
                throw new MyException("您还没有该订单权限，还不能进行编辑");
            }
        }

        throw new MyException("操作失败");
    }

    @LoginRequired
    @PostMapping(value = "/deleteById")
    public RestInfo deleteClothesCategory(@RequestHeader(value = HEADER_TOKEN) String token, @RequestParam(value = "id", required = true) long id) {
        if (id <= 0) {
            throw new MyException("请传入正确的Id");
        }
        AccountInfoEntity user = null;
        user = tokenVerify.getUserInfoByToken(token);
        if (user != null) {
            String roleName = tokenVerify.getRoleNameByUser(user);
            ClothesOrderEntity clothesOrderEntity = null;
            if (ADMIN_ROL.equals(roleName)) {
                Optional<ClothesOrderEntity> clothesOrderEntityOptional = clothesOrderService.findById(id);
                clothesOrderEntity = clothesOrderEntityOptional == null ? null : clothesOrderEntityOptional.get();
            } else if (STORE_ROL.equals(roleName)) {
                if (user.getStoreId() <= 0) {
                    throw new MyException("您还没有对应的店铺信息");
                }
                clothesOrderEntity = clothesOrderService.findByStoreIdAndId(user.getStoreId(), id);
            }
            if (clothesOrderEntity != null) {
                clothesOrderService.delete(clothesOrderEntity);
                return new RestInfo<>();
            } else {
                throw new MyException("您还没有相应的数据");
            }
        }
        throw new AuthorityException("您还没有相应的权限");

    }

    @LoginRequired
    @GetMapping(value = "/searchByName")
    public RestInfo getClothesCategoryByName(@RequestHeader(value = HEADER_TOKEN) String
                                                     token, @RequestParam(value = "phone", required = true) String
                                                     phone, @PageableDefault(page = 0, size = 10, sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable) {
        AccountInfoEntity user = null;
        if (token != null) {
            user = tokenVerify.getUserInfoByToken(token);
            if (user != null) {
                Page<ClothesOrderEntity> page = null;
                String roleName = tokenVerify.getRoleNameByUser(user);
                if (ADMIN_ROL.equals(roleName)) {
                    page = clothesOrderService.findByPhone(phone, pageable);
                } else if (STORE_ROL.equals(roleName)) {
                    if (user.getStoreId() <= 0) {
                        throw new MyException("您还没有对应的店铺信息");
                    }
                    page = clothesOrderService.findByStoreIdAndPhone(user.getStoreId(), phone, pageable);
                }
                return new RestInfo(page);
            }
        }
        throw new AuthorityException("您还没有相应的权限");
    }

    private ClothesOrderEntity updateOrder(ClothesOrderEntity clothesOrderEntity, ClothesOrderEntity requestClothesOrder) {

        if (clothesOrderEntity == null) {
            return null;
        }
        if (!StringUtils.isBlank(requestClothesOrder.getPhone())) {
            clothesOrderEntity.setPhone(requestClothesOrder.getPhone());
        }
        if (requestClothesOrder.getStorageNum() > 0) {
            clothesOrderEntity.setStorageNum(requestClothesOrder.getStorageNum());
        }
        if (requestClothesOrder.getCategoryEntitySet().size() > 0) {
            clothesOrderEntity.setCategoryEntitySet(requestClothesOrder.getCategoryEntitySet());
        }
        if (requestClothesOrder.getStoreId() > 0) {
            clothesOrderEntity.setStoreId(requestClothesOrder.getStoreId());
        }
        if (requestClothesOrder.getImageSet().size() > 0) {
            clothesOrderEntity.setImageSet(requestClothesOrder.getImageSet());
        }
        if (requestClothesOrder.getPaymentEntity() != null) {
            clothesOrderEntity.setPaymentEntity(requestClothesOrder.getPaymentEntity());
        }
        if (requestClothesOrder.getStateEntity() != null) {
            clothesOrderEntity.setStateEntity(requestClothesOrder.getStateEntity());
        }
        if (requestClothesOrder.getTotalNum() > 0) {
            clothesOrderEntity.setTotalNum(requestClothesOrder.getTotalNum());
        }
        clothesOrderEntity.setTotalPrice(requestClothesOrder.getTotalPrice());
        return clothesOrderEntity;
    }

}
