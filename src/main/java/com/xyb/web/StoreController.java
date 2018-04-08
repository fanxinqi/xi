package com.xyb.web;

import com.xyb.domain.entity.StoreEntity;
import com.xyb.exception.AuthorityException;
import com.xyb.exception.MyException;
import com.xyb.exception.RestInfo;
import com.xyb.service.StoreService;
import com.xyb.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/store")
public class StoreController {
    @Autowired
    private StoreService storeService;

    @PostMapping("/get_store")
    public RestInfo getStoreById(@RequestParam(value = "storeId", required = true) long storeId) {
        StoreEntity entity = new StoreEntity();
        Optional<StoreEntity> optionalStoreEntity = storeService.findById(storeId);
        if (optionalStoreEntity.isPresent()) {
            entity = optionalStoreEntity.get();
        }
        return new RestInfo(entity);
    }

    @PostMapping(value = "/update")
    public RestInfo update(@RequestBody StoreEntity parameterEntity) {
        StoreEntity entity = new StoreEntity();
        long id = 0;
        if (parameterEntity != null && parameterEntity.getId() != null) {
            id = parameterEntity.getId();
            if (storeService.findById(id).isPresent()) {
                entity = storeService.findById(id).get();
                if (!StringUtils.isBlank(parameterEntity.getAddress())) {
                    entity.setAddress(parameterEntity.getAddress());
                }
                if (!StringUtils.isBlank(parameterEntity.getName())) {
                    entity.setName(parameterEntity.getName());
                }
                if (parameterEntity.getStoreLaundryExpertSet() != null && parameterEntity.getStoreLaundryExpertSet().size() > 0) {
                    entity.setStoreLaundryExpertSet(parameterEntity.getStoreLaundryExpertSet());
                }
                entity = storeService.save(entity);
            } else {
                throw new MyException("更新的条目的id有误");
            }

        } else {
            if (parameterEntity == null) {
                throw new MyException("请提交要更新的内容");
            }
            if (parameterEntity.getId() == null) {
                throw new MyException("请提交要更新的条目的id");
            }
        }
        return new RestInfo(entity);
    }

    @PostMapping(value = "/save")
    public RestInfo save(@RequestBody StoreEntity parameterEntity) {
        StoreEntity entity = new StoreEntity();
        if (parameterEntity != null) {
            entity = storeService.save(parameterEntity);
        } else {
            throw new MyException("请提交需要保存的内容");
        }
        return new RestInfo(entity);
    }

    @PostMapping(value = "/search_by_name")
    public RestInfo searchStoreByName(@RequestParam(value = "name", required = true) String name) {
        Optional<StoreEntity> storeEntityOptional = storeService.findByName(name);
        StoreEntity entity = storeEntityOptional.isPresent() ? storeEntityOptional.get() : null;
        return new RestInfo(entity);
    }

    @PostMapping(value = "/delete_by_id")
    public RestInfo deleteStoreById(@RequestParam(value = "id", required = true) long id) {
        try {
            storeService.deleteById(id);
        } catch (Exception e) {
            e.printStackTrace();
            throw new MyException("操作失败");
        }
        return new RestInfo();
    }

    @PostMapping(value = "/get_store_members")
    public RestInfo getStoreMemberList(@RequestParam(value = "storeId", required = true) long storeId) {
        Optional<StoreEntity> optionalStoreEntity =
                storeService.findById(storeId);
        if (optionalStoreEntity.isPresent()) {
            StoreEntity entity = optionalStoreEntity.get();
            if (entity != null) {
                return new RestInfo(entity.getStoreMemberSet());
            } else {
                throw new AuthorityException("没有找到对应的数据");
            }
        } else {
            throw new AuthorityException("没有找到对应的数据");
        }
    }
    @PostMapping(value = "/get_store_orders")
    public RestInfo getStoreOrderList(@RequestParam(value = "storeId", required = true) long storeId) {
        Optional<StoreEntity> optionalStoreEntity =
                storeService.findById(storeId);
        if (optionalStoreEntity.isPresent()) {
            StoreEntity entity = optionalStoreEntity.get();
            if (entity != null) {
                return new RestInfo(entity.getStoreOrderSet());
            } else {
                throw new AuthorityException("您还没有自己的店铺");
            }
        } else {
            throw new AuthorityException("您还没有自己的店铺");
        }
    }
}
