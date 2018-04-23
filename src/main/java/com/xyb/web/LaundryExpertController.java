package com.xyb.web;

import com.xyb.annotation.LoginRequired;
import com.xyb.common.TokenVerify;
import com.xyb.domain.entity.AccountInfoEntity;
import com.xyb.domain.entity.ClothesCategoryEntity;
import com.xyb.domain.entity.LaundryExpertEntity;
import com.xyb.exception.AuthorityException;
import com.xyb.exception.MyException;
import com.xyb.exception.RestInfo;
import com.xyb.service.LaundryExpectService;
import com.xyb.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import static com.xyb.constants.Constants.ADMIN_ROL;
import static com.xyb.constants.Constants.HEADER_TOKEN;
import static com.xyb.constants.Constants.STORE_ROL;

@RestController     // @RestController返回的是json数据，@Controller返回的是跳转控制(有模版引擎时使用)
@RequestMapping("/laundryExpert") // 方法上的@RequestMapping用于区分不同的模块
public class LaundryExpertController {
    @Autowired
    private LaundryExpectService laundryExpectService;
    @Autowired
    private TokenVerify tokenVerify;
    /**
     * 内存中保存，用于演示。ConcurrentHashMap是线程安全的map
     */
    private static Map<Long, ClothesCategoryEntity> clothesCategorys = new ConcurrentHashMap<>();

