package com.xyb.web;

import com.xyb.annotation.LoginRequired;
import com.xyb.common.TokenVerify;
import com.xyb.domain.entity.AccountInfoEntity;
import com.xyb.domain.entity.ClothesCategoryEntity;
import com.xyb.domain.entity.PaymentEntity;
import com.xyb.exception.AuthorityException;
import com.xyb.exception.MyException;
import com.xyb.exception.RestInfo;
import com.xyb.service.ChothesCategoryService;
import com.xyb.service.PaymentService;
import com.xyb.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import static com.xyb.constants.Constants.*;

/**
 * 演示user在内存中的操作
 *
 * @Author fanxinqi
 * @Date 2018/3/20
 */
@RestController
@RequestMapping("/payment")
public class PaymentController {
    @Autowired
    private PaymentService paymentService;
    @Autowired
    private TokenVerify tokenVerify;


    @LoginRequired
    @GetMapping("/list")
    public RestInfo<PaymentEntity> getPaymentList() {
        List<PaymentEntity> list = paymentService.findAll();
        return new RestInfo(list);
    }

    @LoginRequired
    @GetMapping(value = "/getById")
    public RestInfo<PaymentEntity> getPaymentById(@RequestParam(value = "id", required = true) long id) {
        Optional<PaymentEntity> optionalPaymentEntity = paymentService.findById(id);
        PaymentEntity paymentEntity = null;
        if (optionalPaymentEntity.isPresent()) {
            paymentEntity = optionalPaymentEntity.get();
        }
        return new RestInfo(paymentEntity);
    }

    @LoginRequired
    @GetMapping(value = "/searchByName")
    public RestInfo<PaymentEntity> getPaymentByName(@RequestParam(value = "name", required = true) String name) {
        if (StringUtils.isBlank(name)) {
            throw new MyException("请输入要搜索的名字");
        }
        PaymentEntity paymentEntity = paymentService.findByName(name);
        return new RestInfo(paymentEntity);
    }

    @LoginRequired
    @PostMapping(value = "/save")
    public RestInfo saveOrUpdatePayment(@RequestHeader(value = HEADER_TOKEN) String token, @RequestBody PaymentEntity paymentEntity) {
        if (paymentEntity == null) {
            throw new MyException("请传入更新的内容");
        }
        AccountInfoEntity user = null;
        user = tokenVerify.getUserInfoByToken(token);
        if (user != null) {
            String roleName = tokenVerify.getRoleNameByUser(user);
            if (ADMIN_ROL.equals(roleName)) {
               return new RestInfo(paymentService.save(paymentEntity));
            } else {
                throw new AuthorityException("还没有相应的权限");
            }
        }
        throw new MyException("操作失败");
    }

    @PostMapping(value = "/update")
    public RestInfo update(@RequestHeader(value = HEADER_TOKEN) String token, @RequestBody PaymentEntity requestPaymentEntity) {
        if (requestPaymentEntity == null || requestPaymentEntity.getId() <= 0) {
            throw new MyException("请传入更新的内容");
        }
        AccountInfoEntity user = null;
        user = tokenVerify.getUserInfoByToken(token);
        if (user != null) {
            String roleName = tokenVerify.getRoleNameByUser(user);
            PaymentEntity paymentEntity = null;
            if (ADMIN_ROL.equals(roleName)) {
                Optional<PaymentEntity> paymentEntityOptional = paymentService.findById(requestPaymentEntity.getId());
                if (paymentEntityOptional != null) {
                    paymentEntity = paymentEntityOptional.get();
                } else {
                    throw new MyException("您还没有该分类，还不能进行编辑");
                }
            } else if (STORE_ROL.equals(roleName)) {
                throw new AuthorityException("您还没有相应的权限");
            }
            if (paymentEntity != null) {
                paymentService.save(updateClothesCategory(paymentEntity, requestPaymentEntity));
            } else {
                throw new AuthorityException("您还没有相应的权限");
            }
        }
        throw new AuthorityException("您还没有相应的权限");
    }

    @LoginRequired
    @PostMapping(value = "/deleteById")
    public RestInfo deletePayment(@RequestHeader(value = HEADER_TOKEN) String token, @RequestParam(value = "id", required = true) long id) {
        AccountInfoEntity user = null;
        user = tokenVerify.getUserInfoByToken(token);
        if (user != null) {
            PaymentEntity paymentEntity = null;
            String roleName = tokenVerify.getRoleNameByUser(user);
            if (ADMIN_ROL.equals(roleName)) {
                Optional<PaymentEntity> paymentEntityOptional = paymentService.findById(id);
                paymentEntity = paymentEntityOptional == null ? null : paymentEntityOptional.get();
                try {
                    paymentService.delete(paymentEntity);
                   return new RestInfo();
                } catch (Exception e) {
                    e.printStackTrace();
                    throw new MyException("操作失败");
                }
            } else if (STORE_ROL.equals(roleName)) {
                throw new AuthorityException("您还没有相应的权限");
            }
        }
        throw new AuthorityException("您还没有相应的权限");
    }


    private PaymentEntity updateClothesCategory(PaymentEntity paymentEntity, PaymentEntity requestPaymentEntity) {
        if (!StringUtils.isBlank(requestPaymentEntity.getName())) {
            paymentEntity.setName(requestPaymentEntity.getName());
        }
        if (requestPaymentEntity.getImageEntity()!=null) {
            paymentEntity.setImageEntity(requestPaymentEntity.getImageEntity());
        }

        return paymentEntity;
    }
}
