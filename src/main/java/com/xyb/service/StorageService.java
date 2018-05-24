package com.xyb.service;

import com.xyb.domain.entity.StorageEntity;
import com.xyb.domain.entity.StoreEntity;
import com.xyb.domain.entity.UserEntity;

import java.util.List;
import java.util.Optional;

/**
 * @Author lian
 * @Date 2018/3/20
 */

public interface StorageService {
    List<StorageEntity> findAll();

    StorageEntity save(StorageEntity entity);

    void delete(StorageEntity entity);

    void deleteById(Long id);

    Optional<StorageEntity> findById(Long id);

    StorageEntity findByName(String name);

    List<StorageEntity> findByStoreIdAndUsableState(long storeId, int usableState);
    int getCount(String preName);
}
