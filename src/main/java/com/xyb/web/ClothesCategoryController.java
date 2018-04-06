package com.xyb.web;

import com.xyb.domain.entity.ClothesCategoryEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import com.xyb.service.ChothesCategoryService;

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
    public List<ClothesCategoryEntity> getClothesCategoryList() {
        List<ClothesCategoryEntity> r = chothesCategoryService.findAll();
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
        return sortList;
    }

    @GetMapping(value = "/get_by_id")
    public Optional<ClothesCategoryEntity> getClothesCategoryById(@RequestParam(value = "id", required = true) long id) {
        // 处理"/clotheCategory/{id}"的GET请求，用来获取url中id值的User信息
        // url中的id可通过@PathVariable绑定到函数的参数中
        return chothesCategoryService.findById(id);
    }

    @PostMapping(value = "/save_or_update")
    public ClothesCategoryEntity saveOrUpdateClothesCategory(@RequestBody ClothesCategoryEntity clothesCategory) {
        return chothesCategoryService.save(clothesCategory);
    }

    @PostMapping(value = "/delete_by_id")
    public void deleteClothesCategory(@RequestParam(value = "id", required = true) long id) {
        chothesCategoryService.deleteById(id);
    }

    @PostMapping(value = "/search_by_name")
    public Optional<ClothesCategoryEntity> getClothesCategoryByName(@RequestParam(value = "name", required = true) String name) {
        return chothesCategoryService.findByName(name);
    }
}
