package com.xyb.web;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.xyb.annotation.LoginRequired;
import com.xyb.common.TokenVerify;
import com.xyb.domain.entity.ClothesCategoryEntity;
import com.xyb.domain.repository.MemberCategoryRepository;
import com.xyb.exception.RestInfo;
import com.xyb.service.ChothesCategoryService;
import com.xyb.service.CommonEnumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

import static com.xyb.constants.Constants.HEADER_TOKEN;
import static com.xyb.constants.Constants.PAYMENT_TYPE;

@RestController
@RequestMapping("/collectClothes")
public class CollectPageController {
    @Autowired
    private ChothesCategoryService chothesCategoryService;
    @Autowired
    private CommonEnumService paymentService;
    @Autowired
    private TokenVerify tokenVerify;
    @Autowired
    private MemberCategoryRepository memberCategoryRepository;

    @LoginRequired
    @GetMapping("/init")
    public RestInfo getCategoryPayment(@RequestHeader(value = HEADER_TOKEN) String token) {
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
        //定义JSON
        JSONObject jObject = new JSONObject();
        try {
            jObject.put("categoryList", sortList);
            jObject.put("paymentList", paymentService.findByType(PAYMENT_TYPE));
            jObject.put("memberCategory",memberCategoryRepository.findAll());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return new RestInfo(jObject);
    }
}
