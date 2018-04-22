package com.xyb.domain.repository;

import com.xyb.domain.entity.ClothesCategoryEntity;
import com.xyb.domain.entity.ClothesOrderEntity;
import com.xyb.domain.entity.StoreEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StoreRepository  extends JpaRepository<StoreEntity, Long> {
    StoreEntity findByName(String name);
    Page<StoreEntity> findAllByName(String name, Pageable pageable);
}
