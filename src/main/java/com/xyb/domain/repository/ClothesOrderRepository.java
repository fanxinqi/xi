package com.xyb.domain.repository;

import com.xyb.domain.entity.ClothesCategoryEntity;
import com.xyb.domain.entity.ClothesOrderEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ClothesOrderRepository extends JpaRepository<ClothesOrderEntity, Long> {
    Page<ClothesOrderEntity> findByPhone(String phone, Pageable pageable);

    Page<ClothesOrderEntity> findByStoreId(long storeId, Pageable pageable);

    Page<ClothesOrderEntity> findByStoreIdAndPhone(long storeId, String phone, Pageable pageable);

    ClothesOrderEntity findByStoreIdAndId(long storeId, long id);
//
//    @Query(" from ClothesOrderEntity cc where cc.phone=:phone")
//    List<ClothesOrderEntity> findOrder(@Param("phone") String phone);
}
