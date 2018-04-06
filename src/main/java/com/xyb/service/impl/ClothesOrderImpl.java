package com.xyb.service.impl;

import com.xyb.domain.entity.ClothesCategoryEntity;
import com.xyb.domain.entity.ClothesOrderEntity;
import com.xyb.domain.repository.ClothesCategoryRepository;
import com.xyb.domain.repository.ClothesOrderRepository;
import com.xyb.service.ChothesCategoryService;
import com.xyb.service.ClothesOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @Author fanxinqi
 * @Date 2018/3/20
 */
@Service
public class ClothesOrderImpl implements ClothesOrderService {
    @Autowired
    private ClothesOrderRepository clothesOrderRepository;
    @Override
    public List<ClothesOrderEntity> findAll() {
        return clothesOrderRepository.findAll();
    }

    @Override
    public ClothesOrderEntity save(ClothesOrderEntity entity) {
        return clothesOrderRepository.save(entity);
    }

    @Override
    public void delete(ClothesOrderEntity entity) {
           clothesOrderRepository.delete(entity);
    }

    @Override
    public void deleteById(Long id) {
        clothesOrderRepository.deleteById(id);

    }

    @Override
    public Optional<ClothesOrderEntity> findById(Long id) {
        return clothesOrderRepository.findById(id);
    }

    @Override
    public Optional<ClothesOrderEntity> findByPhone(String phone) {
        return clothesOrderRepository.findByPhone(phone);
    }
}
