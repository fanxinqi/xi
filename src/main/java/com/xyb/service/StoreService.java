package com.xyb.service;

import com.xyb.domain.entity.MemberEntity;
import com.xyb.domain.entity.StoreEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface StoreService {
    Page<StoreEntity> findAll(Pageable pageable);
    Page<StoreEntity> findAllByName(String name,Pageable pageable);
    List<StoreEntity> findAll();
    StoreEntity save(StoreEntity entity);

    void delete(StoreEntity entity);

    void deleteById(Long id);

    Optional<StoreEntity> findById(Long id);
    StoreEntity findByName(String name);
}