    @LoginRequired
    @GetMapping("/list")
    public RestInfo<LaundryExpertEntity> getLaundryExpectList(@RequestHeader(value = HEADER_TOKEN) String token
            , @PageableDefault(page = 0, size = 10, sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable
            , @RequestParam(value = "name", required = false) String name
            , @RequestParam(value = "phone", required = false) String phone
            , @RequestParam(value = "storeId", required = false) long storeId
    ) {

        AccountInfoEntity user = null;
        user = tokenVerify.getUserInfoByToken(token);
        if (user != null) {
            ClothesCategoryEntity storeEntity = null;
            String roleName = tokenVerify.getRoleNameByUser(user);
            Page<LaundryExpertEntity> laundryExpertEntityPage = null;
            if (ADMIN_ROL.equals(roleName)) {
                if (storeId > 0) {
                    laundryExpertEntityPage=searchLaundryExpert(storeId,name,phone,pageable);
                } else {
                    if (!StringUtils.isBlank(name)) {
                        laundryExpertEntityPage = laundryExpectService.findByName(name, pageable);
                    }
                    if (!StringUtils.isBlank(phone)) {
                        laundryExpertEntityPage = laundryExpectService.findByPhone(phone, pageable);
                    }
                    if (StringUtils.isBlank(name) && StringUtils.isBlank(phone)) {
                        laundryExpertEntityPage = laundryExpectService.findAll(pageable);
                    }
                }
                return new RestInfo(laundryExpertEntityPage);

            } else if (STORE_ROL.equals(roleName)) {
                if (user.getStoreId() > 0) {
                    laundryExpertEntityPage=searchLaundryExpert(user.getStoreId(),name,phone,pageable);
                    return new RestInfo(laundryExpertEntityPage);
                }else {
                    throw new AuthorityException("您还没有相应的店铺");
                }
            }
        }
        throw new AuthorityException("您还没有相应的权限");

    }

    @LoginRequired
    @GetMapping(value = "/getById")
    public RestInfo<LaundryExpertEntity> getLaundryExpertEntity(@RequestParam(value = "id", required = true) long id) {
        Optional<LaundryExpertEntity> laundryExpertEntityOptional = laundryExpectService.findById(id);
        LaundryExpertEntity laundryExpertEntity = null;
        if (laundryExpertEntityOptional.isPresent()) {
            laundryExpertEntity = laundryExpertEntityOptional.get();
        }
        return new RestInfo(laundryExpertEntity);
    }

    @LoginRequired
    @GetMapping(value = "/searchByName")
    public RestInfo<LaundryExpertEntity> getLaundryExpertByName(@RequestParam(value = "name", required = true) String name
            , @PageableDefault(page = 0, size = 10, sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable) {
        if (StringUtils.isBlank(name)) {
            throw new MyException("请输入要搜索的名字");
        }
        Page<LaundryExpertEntity> laundryExpertEntityPage = laundryExpectService.findByName(name,pageable);
        return new RestInfo(laundryExpertEntityPage);
    }

    @LoginRequired
    @PostMapping(value = "/save")
    public RestInfo saveLaundryExpert(@RequestHeader(value = HEADER_TOKEN) String token, @RequestBody LaundryExpertEntity laundryExpertEntity) {
        if (laundryExpertEntity == null) {
            throw new MyException("请提交要保存的内容");
        }
        AccountInfoEntity user = null;
        user = tokenVerify.getUserInfoByToken(token);
        if (user != null) {
            String roleName = tokenVerify.getRoleNameByUser(user);
            if (ADMIN_ROL.equals(roleName)) {
                if(laundryExpectService.findByName(laundryExpertEntity.getName())!=null || laundryExpectService.findByPhone(laundryExpertEntity.getPhone())!=null )
                {
                    throw new MyException("您填写的名字或者手机号已经存在");
                }
                laundryExpertEntity.setCreateTime(System.currentTimeMillis());
                return new RestInfo(laundryExpectService.save(laundryExpertEntity));
            } else {
                throw new AuthorityException("还没有相应的权限");
            }
        }
        throw new MyException("操作失败");
    }

    @PostMapping(value = "/update")
    public RestInfo update(@RequestHeader(value = HEADER_TOKEN) String token, @RequestBody LaundryExpertEntity requestLaundryExpertEntity) {
        if (requestLaundryExpertEntity == null || requestLaundryExpertEntity.getId() <= 0) {
            throw new MyException("请传入更新的内容");
        }
        AccountInfoEntity user = null;
        user = tokenVerify.getUserInfoByToken(token);
        if (user != null) {
            String roleName = tokenVerify.getRoleNameByUser(user);
            LaundryExpertEntity laundryExpertEntity = null;
            if (ADMIN_ROL.equals(roleName)) {
                Optional<LaundryExpertEntity> laundryExpertEntityOptional = laundryExpectService.findById(requestLaundryExpertEntity.getId());
                if (laundryExpertEntityOptional != null) {
                    laundryExpertEntity = laundryExpertEntityOptional.get();
                } else {
                    throw new MyException("您还没有该记录，还不能进行编辑");
                }
            } else if (STORE_ROL.equals(roleName)) {
                throw new AuthorityException("您还没有相应的权限");
            }
            if (laundryExpertEntity != null) {
                return new RestInfo(laundryExpectService.save(updateExpertEntity(laundryExpertEntity, requestLaundryExpertEntity)));
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
            LaundryExpertEntity laundryExpertEntity = null;
            String roleName = tokenVerify.getRoleNameByUser(user);
            if (ADMIN_ROL.equals(roleName)) {
                Optional<LaundryExpertEntity> laundryExpertEntityOptional = laundryExpectService.findById(id);
                laundryExpertEntity = laundryExpertEntityOptional == null ? null : laundryExpertEntityOptional.get();
                try {
                    laundryExpectService.delete(laundryExpertEntity);
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


    private LaundryExpertEntity updateExpertEntity(LaundryExpertEntity laundryExpertEntity, LaundryExpertEntity requestLaundryExpertEntity) {
        if (!StringUtils.isBlank(requestLaundryExpertEntity.getDes())) {
            laundryExpertEntity.setDes(requestLaundryExpertEntity.getDes());
        }
        if (!StringUtils.isBlank(requestLaundryExpertEntity.getName())) {
            laundryExpertEntity.setName(requestLaundryExpertEntity.getName());
        }
        if (!StringUtils.isBlank(requestLaundryExpertEntity.getAddress())) {
            laundryExpertEntity.setAddress(requestLaundryExpertEntity.getAddress());
        }
        if (!StringUtils.isBlank(requestLaundryExpertEntity.getIdNum())) {
            laundryExpertEntity.setIdNum(requestLaundryExpertEntity.getIdNum());
        }  if (!StringUtils.isBlank(requestLaundryExpertEntity.getPhone())) {
            laundryExpertEntity.setPhone(requestLaundryExpertEntity.getPhone());
        }
        if (!StringUtils.isBlank(requestLaundryExpertEntity.getProfessionalDes())) {
            laundryExpertEntity.setProfessionalDes(requestLaundryExpertEntity.getProfessionalDes());
        }
        if (requestLaundryExpertEntity.getBirthday()>0) {
            laundryExpertEntity.setBirthday(requestLaundryExpertEntity.getBirthday());
        }
        if (requestLaundryExpertEntity.getStoreId()>0) {
            laundryExpertEntity.setStoreId(requestLaundryExpertEntity.getStoreId());
        }
        if (requestLaundryExpertEntity.getLevelEntity()!=null) {
            laundryExpertEntity.setLevelEntity(requestLaundryExpertEntity.getLevelEntity());
        }
        if (requestLaundryExpertEntity.getImageEntity()!=null) {
            laundryExpertEntity.setImageEntity(requestLaundryExpertEntity.getImageEntity());
        }
        if (requestLaundryExpertEntity.getWorkingYears()>0) {
            laundryExpertEntity.setWorkingYears(requestLaundryExpertEntity.getWorkingYears());
        }
        return laundryExpertEntity;
    }

    private Page<LaundryExpertEntity> searchLaundryExpert(long storeId, String name, String phone,Pageable pageable) {
        Page<LaundryExpertEntity> laundryExpertEntityPage = null;
        if (!StringUtils.isBlank(name)) {
            laundryExpertEntityPage = laundryExpectService.findByStoreIdAndName(storeId, name, pageable);
        }
        if (!StringUtils.isBlank(phone)) {
            laundryExpertEntityPage = laundryExpectService.findByStoreIdAndPhone(storeId, phone, pageable);
        }
        if (StringUtils.isBlank(name) && StringUtils.isBlank(phone)) {
            laundryExpertEntityPage = laundryExpectService.findByStoreId(storeId, pageable);
        }
        return laundryExpertEntityPage;
    }
}
