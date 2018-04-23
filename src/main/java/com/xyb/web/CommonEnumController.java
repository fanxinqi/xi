package com.xyb.web;

import com.xyb.annotation.LoginRequired;
import com.xyb.common.TokenVerify;
import com.xyb.domain.entity.AccountInfoEntity;
import com.xyb.domain.entity.CommonEnumEntity;
import com.xyb.exception.AuthorityException;
import com.xyb.exception.MyException;
import com.xyb.exception.RestInfo;
import com.xyb.service.CommonEnumService;
import com.xyb.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static com.xyb.constants.Constants.*;

/**
 * 演示user在内存中的操作
 *
 * @Author fanxinqi
 * @Date 2018/3/20
 */
@RestController
@RequestMapping("/enum")
public class CommonEnumController {
    @Autowired
    private CommonEnumService paymentService;
    @Autowired
    private TokenVerify tokenVerify;


    @LoginRequired
    @GetMapping("/list")
    public RestInfo<CommonEnumEntity> getPaymentList() {
        List<CommonEnumEntity> list = paymentService.findAll();
        return new RestInfo(list);
    }
    @LoginRequired
    @GetMapping("/listByType")
    public RestInfo<CommonEnumEntity> getPaymentList(int type) {
        List<CommonEnumEntity> list = paymentService.findByType(type);
        return new RestInfo(list);
    }

    @LoginRequired
    @GetMapping(value = "/getById")
    public RestInfo<CommonEnumEntity> getPaymentById(@RequestParam(value = "id", required = true) long id) {
        Optional<CommonEnumEntity> optionalPaymentEntity = paymentService.findById(id);
        CommonEnumEntity paymentEntity = null;
        if (optionalPaymentEntity.isPresent()) {
            paymentEntity = optionalPaymentEntity.get();
        }
        return new RestInfo(paymentEntity);
    }

    @LoginRequired
    @GetMapping(value = "/searchByName")
    public RestInfo<CommonEnumEntity> getPaymentByName(@RequestParam(value = "name", required = true) String name) {
        if (StringUtils.isBlank(name)) {
            throw new MyException("请输入要搜索的名字");
        }
        CommonEnumEntity paymentEntity = paymentService.findByName(name);
        return new RestInfo(paymentEntity);
    }

    @LoginRequired
    @PostMapping(value = "/save")
    public RestInfo saveOrUpdatePayment(@RequestHeader(value = HEADER_TOKEN) String token, @RequestBody CommonEnumEntity paymentEntity) {
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
    public RestInfo update(@RequestHeader(value = HEADER_TOKEN) String token, @RequestBody CommonEnumEntity requestPaymentEntity) {
        if (requestPaymentEntity == null || requestPaymentEntity.getId() <= 0) {
            throw new MyException("请传入更新的内容");
        }
        AccountInfoEntity user = null;
        user = tokenVerify.getUserInfoByToken(token);
        if (user != null) {
            String roleName = tokenVerify.getRoleNameByUser(user);
            CommonEnumEntity paymentEntity = null;
            if (ADMIN_ROL.equals(roleName)) {
                Optional<CommonEnumEntity> paymentEntityOptional = paymentService.findById(requestPaymentEntity.getId());
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
            CommonEnumEntity paymentEntity = null;
            String roleName = tokenVerify.getRoleNameByUser(user);
            if (ADMIN_ROL.equals(roleName)) {
                Optional<CommonEnumEntity> paymentEntityOptional = paymentService.findById(id);
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


    private CommonEnumEntity updateClothesCategory(CommonEnumEntity paymentEntity, CommonEnumEntity requestPaymentEntity) {
        if (!StringUtils.isBlank(requestPaymentEntity.getName())) {
            paymentEntity.setName(requestPaymentEntity.getName());
        }
        if (requestPaymentEntity.getImageEntity()!=null) {
            paymentEntity.setImageEntity(requestPaymentEntity.getImageEntity());
        }

        return paymentEntity;
    }
}
