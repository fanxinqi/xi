package com.xyb.domain.repository;

import com.xyb.domain.entity.ClothesCategoryEntity;
import com.xyb.domain.entity.ClothesOrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ClothesOrderRepository extends JpaRepository<ClothesOrderEntity, Long> {
    Optional<ClothesOrderEntity> findByPhone(String phone);

    @Query("from ClothesOrderEntity cc where cc.phone=:phone")
    Optional<ClothesOrderEntity> findOrder(@Param("phone") String phone);
}
