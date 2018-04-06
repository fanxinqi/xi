package com.xyb.web;

import com.xyb.domain.entity.ClothesCategoryEntity;
import com.xyb.domain.entity.ClothesOrderEntity;
import com.xyb.exception.MyException;
import com.xyb.exception.RestInfo;
import com.xyb.service.ChothesCategoryService;
import com.xyb.service.ClothesOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/clothes_order")
public class ClothesOrderController {
    @Autowired
    private ClothesOrderService clothesOrderService;

    @GetMapping("/list")
    public RestInfo getClothesCategoryList() {
        List<ClothesOrderEntity> list = clothesOrderService.findAll();
//        throw new MyException("hahh");
        return new RestInfo<>(list);
    }

    @GetMapping(value = "/get_by_id")
    public RestInfo getClothesOrderById(@RequestParam(value = "id", required = true) long id) {
        // 处理"/clotheCategory/{id}"的GET请求，用来获取url中id值的User信息
        // url中的id可通过@PathVariable绑定到函数的参数中
        return new RestInfo<>(clothesOrderService.findById(id));
    }

    @PostMapping(value = "/save_or_update")
    public RestInfo saveOrUpdateClothesCategory(@RequestBody ClothesOrderEntity clothesOrderEntity) {
        return new RestInfo<>(clothesOrderService.save(clothesOrderEntity));
    }

    @PostMapping(value = "/delete_by_id")
    public RestInfo deleteClothesCategory(@RequestParam(value = "id", required = true) long id) {
        clothesOrderService.deleteById(id);
        return new RestInfo<>(null);
    }

    @PostMapping(value = "/search_by_phone")
    public RestInfo getClothesCategoryByName(@RequestParam(value = "phone", required = true) String phone) {
        return new RestInfo<>(clothesOrderService.findByPhone(phone));
    }
}
