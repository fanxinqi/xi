package com.xyb.service;


import com.xyb.domain.entity.ClothesOrderEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.Optional;

public interface ClothesOrderService {
    Page<ClothesOrderEntity> findAll(Pageable pageable);

    ClothesOrderEntity save(ClothesOrderEntity entity);

    void delete(ClothesOrderEntity entity);

    void deleteById(Long id);

    Optional<ClothesOrderEntity> findById(Long id);
    Page<ClothesOrderEntity> findByPhone(String name,Pageable pageable);
    Page<ClothesOrderEntity> findByStoreId(long storeId,Pageable pageable);
    Page<ClothesOrderEntity> findByStoreIdAndPhone(long storeId,String phone,Pageable pageable);
    ClothesOrderEntity findByStoreIdAndId(long storeId,long id);
}
