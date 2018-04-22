package com.xyb.service.impl;

import com.xyb.domain.entity.ClothesOrderEntity;
import com.xyb.domain.repository.ClothesOrderRepository;
import com.xyb.service.ClothesOrderService;
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
public class  ClothesOrderImpl implements ClothesOrderService {
    @Autowired
    private ClothesOrderRepository clothesOrderRepository;

    @Override
    public Page<ClothesOrderEntity> findAll(Pageable pageable) {
        return clothesOrderRepository.findAll(pageable);
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
    public Page<ClothesOrderEntity> findByPhone(String phone, Pageable pageable) {
        return clothesOrderRepository.findByPhone(phone,pageable);
    }

    @Override
    public Page<ClothesOrderEntity> findByStoreId(long storeId, Pageable pageable) {
        return clothesOrderRepository.findByStoreId(storeId,pageable);
    }

    @Override
    public Page<ClothesOrderEntity> findByStoreIdAndPhone(long storeId, String phone, Pageable pageable) {
        return clothesOrderRepository.findByStoreIdAndPhone(storeId,phone,pageable);
    }

    @Override
    public ClothesOrderEntity findByStoreIdAndId(long storeId, long id) {
        return clothesOrderRepository.findByStoreIdAndId(storeId,id);
    }


}
