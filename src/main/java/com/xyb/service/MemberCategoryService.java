package com.xyb.service;

import com.xyb.domain.entity.MemberCategoryEntity;
import com.xyb.domain.entity.MemberEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * @Author fanxinqi
 * @Date 2018/3/29
 */

public interface MemberCategoryService {
    List<MemberCategoryEntity> findAll();
    MemberCategoryEntity save(MemberCategoryEntity entity);
    void delete(MemberCategoryEntity entity);
    void deleteById(Long id);
    Optional<MemberCategoryEntity> findById(Long id);
    Optional<MemberCategoryEntity> findByName(String name);
}
