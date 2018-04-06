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
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 演示user在内存中的操作
 *
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

    @PostMapping(value = "/save_or_update")
    public MemberEntity addMember(@RequestBody MemberEntity member) {
        return memberService.save(member);
    }

    @PostMapping(value = "/search_by_name")
    public Optional<MemberEntity> searchMemberByName(@RequestParam(value = "name", required = true) String name) {
        return memberService.findByName(name);
    }

    @PostMapping(value = "/delete_by_id")
    public void deleteMemberById(@RequestParam(value = "id", required = true) long id) {
        memberService.deleteById(id);
    }

}
