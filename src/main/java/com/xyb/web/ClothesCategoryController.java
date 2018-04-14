package com.xyb.web;

import com.xyb.domain.entity.ClothesCategoryEntity;
import com.xyb.domain.entity.StoreEntity;
import com.xyb.exception.MyException;
import com.xyb.exception.RestInfo;
import com.xyb.utils.StringUtils;
import org.apache.shiro.authz.annotation.RequiresRoles;
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

import static com.xyb.exception.RestInfo.OPERATE_ERROR_STRING;

/**
 * 演示user在内存中的操作
 *
 * @Author fanxinqi
 * @Date 2018/3/20
 */
@RestController
@RequestMapping("/clothes_category")
public class ClothesCategoryController {
    @Autowired
    private ChothesCategoryService chothesCategoryService;
    /**
     * 内存中保存，用于演示。ConcurrentHashMap是线程安全的map
     */
    private static Map<Long, ClothesCategoryEntity> clothesCategorys = new ConcurrentHashMap<>();

    @GetMapping("/list")
//    @RequiresRoles("ADMIN")
    public RestInfo<ClothesCategoryEntity> getClothesCategoryList(@PageableDefault(page = 0, size=10,sort = { "id" }, direction = Sort.Direction.DESC) Pageable pageable) {
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
        PageImpl page=new PageImpl(sortList);
        return new RestInfo(page);
    }

    @GetMapping(value = "/get_by_id")
    public RestInfo<ClothesCategoryEntity> getClothesCategoryById(@RequestParam(value = "id", required = true) long id) {
        // 处理"/clotheCategory/{id}"的GET请求，用来获取url中id值的User信息
        // url中的id可通过@PathVariable绑定到函数的参数中
        return new RestInfo(chothesCategoryService.findById(id));
    }

    @PostMapping(value = "/save_or_update")
    public RestInfo saveOrUpdateClothesCategory(@ModelAttribute ClothesCategoryEntity clothesCategory) {
        return new RestInfo(chothesCategoryService.save(clothesCategory));
    }
    @PostMapping(value = "/update")
    public RestInfo update(@RequestBody ClothesCategoryEntity parameterEntity) {
        ClothesCategoryEntity entity = new ClothesCategoryEntity();
        long id = 0;
        if (parameterEntity != null && parameterEntity.getId() != null) {
            id = parameterEntity.getId();
            if (chothesCategoryService.findById(id).isPresent()) {
                entity = chothesCategoryService.findById(id).get();
                if (!StringUtils.isBlank(parameterEntity.getDes())) {
                    entity.setDes(parameterEntity.getDes());
                }
                if (!StringUtils.isBlank(parameterEntity.getName())) {
                    entity.setName(parameterEntity.getName());
                }
                if (parameterEntity.getPrice()!=0) {
                    entity.setPrice(parameterEntity.getPrice());
                }

                entity = chothesCategoryService.save(entity);
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
    @PostMapping(value = "/delete_by_id")
    public    RestInfo deleteClothesCategory(@RequestParam(value = "id", required = true) long id) {
        try {
            chothesCategoryService.deleteById(id);
            return new RestInfo();
        } catch (Exception e) {
            e.printStackTrace();
            return new RestInfo().setCode(RestInfo.OPERATE_ERROR).setMessage(OPERATE_ERROR_STRING);
        }
    }

    @PostMapping(value = "/search_by_name")
    public RestInfo<ClothesCategoryEntity> getClothesCategoryByName(@RequestParam(value = "name", required = true) String name) {
        Optional<ClothesCategoryEntity>entityOptional=chothesCategoryService.findByName(name);
        return new RestInfo<>(entityOptional!=null?entityOptional.get():null);
    }
}
