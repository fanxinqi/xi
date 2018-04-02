package com.xyb.web;

import com.xyb.domain.entity.ClothesCategoryEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.xyb.service.ChothesCategoryService;
/**
 * 演示user在内存中的操作
 * @Author fanxinqi
 * @Date 2018/3/20
 */
@RestController
@RequestMapping("/clothes")
public class ClothesCategoryController {
    @Autowired
    private ChothesCategoryService chothesCategoryService;
    /**
     * 内存中保存，用于演示。ConcurrentHashMap是线程安全的map
     */
    private static Map<Long, ClothesCategoryEntity> clothesCategorys = new ConcurrentHashMap<>();

    @GetMapping("/list")
    public List<ClothesCategoryEntity> getUserList() {
        List<ClothesCategoryEntity> r = chothesCategoryService.findAll();
        return r;
    }

    @PostMapping(value="/add")
    public ClothesCategoryEntity addUser(@RequestBody @Valid ClothesCategoryEntity clothesCategory) {
//        clothesCategorys.
        return clothesCategory;
    }


    @GetMapping(value="/get/{id}")
    public ClothesCategoryEntity getUser(@PathVariable Long id) {
        // 处理"/clotheCategory/{id}"的GET请求，用来获取url中id值的User信息
        // url中的id可通过@PathVariable绑定到函数的参数中
        return clothesCategorys.get(id);
    }

    @PostMapping(value="/update/{id}")
    public String updateUser(@PathVariable Long id,@RequestBody ClothesCategoryEntity clothesCategory) {
        ClothesCategoryEntity u = clothesCategorys.get(id);
        u.setName(clothesCategory.getName());
        return "success";
    }
}
