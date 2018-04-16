package com.xyb.domain.model;

import com.xyb.domain.entity.MemberCategoryEntity;
import com.xyb.domain.entity.MemberEntity;
import org.springframework.data.domain.Page;

import java.util.ArrayList;

public class MemberAndCategory {
    public Page<MemberEntity> getMemberEntity() {
        return memberEntity;
    }

    public void setMemberEntity(Page<MemberEntity> memberEntity) {
        this.memberEntity = memberEntity;
    }

    private Page<MemberEntity> memberEntity;



    public ArrayList<MemberCategoryEntity> getMemberCategoryEntity() {
        return memberCategoryEntity;
    }

    public void setMemberCategoryEntity(ArrayList<MemberCategoryEntity> memberCategoryEntity) {
        this.memberCategoryEntity = memberCategoryEntity;
    }

    private ArrayList<MemberCategoryEntity> memberCategoryEntity;



}
