package com.xyb.web;

import com.xyb.annotation.LoginRequired;
import com.xyb.common.TokenVerify;
import com.xyb.domain.entity.AccountInfoEntity;
import com.xyb.domain.entity.ClothesCategoryEntity;
import com.xyb.domain.entity.StoreEntity;
import com.xyb.exception.AuthorityException;
import com.xyb.exception.MyException;
import com.xyb.exception.RestInfo;
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

import com.xyb.service.ChothesCategoryService;

import static com.xyb.constants.Constants.ADMIN_ROL;
import static com.xyb.constants.Constants.HEADER_TOKEN;
import static com.xyb.constants.Constants.STORE_ROL;
import static com.xyb.exception.RestInfo.OPERATE_ERROR_STRING;

/**
 * 演示user在内存中的操作
 *
 * @Author fanxinqi
 * @Date 2018/3/20
 */
@RestController
@RequestMapping("/clothesCategory")
public class ClothesCategoryController {
    @Autowired
    private ChothesCategoryService chothesCategoryService;
    @Autowired
    private TokenVerify tokenVerify;
    /**
     * 内存中保存，用于演示。ConcurrentHashMap是线程安全的map
     */
    private static Map<Long, ClothesCategoryEntity> clothesCategorys = new ConcurrentHashMap<>();

    @LoginRequired
    @GetMapping("/list")
    public RestInfo<ClothesCategoryEntity> getClothesCategoryList(@PageableDefault(page = 0, size = 1000, sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable) {
        Page<ClothesCategoryEntity> r = chothesCategoryService.findAll(pageable);
        List<ClothesCategoryEntity> sortList = new ArrayList<>();
        for (ClothesCategoryEntity entity : r) {
            if (entity.getParentId() == 0) {
                sortList.add(entity);
                for (ClothesCategoryEntity entityChildren : r) {
                    if (entityChildren.getParentId() != 0 && entity.getId() == entityChildren.getParentId()) {
                        entity.getChildrenClothesCategoryEntitySet().add(entityChildren);
                    }
                }
            }
        }
        PageImpl page = new PageImpl(sortList);
        return new RestInfo(page);
    }

    @LoginRequired
    @GetMapping(value = "/getById")
    public RestInfo<ClothesCategoryEntity> getClothesCategoryById(@RequestParam(value = "id", required = true) long id) {
        Optional<ClothesCategoryEntity> clothesCategoryEntityOptional = chothesCategoryService.findById(id);
        ClothesCategoryEntity clothesCategoryEntity = null;
        if (clothesCategoryEntityOptional.isPresent()) {
            clothesCategoryEntity = clothesCategoryEntityOptional.get();
        }
        return new RestInfo(clothesCategoryEntity);
    }

    @LoginRequired
    @GetMapping(value = "/searchByName")
    public RestInfo<ClothesCategoryEntity> getClothesCategoryByName(@RequestParam(value = "name", required = true) String name) {
        if (StringUtils.isBlank(name)) {
            throw new MyException("请输入要搜索的名字");
        }
        ClothesCategoryEntity clothesCategoryEntity = chothesCategoryService.findByName(name);
        return new RestInfo(clothesCategoryEntity);
    }

    @LoginRequired
    @PostMapping(value = "/save")
    public RestInfo saveOrUpdateClothesCategory(@RequestHeader(value = HEADER_TOKEN) String token, @RequestBody ClothesCategoryEntity clothesCategory) {
        if (clothesCategory == null) {
            throw new MyException("请传入更新的内容");
        }
        AccountInfoEntity user = null;
        user = tokenVerify.getUserInfoByToken(token);
        if (user != null) {
            String roleName = tokenVerify.getRoleNameByUser(user);
            if (ADMIN_ROL.equals(roleName)) {
                return new RestInfo(chothesCategoryService.save(clothesCategory));
            } else {
                throw new AuthorityException("还没有相应的权限");
            }
        }
        throw new MyException("操作失败");
    }

    @PostMapping(value = "/update")
    public RestInfo update(@RequestHeader(value = HEADER_TOKEN) String token, @RequestBody ClothesCategoryEntity requestClothesCategory) {
        if (requestClothesCategory == null || requestClothesCategory.getId() <= 0) {
            throw new MyException("请传入更新的内容");
        }
        AccountInfoEntity user = null;
        user = tokenVerify.getUserInfoByToken(token);
        if (user != null) {
            String roleName = tokenVerify.getRoleNameByUser(user);
            ClothesCategoryEntity clothesCategoryEntity = null;
            if (ADMIN_ROL.equals(roleName)) {
                Optional<ClothesCategoryEntity> clothesCategoryEntityOptional = chothesCategoryService.findById(requestClothesCategory.getId());
                if (clothesCategoryEntityOptional != null) {
                    clothesCategoryEntity = clothesCategoryEntityOptional.get();
                } else {
                    throw new MyException("您还没有该分类，还不能进行编辑");
                }
            } else if (STORE_ROL.equals(roleName)) {
                throw new AuthorityException("您还没有相应的权限");
            }
            if (clothesCategoryEntity != null) {
                return new RestInfo(chothesCategoryService.save(updateClothesCategory(clothesCategoryEntity, requestClothesCategory)));
            } else {
                throw new AuthorityException("您还没有相应的权限");
            }
        }
        throw new AuthorityException("您还没有相应的权限");
    }

    @LoginRequired
    @PostMapping(value = "/deleteById")
    public RestInfo deleteClothesCategory(@RequestHeader(value = HEADER_TOKEN) String token, @RequestParam(value = "id", required = true) long id) {
        AccountInfoEntity user = null;
        user = tokenVerify.getUserInfoByToken(token);
        if (user != null) {
            ClothesCategoryEntity storeEntity = null;
            String roleName = tokenVerify.getRoleNameByUser(user);
            if (ADMIN_ROL.equals(roleName)) {
                Optional<ClothesCategoryEntity> clothesCategoryEntityOptional = chothesCategoryService.findById(id);
                storeEntity = clothesCategoryEntityOptional == null ? null : clothesCategoryEntityOptional.get();
                try {
                    chothesCategoryService.delete(storeEntity);
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


    private ClothesCategoryEntity updateClothesCategory(ClothesCategoryEntity clothesCategoryEntity, ClothesCategoryEntity requestCategory) {
        if (!StringUtils.isBlank(requestCategory.getDes())) {
            clothesCategoryEntity.setDes(requestCategory.getDes());
        }
        if (!StringUtils.isBlank(requestCategory.getName())) {
            clothesCategoryEntity.setName(requestCategory.getName());
        }
        if (requestCategory.getPrice() != 0) {
            clothesCategoryEntity.setPrice(requestCategory.getPrice());
        }
        return clothesCategoryEntity;
    }
}
