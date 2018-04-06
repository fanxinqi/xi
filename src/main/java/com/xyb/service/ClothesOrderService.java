package com.xyb.service;


import com.xyb.domain.entity.ClothesOrderEntity;

import java.util.List;
import java.util.Optional;

public interface ClothesOrderService {
    List<ClothesOrderEntity> findAll();

    ClothesOrderEntity save(ClothesOrderEntity entity);

    void delete(ClothesOrderEntity entity);

    void deleteById(Long id);

    Optional<ClothesOrderEntity> findById(Long id);
    Optional<ClothesOrderEntity> findByPhone(String name);
}
