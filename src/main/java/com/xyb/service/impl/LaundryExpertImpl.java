package com.xyb.service.impl;

import com.xyb.domain.entity.LaundryExpertEntity;
import com.xyb.domain.repository.LaundryExpertRepository;
import com.xyb.domain.repository.MemberCategoryRepository;
import com.xyb.service.LaundryExpectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class LaundryExpertImpl implements LaundryExpectService {
    @Autowired
    private LaundryExpertRepository laundryExpertRepository;
    @Override
    public Page<LaundryExpertEntity> findAll(Pageable pageable) {
        return laundryExpertRepository.findAll(pageable);
    }

    @Override
    public LaundryExpertEntity save(LaundryExpertEntity entity) {
        return laundryExpertRepository.save(entity);
    }

    @Override
    public void delete(LaundryExpertEntity entity) {
        laundryExpertRepository.delete(entity);
    }

    @Override
    public void deleteById(Long id) {
        laundryExpertRepository.deleteById(id);
    }

    @Override
    public Optional<LaundryExpertEntity> findById(Long id) {
        return laundryExpertRepository.findById(id);
    }

    @Override
    public Page<LaundryExpertEntity> findByPhone(String phone, Pageable pageable) {
        return laundryExpertRepository.findByPhone(phone,pageable);
    }

    @Override
    public Page<LaundryExpertEntity> findByName(String name, Pageable pageable) {
        return laundryExpertRepository.findByName(name,pageable);
    }

    @Override
    public LaundryExpertEntity findByName(String name) {
        return null;
    }

    @Override
    public LaundryExpertEntity findByPhone(String phone) {
        return null;
    }

    @Override
    public Page<LaundryExpertEntity> findByStoreId(long storeId, Pageable pageable) {
        return laundryExpertRepository.findByStoreId(storeId,pageable);
    }

    @Override
    public Page<LaundryExpertEntity> findByStoreIdAndPhone(long storeId, String phone, Pageable pageable) {
        return laundryExpertRepository.findByStoreIdAndPhone(storeId,phone,pageable);
    }

    @Override
    public Page<LaundryExpertEntity> findByStoreIdAndName(long storeId, String name, Pageable pageable) {
        return laundryExpertRepository.findByStoreIdAndPhone(storeId,name,pageable);
    }

    @Override
    public LaundryExpertEntity findByStoreIdAndId(long storeId, long id) {
        return laundryExpertRepository.findByStoreIdAndId(storeId,id);
    }
}
