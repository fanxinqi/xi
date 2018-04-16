package com.xyb.service;

import com.xyb.domain.entity.MemberEntity;
import com.xyb.domain.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

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
    Optional<MemberEntity> findByName(String name);
    Page<MemberEntity> findByStoreId(long storeId,Pageable pageable);
    MemberEntity findTopByStoreId(long storeId);
    MemberEntity findByPhone(String  phone);
    MemberEntity findByStoreIdAndPhone(long storeId,String  phone);
}
