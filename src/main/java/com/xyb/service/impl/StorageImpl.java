package com.xyb.service.impl;

import com.xyb.domain.entity.StorageEntity;
import com.xyb.domain.entity.UserEntity;
import com.xyb.domain.repository.StorageRepository;
import com.xyb.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @Author lian
 * @Date 2018/3/20
 */
@Service
public class StorageImpl implements StorageService {
    @Autowired
    private StorageRepository storageRepository;

    @Override
    public List<StorageEntity> findAll() {
        return storageRepository.findAll();
    }

    @Override
    public StorageEntity save(StorageEntity entity) {
        return storageRepository.save(entity);
    }

    @Override
    public void delete(StorageEntity entity) {
        storageRepository.delete(entity);
    }
    @Override
    public void deleteById(Long id) {
        storageRepository.deleteById(id);
    }

    @Override
    public Optional<StorageEntity> findById(Long id) {
        return storageRepository.findById(id);
    }

    @Override
    public StorageEntity findByName(String name) {
        return storageRepository.findByName(name);
    }

    @Override
    public List<StorageEntity> findByStoreIdAndUsableState(long storeId, int usableState) {
        return storageRepository.findByStoreIdAndUsableState(storeId,usableState);
    }

    @Override
    public int getCount(String name) {
        return storageRepository.countAllByNameContains(name);
    }
}
