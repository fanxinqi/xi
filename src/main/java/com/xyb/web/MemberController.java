package com.xyb.web;

import com.xyb.common.TokenVerify;
import com.xyb.domain.entity.AccountInfoEntity;
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
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import static com.xyb.constants.Constants.HEADER_TOKEN;

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

    @GetMapping("/list")
    public List<MemberEntity> getUserList() {
        return memberService.findAll();
    }

    @GetMapping("/list_category")
    public RestInfo getUserList(@RequestHeader(value = HEADER_TOKEN, required = false) String token, @PageableDefault(page = 0, size = 10, sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable) {
        AccountInfoEntity entity = null;
        if (token != null) {
            entity = tokenVerify.getUserInfoByToken(token);
            if (entity.getStoreId() > 0) {
                MemberAndCategory memberAndCategory = new MemberAndCategory();
                memberAndCategory.setMemberEntity(memberService.findByStoreId(entity.getStoreId(), pageable));
                memberAndCategory.setMemberCategoryEntity((ArrayList<MemberCategoryEntity>) memberCategoryRepository.findAll());
                return new RestInfo(memberAndCategory);
            }
        }
        throw new AuthorityException("你没有相应的权限");
    }

    @PostMapping(value = "/save")
    public RestInfo addMember(@RequestHeader(value = HEADER_TOKEN, required = false) String token, @RequestBody MemberEntity member) {
        if (member == null) {
            throw new MyException("请传入相应的实体");
        }
        AccountInfoEntity entity = null;
        if (token != null) {
            entity = tokenVerify.getUserInfoByToken(token);
            if (entity.getStoreId() > 0) {
                if (!StringUtils.isBlank(member.getPhone())) {
                    if (memberService.findByStoreIdAndPhone(entity.getStoreId(), member.getPhone()) != null) {
                        throw new MyException("您已有会员，是否需要充值");
                    } else {
                        member.setStoreId(entity.getStoreId());
                        return new RestInfo(memberService.save(member));
                    }
                }

            }
        }
        throw new AuthorityException("你没有相应的权限");
    }

    @PostMapping(value = "/update")
    public RestInfo updateMember(@RequestHeader(value = HEADER_TOKEN, required = false) String token, @RequestBody MemberEntity member) {
        if (member == null) {
            throw new MyException("请传入相应的实体");
        }
        AccountInfoEntity entity = null;
        if (token != null) {
            entity = tokenVerify.getUserInfoByToken(token);
            if (entity.getStoreId() > 0) {
                if (member.getId() > 0) {
                  MemberEntity memberEntity = memberService.findByStoreIdAndId(entity.getStoreId(),entity.getId());
                    if (memberEntity != null) {
                        if (!StringUtils.isBlank(member.getPhone())) {
                            memberEntity.setPhone(member.getPhone());
                        }
                        if (!StringUtils.isBlank(member.getAddress())) {
                            memberEntity.setAddress(member.getAddress());
                        }
                        if (member.getBirthday() > 0) {
                            memberEntity.setBirthday(member.getBirthday());
                        }
                        if (!StringUtils.isBlank(member.getDes())) {
                            memberEntity.setDes(member.getDes());
                        }
                        if (!StringUtils.isBlank(member.getHeadUrl())) {
                            memberEntity.setHeadUrl(member.getHeadUrl());
                        }
                        if (!StringUtils.isBlank(member.getIdNum())) {
                            memberEntity.setIdNum(member.getIdNum());
                        }
                        if (!StringUtils.isBlank(member.getName())) {
                            memberEntity.setName(member.getName());
                        }
                        if (!StringUtils.isBlank(member.getRemainFee())) {
                            memberEntity.setRemainFee(member.getRemainFee());
                        }
                        if (member.getBirthday() > 0) {
                            memberEntity.setBirthday(member.getBirthday());
                        }
                        if (member.getMemberCategoryId() > 0) {
                            memberEntity.setMemberCategoryId(member.getMemberCategoryId());
                        }
                        return new RestInfo(memberService.save(memberEntity));

                    } else {
                        throw new MyException("您还没有该会员，还不能进行编辑");
                    }
                }

            }
        }
        throw new AuthorityException("你没有相应的权限");
    }

    @PostMapping(value = "/search_by_name")
    public Page<MemberEntity> searchMemberByName(@RequestParam(value = "storeId", required = true) long storeId, @PageableDefault(page = 0, size = 10, sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable) {
        return memberService.findByStoreId(storeId, pageable);
    }

    @PostMapping(value = "/delete_by_id")
    public void deleteMemberById(@RequestParam(value = "id", required = true) long id) {
        memberService.deleteById(id);
    }

}
