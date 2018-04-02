package com.xyb.web;

import com.oracle.tools.packager.Log;
import com.xyb.domain.entity.MemberEntity;
import com.xyb.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 演示user在内存中的操作
 * @Author fanxinqi
 * @Date 2018/3/20
 */
@RestController
@RequestMapping("/Member")
public class MemberController {
    @Autowired
    private MemberService memberService;
    @GetMapping("/list")
    public List<MemberEntity> getUserList() {
        return memberService.findAll();
    }
    @PostMapping(value="/save")
    public MemberEntity addMember(@RequestBody MemberEntity member) {
        Log.debug("xxxxx");
        //@RequestBody 会自动把前端传的json转换成user对象
//        MemberEntity m = new MemberEntity();
//        m.setName(member.getName());
//        m.setBirthday(member.getBirthday());
//        m.setAddress(member.getAddress());
//        m.setStoreId(member.getStoreId());
//        m.setHeadUrl(member.getHeadUrl());
//        m.setCreateTime(System.currentTimeMillis());
//        Log.debug(m.toString());
        return memberService.save(member);
       // return "success";
    }

}
