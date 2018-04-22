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
    Page<MemberEntity> findAll(Pageable pageable);

    MemberEntity save(MemberEntity entity);

    void delete(MemberEntity entity);

    void deleteById(Long id);

    Optional<MemberEntity> findById(Long id);

    Page<MemberEntity> findByName(String name, Pageable pageable);

    Page<MemberEntity> findByStoreId(long storeId, Pageable pageable);

    MemberEntity findTopByStoreId(long storeId);

    Page<MemberEntity> findByPhone(String phone, Pageable pageable);

    MemberEntity findByStoreIdAndPhone(long storeId, String phone);

    MemberEntity findByStoreIdAndId(long storeId, long id);

    Page<MemberEntity> findByStoreIdAndName(long storeId, String name, Pageable pageable);
}
