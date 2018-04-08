package com.xyb.service;

import com.xyb.domain.entity.MemberEntity;
import com.xyb.domain.entity.StoreEntity;

import java.util.List;
import java.util.Optional;

public interface StoreService {
    List<StoreEntity> findAll();

    StoreEntity save(StoreEntity entity);

    void delete(StoreEntity entity);

    void deleteById(Long id);

    Optional<StoreEntity> findById(Long id);
    Optional<StoreEntity> findByName(String name);
}
