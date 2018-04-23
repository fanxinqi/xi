package com.xyb.web;

import com.xyb.annotation.LoginRequired;
import com.xyb.common.TokenVerify;
import com.xyb.domain.entity.AccountInfoEntity;
import com.xyb.domain.entity.ClothesOrderEntity;
import com.xyb.domain.entity.MemberCategoryEntity;
import com.xyb.domain.entity.MemberEntity;
import com.xyb.domain.model.MemberAndCategory;
import com.xyb.domain.repository.MemberCategoryRepository;
import com.xyb.exception.AuthorityException;
import com.xyb.exception.MyException;
import com.xyb.exception.RestInfo;
import com.xyb.service.MemberService;
import com.xyb.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Generated;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import static com.xyb.constants.Constants.ADMIN_ROL;
import static com.xyb.constants.Constants.HEADER_TOKEN;
import static com.xyb.constants.Constants.STORE_ROL;

/**
 * 演示user在内存中的操作
 *
 * @Author fanxinqi
 * @Date 2018/3/20
 */
@RestController
@RequestMapping("/member")
public class MemberController {
    @Autowired
    private MemberService memberService;
    @Autowired
    private MemberCategoryRepository memberCategoryRepository;
    @Autowired
    private TokenVerify tokenVerify;

    @LoginRequired
    @GetMapping("/list")
    public RestInfo getUserList(@RequestHeader(value = HEADER_TOKEN) String token, @PageableDefault(page = 0, size = 10, sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable) {
        AccountInfoEntity entity = null;
        if (token != null) {
            entity = tokenVerify.getUserInfoByToken(token);
            String roleName = tokenVerify.getRoleNameByUser(entity);
            if (ADMIN_ROL.equals(roleName)) {
                return new RestInfo(memberService.findAll(pageable));
            } else if (STORE_ROL.equals(roleName)) {
                if (entity.getStoreId() > 0) {
                    return new RestInfo(memberService.findByStoreId(entity.getStoreId(), pageable));
                }
            }
        }
        throw new MyException("操作失败");
    }

    @LoginRequired
    @GetMapping("/listCategory")
    public RestInfo getMemberListCategory(@RequestHeader(value = HEADER_TOKEN) String token, @PageableDefault(page = 0, size = 10, sort = {"storeId"}, direction = Sort.Direction.DESC) Pageable pageable) {
        AccountInfoEntity entity = null;
        if (token != null) {
            entity = tokenVerify.getUserInfoByToken(token);
            String roleName = tokenVerify.getRoleNameByUser(entity);
            MemberAndCategory memberAndCategory = new MemberAndCategory();
            memberAndCategory.setMemberCategoryEntity((ArrayList<MemberCategoryEntity>) memberCategoryRepository.findAll());
            if (ADMIN_ROL.equals(roleName)) {
                memberAndCategory.setMemberEntity(memberService.findAll(pageable));
                return new RestInfo(memberAndCategory);
            } else if (STORE_ROL.equals(roleName)) {
                if (entity.getStoreId() > 0) {
                    memberAndCategory.setMemberEntity(memberService.findByStoreId(entity.getStoreId(), pageable));
                    return new RestInfo(memberAndCategory);
                }
            }
        }
        throw new MyException("操作失败");
    }

    @LoginRequired
    @PostMapping(value = "/save")
    public RestInfo addMember(@RequestHeader(value = HEADER_TOKEN, required = false) String token, @RequestBody MemberEntity member) {
        if (member == null) {
            throw new MyException("请传入相应的实体");
        }
        AccountInfoEntity entity = null;
        if (token != null) {
            entity = tokenVerify.getUserInfoByToken(token);
            String roleName = tokenVerify.getRoleNameByUser(entity);
            if (ADMIN_ROL.equals(roleName)) {
                if (member.getStoreId() <= 0) {
                    throw new MyException("请选择您所加会员的门店");
                } else {
                    if (memberService.findByStoreIdAndPhone(member.getStoreId(), member.getPhone()) != null) {
                        throw new MyException("您已有会员，是否需要充值");
                    } else {
                        member.setCreateTime(System.currentTimeMillis());
                        return new RestInfo(memberService.save(member));
                    }
                }
            } else if (STORE_ROL.equals(roleName)) {
                if (entity.getStoreId() > 0) {
                    if (!StringUtils.isBlank(member.getPhone())) {
                        if (memberService.findByStoreIdAndPhone(entity.getStoreId(), member.getPhone()) != null) {
                            throw new MyException("您已有会员，是否需要充值");
                        } else {
                            member.setCreateTime(System.currentTimeMillis());
                            member.setStoreId(entity.getStoreId());
                            return new RestInfo(memberService.save(member));
                        }
                    }
                }
            }
        }
        throw new MyException("操作失败");
    }

    @LoginRequired
    @PostMapping(value = "/update")
    public RestInfo updateMember(@RequestHeader(value = HEADER_TOKEN) String token, @RequestBody MemberEntity member) {
        if (member == null || member.getId() <= 0) {
            throw new MyException("请传入更新的内容");
        }
        AccountInfoEntity entity = null;
        entity = tokenVerify.getUserInfoByToken(token);
        String roleName = tokenVerify.getRoleNameByUser(entity);
        if (ADMIN_ROL.equals(roleName)) {
            Optional<MemberEntity> memberEntityOptional = memberService.findById(member.getId());
            if (memberEntityOptional != null) {
                MemberEntity memberEntity = memberEntityOptional.get();
                if (memberEntity != null) {
                    return new RestInfo(memberService.save(updateMember(memberEntity, member)));
                } else {
                    throw new MyException("您还没有该会员，还不能进行编辑");
                }
            } else {
                throw new MyException("您还没有该会员，还不能进行编辑");
            }

        } else if (STORE_ROL.equals(roleName)) {
            if (entity.getStoreId() > 0) {
                if (member.getId() > 0) {
                    MemberEntity memberEntity = memberService.findByStoreIdAndId(entity.getStoreId(), member.getId());
                    if (memberEntity != null) {
                        return new RestInfo(memberService.save(updateMember(memberEntity, member)));
                    } else {
                        throw new MyException("您还没有该会员，还不能进行编辑");
                    }
                }
            }
        }

        throw new MyException("操作失败");
    }

    @LoginRequired
    @GetMapping(value = "/searchByName")
    public RestInfo searchMemberByName(@RequestHeader(value = HEADER_TOKEN) String token
            , @RequestParam(value = "name") String name
            , @RequestParam(value = "storeId", required = true) long storeId, @PageableDefault(page = 0, size = 10, sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable) {
        AccountInfoEntity user = null;
        user = tokenVerify.getUserInfoByToken(token);
        if (user != null) {
            Page<MemberEntity> page = null;
            String roleName = tokenVerify.getRoleNameByUser(user);
            if (ADMIN_ROL.equals(roleName)) {
                page = memberService.findByName(name, pageable);
            } else if (STORE_ROL.equals(roleName)) {
                if (user.getStoreId() <= 0) {
                    throw new MyException("您还没有对应的店铺信息");
                }
                page = memberService.findByStoreIdAndName(user.getStoreId(), name, pageable);
            }
            return new RestInfo(page);
        }
        throw new AuthorityException("您还没有相应的权限");
    }

    @LoginRequired
    @GetMapping(value = "/searchMemberByPhone")
    public RestInfo searchMemberByPhone(@RequestHeader(value = HEADER_TOKEN) String token, @RequestParam(value = "phone", required = true) String phone, @PageableDefault(page = 0, size = 10, sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable) {
        AccountInfoEntity user = null;
        user = tokenVerify.getUserInfoByToken(token);
        if (user != null) {
            String roleName = tokenVerify.getRoleNameByUser(user);
            Page<MemberEntity> page = null;
            if (ADMIN_ROL.equals(roleName)) {
                page = memberService.findByPhone(phone, pageable);
            } else if (STORE_ROL.equals(roleName)) {
                if (user.getStoreId() <= 0) {
                    throw new MyException("您还没有对应的店铺信息");
                }
                MemberEntity memberEntity = memberService.findByStoreIdAndPhone(user.getStoreId(), phone);
                ArrayList<MemberEntity> memberList = new ArrayList<>();
                memberList.add(memberEntity);
                page = new PageImpl(memberList);
            }
            return new RestInfo(page);
        }
        throw new AuthorityException("您还没有相应的权限");
    }

    @LoginRequired
    @GetMapping(value = "/searchMemberById")
    public RestInfo searchMemberById(@RequestHeader(value = HEADER_TOKEN) String token, @RequestParam(value = "id", required = true) long id, @PageableDefault(page = 0, size = 10, sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable) {
        AccountInfoEntity user = null;
        user = tokenVerify.getUserInfoByToken(token);
        if (user != null) {
            MemberEntity memberEntity = null;
            String roleName = tokenVerify.getRoleNameByUser(user);
            if (ADMIN_ROL.equals(roleName)) {
                Optional<MemberEntity> optionalMemberEntity = memberService.findById(id);
                memberEntity = optionalMemberEntity == null ? null : optionalMemberEntity.get();
            } else if (STORE_ROL.equals(roleName)) {
                if (user.getStoreId() <= 0) {
                    throw new MyException("您还没有对应的店铺信息");
                }
                memberEntity = memberService.findByStoreIdAndId(user.getStoreId(), id);
            }
            return new RestInfo(memberEntity);
        }
        throw new AuthorityException("您还没有相应的权限");
    }

    @LoginRequired
    @PostMapping(value = "/deleteById")
    public RestInfo deleteMemberById(@RequestHeader(value = HEADER_TOKEN) String token, @RequestParam(value = "id", required = true) long id) {
        AccountInfoEntity user = null;
        user = tokenVerify.getUserInfoByToken(token);
        if (user != null) {
            MemberEntity memberEntity = null;
            String roleName = tokenVerify.getRoleNameByUser(user);
            if (ADMIN_ROL.equals(roleName)) {
                Optional<MemberEntity> optionalMemberEntity = memberService.findById(id);
                memberEntity = optionalMemberEntity == null ? null : optionalMemberEntity.get();
            } else if (STORE_ROL.equals(roleName)) {
                if (user.getStoreId() <= 0) {
                    throw new MyException("您还没有对应的会员信息");
                }
                memberEntity = memberService.findByStoreIdAndId(user.getStoreId(), id);
            }
            if (memberEntity != null) {
                memberService.delete(memberEntity);
                return new RestInfo();
            } else {
                throw new MyException("您还没有对应的会员信息");
            }
        }
        throw new AuthorityException("您还没有相应的权限");

    }

    private MemberEntity updateMember(MemberEntity memberEntity, MemberEntity requestMember) {
        if (memberEntity == null) {
            return null;
        }
        if (!StringUtils.isBlank(requestMember.getPhone())) {
            memberEntity.setPhone(requestMember.getPhone());
        }
        if (!StringUtils.isBlank(requestMember.getAddress())) {
            memberEntity.setAddress(requestMember.getAddress());
        }
        if (requestMember.getBirthday() > 0) {
            memberEntity.setBirthday(requestMember.getBirthday());
        }
        if (!StringUtils.isBlank(requestMember.getDes())) {
            memberEntity.setDes(requestMember.getDes());
        }
        if (!StringUtils.isBlank(requestMember.getHeadUrl())) {
            memberEntity.setHeadUrl(requestMember.getHeadUrl());
        }
        if (!StringUtils.isBlank(requestMember.getIdNum())) {
            memberEntity.setIdNum(requestMember.getIdNum());
        }
        if (!StringUtils.isBlank(requestMember.getName())) {
            memberEntity.setName(requestMember.getName());
        }
        if (!StringUtils.isBlank(requestMember.getRemainFee())) {
            memberEntity.setRemainFee(requestMember.getRemainFee());
        }
        if (requestMember.getBirthday() > 0) {
            memberEntity.setBirthday(requestMember.getBirthday());
        }
        if (requestMember.getMemberCategoryId() > 0) {
            memberEntity.setMemberCategoryId(requestMember.getMemberCategoryId());
        }
        if (requestMember.getGender() > 0) {
            memberEntity.setGender(requestMember.getGender());
        }
        return memberEntity;
    }

}
