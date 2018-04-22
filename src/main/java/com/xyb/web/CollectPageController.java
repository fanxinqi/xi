package com.xyb.web;

import com.xyb.annotation.LoginRequired;
import com.xyb.common.TokenVerify;
import com.xyb.domain.entity.AccountInfoEntity;
import com.xyb.domain.entity.ClothesCategoryEntity;
import com.xyb.domain.entity.MemberCategoryEntity;
import com.xyb.domain.model.MemberAndCategory;
import com.xyb.exception.MyException;
import com.xyb.exception.RestInfo;
import com.xyb.service.ChothesCategoryService;
import com.xyb.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.xyb.constants.Constants.ADMIN_ROL;
import static com.xyb.constants.Constants.HEADER_TOKEN;
import static com.xyb.constants.Constants.STORE_ROL;

@RestController
@RequestMapping("/collectClothes")
public class CollectPageController {
    @Autowired
    private ChothesCategoryService chothesCategoryService;
    @Autowired
    private PaymentService paymentService;
    @Autowired
    private TokenVerify tokenVerify;

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
            jObject.put("paymentList", paymentService.findAll());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return new RestInfo(jObject);
    }
}
