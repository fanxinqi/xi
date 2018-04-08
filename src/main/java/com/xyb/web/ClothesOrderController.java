package com.xyb.web;

import com.xyb.annotation.LoginRequired;
import com.xyb.domain.entity.ClothesOrderEntity;
import com.xyb.exception.RestInfo;
import com.xyb.service.ClothesOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clothes_order")
public class ClothesOrderController {
    @Autowired
    private ClothesOrderService clothesOrderService;

    @LoginRequired
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
        return new RestInfo<>();
    }

    @PostMapping(value = "/search_by_phone")
    public RestInfo getClothesCategoryByName(@RequestParam(value = "phone", required = true) String phone) {
        List<ClothesOrderEntity> list= clothesOrderService.findByPhone(phone);
        return new RestInfo<>(list);
    }
}
