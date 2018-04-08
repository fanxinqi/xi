package com.xyb.domain.repository;

import com.xyb.domain.entity.ClothesCategoryEntity;
import com.xyb.domain.entity.ClothesOrderEntity;
import com.xyb.domain.entity.StoreEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StoreRepository  extends JpaRepository<StoreEntity, Long> {
    Optional<StoreEntity> findByName(String name);
}
