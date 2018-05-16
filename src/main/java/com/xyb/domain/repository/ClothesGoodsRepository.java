package com.xyb.domain.repository;

import com.xyb.domain.entity.ClothesCategoryEntity;
import com.xyb.domain.entity.ClothesGoodsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

/**
 * @Author lian
 * @Date 2018/3/20
 */

public interface ClothesGoodsRepository extends JpaRepository<ClothesGoodsEntity, Long> {
    ClothesGoodsEntity findByName(String name);
}
