package com.xyb.service;

import com.xyb.domain.entity.ClothesCategoryEntity;
import com.xyb.domain.entity.ClothesGoodsEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * @Author fanxinqi
 * @Date 2018/3/29
 */

public interface ChothesGoodsService {
    Page<ClothesGoodsEntity> findAll(Pageable pageable);

    ClothesGoodsEntity save(ClothesGoodsEntity entity);

    void delete(ClothesGoodsEntity entity);

    void deleteById(Long id);

    Optional<ClothesGoodsEntity> findById(Long id);

    ClothesGoodsEntity findByName(String name);
}
