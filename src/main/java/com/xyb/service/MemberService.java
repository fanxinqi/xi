package com.xyb.service;

import com.xyb.domain.entity.MemberEntity;
import com.xyb.domain.entity.UserEntity;

import java.util.List;
import java.util.Optional;

/**
 * @Author fanxinqi
 * @Date 2018/3/29
 */

public interface MemberService {
    List<MemberEntity> findAll();

    MemberEntity save(MemberEntity entity);

    void delete(MemberEntity entity);

    void deleteById(Long id);

    Optional<MemberEntity> findById(Long id);
}
