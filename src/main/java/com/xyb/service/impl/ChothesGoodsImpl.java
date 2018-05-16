package com.xyb.service.impl;

import com.xyb.domain.entity.ClothesCategoryEntity;
import com.xyb.domain.entity.ClothesGoodsEntity;
import com.xyb.domain.repository.ClothesCategoryRepository;
import com.xyb.domain.repository.ClothesGoodsRepository;
import com.xyb.service.ChothesCategoryService;
import com.xyb.service.ChothesGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @Author fanxinqi
 * @Date 2018/3/20
 */
@Service
public class ChothesGoodsImpl implements ChothesGoodsService {

    @Autowired
    private ClothesGoodsRepository clothesGoodsRepository;

    @Override
    public Page<ClothesGoodsEntity> findAll(Pageable pageable) {
        return clothesGoodsRepository.findAll(pageable);
    }


    @Override
    public ClothesGoodsEntity save(ClothesGoodsEntity entity) {
        return clothesGoodsRepository.save(entity);
    }

    @Override
    public void delete(ClothesGoodsEntity entity) {
        clothesGoodsRepository.delete(entity);
    }

    @Override
    public void deleteById(Long id) {
        clothesGoodsRepository.deleteById(id);
    }

    @Override
    public Optional<ClothesGoodsEntity> findById(Long id) {
        return clothesGoodsRepository.findById(id);
    }

    @Override
    public ClothesGoodsEntity findByName(String name) {
        return clothesGoodsRepository.findByName(name);
    }
}
