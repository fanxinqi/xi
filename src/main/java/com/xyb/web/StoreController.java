package com.xyb.web;

import com.xyb.annotation.LoginRequired;
import com.xyb.common.TokenVerify;
import com.xyb.constants.Constants;
import com.xyb.domain.entity.*;
import com.xyb.exception.AuthorityException;
import com.xyb.exception.MyException;
import com.xyb.exception.RestInfo;
import com.xyb.service.StorageService;
import com.xyb.service.StoreService;
import com.xyb.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import static com.xyb.constants.Constants.*;

@RestController
@RequestMapping("/store")
public class StoreController {
    @Autowired
    private StoreService storeService;
    @Autowired
    private TokenVerify tokenVerify;
    @Autowired
    private StorageService storageService;

    @LoginRequired
    @GetMapping("/getStoreById")
    public RestInfo getStoreById(@RequestHeader(value = HEADER_TOKEN) String token, @RequestParam(value = "id", required = true) long id) {

        AccountInfoEntity user = null;
        user = tokenVerify.getUserInfoByToken(token);
        if (user != null) {
            StoreEntity storeEntity = null;
            String roleName = tokenVerify.getRoleNameByUser(user);
            if (ADMIN_ROL.equals(roleName)) {
                Optional<StoreEntity> storeEntityOptional = storeService.findById(id);
                if (storeEntityOptional.isPresent()) {
                    storeEntity = storeEntityOptional.get();
                }
            } else if (STORE_ROL.equals(roleName)) {
                if (user.getStoreId() <= 0) {
                    throw new MyException("您还没有对应的店铺信息");
                }
                if (user.getStoreId() != id) {
                    throw new AuthorityException("您还没有相应的权限");
                }
                Optional<StoreEntity> storeEntityOptional = storeService.findById(id);
                if (storeEntityOptional.isPresent()) {
                    storeEntity = storeEntityOptional.get();
                }
            }
            return new RestInfo(storeEntity);
        }
        throw new AuthorityException("您还没有相应的权限");
    }

    @LoginRequired
    @GetMapping("/list")
    public RestInfo getStoreList(@RequestHeader(value = HEADER_TOKEN) String token,
                                 @PageableDefault(page = 0, size = 10, sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable
            , @RequestParam(value = "name", required = false) String name) {

        AccountInfoEntity user = null;
        user = tokenVerify.getUserInfoByToken(token);
        if (user != null) {
            StoreEntity storeEntity = null;
            String roleName = tokenVerify.getRoleNameByUser(user);
            if (ADMIN_ROL.equals(roleName)) {
                Page<StoreEntity> storeEntityList = null;
                if (StringUtils.isBlank(name)) {
                    storeEntityList = storeService.findAll(pageable);
                } else {
                    storeEntityList = storeService.findAllByName(name, pageable);
                }
                return new RestInfo(storeEntityList);
            } else if (STORE_ROL.equals(roleName)) {
                throw new AuthorityException("您还没有相应的权限");
            }
        }
        throw new AuthorityException("您还没有相应的权限");
    }

    @LoginRequired
    @PostMapping(value = "/update")
    public RestInfo update(@RequestHeader(value = HEADER_TOKEN) String token, @RequestBody StoreEntity requestStoreEntity) {
        if (requestStoreEntity == null || requestStoreEntity.getId() <= 0) {
            throw new MyException("请传入更新的内容");
        }
        AccountInfoEntity user = null;
        user = tokenVerify.getUserInfoByToken(token);
        if (user != null) {
            String roleName = tokenVerify.getRoleNameByUser(user);
            StoreEntity storeEntity = null;
            if (ADMIN_ROL.equals(roleName)) {
                Optional<StoreEntity> storeEntityOptional = storeService.findById(requestStoreEntity.getId());
                if (storeEntityOptional.isPresent()) {
                    storeEntity = storeEntityOptional.get();
                } else {
                    throw new MyException("您还没有该店铺，还不能进行编辑");
                }
            } else if (STORE_ROL.equals(roleName)) {
                if (user.getStoreId() <= 0) {
                    throw new MyException("您还没有该店铺，还不能进行编辑");
                }
                if (user.getStoreId() != requestStoreEntity.getId()) {
                    throw new MyException("您还没有该店铺，还不能进行编辑");
                }
                Optional<StoreEntity> storeEntityOptional = storeService.findById(requestStoreEntity.getId());
                if (storeEntityOptional != null) {
                    storeEntity = storeEntityOptional.get();
                } else {
                    throw new MyException("您还没有该店铺，还不能进行编辑");
                }
            }
            if (storeEntity != null) {
                return new RestInfo(storeService.save(updateStore(storeEntity, requestStoreEntity)));
            } else {
                throw new MyException("您还没有该订单权限，还不能进行编辑");
            }
        }
        throw new MyException("操作失败");
    }

    @LoginRequired
    @PostMapping(value = "/save")
    public RestInfo save(@RequestHeader(value = HEADER_TOKEN) String token, @RequestBody StoreEntity requestStoreEntity) {
        if (requestStoreEntity == null) {
            throw new MyException("请传入更新的内容");
        }
        if (requestStoreEntity.getStorageNum() <= 0) {
            requestStoreEntity.setStorageNum(STORAGE_NUM);
        }
        AccountInfoEntity user = null;
        user = tokenVerify.getUserInfoByToken(token);
        if (user != null) {
            String roleName = tokenVerify.getRoleNameByUser(user);
            if (ADMIN_ROL.equals(roleName)) {
                if (storeService.findByName(requestStoreEntity.getName()) != null) {
                    throw new MyException("您已存在该店铺名称");
                } else {
                    StoreEntity storeEntity = storeService.save(requestStoreEntity);
                    if (storeEntity != null) {
                        for (int i = 0; i < requestStoreEntity.getStorageNum(); i++) {
                            StorageEntity storageEntity = new StorageEntity();
                            storageEntity.setName(Constants.STORAGE_NAME_PRE + i);
                            storageEntity.setStoreId(storeEntity.getId());
                            storageEntity.setUsableState(0);
                            storageService.save(storageEntity);
                        }
                        return new RestInfo(storeEntity);
                    } else {
                        throw new MyException("创建店铺失败");
                    }
                }

            } else {
                throw new AuthorityException("还没有相应的权限");
            }
        }
        throw new MyException("操作失败");
    }

    @LoginRequired
    @GetMapping(value = "/searchByName")
    public RestInfo searchStoreByName(@RequestHeader(value = HEADER_TOKEN) String token, @RequestParam(value = "name", required = true) String name) {
        if (StringUtils.isBlank(name)) {
            throw new MyException("请输入要搜索的名字");
        }
        AccountInfoEntity user = null;
        user = tokenVerify.getUserInfoByToken(token);
        if (user != null) {
            String roleName = tokenVerify.getRoleNameByUser(user);
            if (ADMIN_ROL.equals(roleName)) {
                StoreEntity storeEntity = storeService.findByName(name);
                return new RestInfo(storeEntity);
            } else {
                throw new AuthorityException("还没有相应的权限");
            }
        }
        throw new MyException("操作失败");
    }

    @LoginRequired
    @PostMapping(value = "/deleteById")
    public RestInfo deleteStoreById(@RequestHeader(value = HEADER_TOKEN) String token, @RequestParam(value = "id", required = true) long id) {
        AccountInfoEntity user = null;
        user = tokenVerify.getUserInfoByToken(token);
        if (user != null) {
            StoreEntity storeEntity = null;
            String roleName = tokenVerify.getRoleNameByUser(user);
            if (ADMIN_ROL.equals(roleName)) {
                Optional<StoreEntity> optionalStoreEntity = storeService.findById(id);
                storeEntity = optionalStoreEntity == null ? null : optionalStoreEntity.get();
                try {
                    storeService.delete(storeEntity);
                    return new RestInfo();
                } catch (Exception e) {
                    e.printStackTrace();
                    throw new MyException("操作失败");
                }

            } else if (STORE_ROL.equals(roleName)) {
                throw new AuthorityException("还没有相应的权限");
            }
        }
        throw new AuthorityException("您还没有相应的权限");
    }


    private StoreEntity updateStore(StoreEntity storeEntity, StoreEntity requestStoreEntity) {
        if (requestStoreEntity.getStorageNum() > storageService.getCount(Constants.STORAGE_NAME_PRE)) {
            for (int i = storageService.getCount(Constants.STORAGE_NAME_PRE); i < requestStoreEntity.getStorageNum(); i++) {
                StorageEntity storageEntity = new StorageEntity();
                storageEntity.setName(Constants.STORAGE_NAME_PRE + i);
                storageEntity.setStoreId(storeEntity.getId());
                storageEntity.setUsableState(0);
                storageService.save(storageEntity);
            }
        }
        storeEntity.setStorageNum(requestStoreEntity.getStorageNum());
        if (!StringUtils.isBlank(requestStoreEntity.getAddress())) {
            storeEntity.setAddress(requestStoreEntity.getAddress());
        }
        if (!StringUtils.isBlank(requestStoreEntity.getName())) {
            storeEntity.setName(requestStoreEntity.getName());
        }
        if (!StringUtils.isBlank(requestStoreEntity.getDes())) {
            storeEntity.setDes(requestStoreEntity.getDes());
        }
        if (requestStoreEntity.getImageEntity() != null) {
            if (requestStoreEntity.getImageEntity().getId() > 0) {
                storeEntity.setImageEntity(requestStoreEntity.getImageEntity());
            }
        }
        return storeEntity;

    }
}
