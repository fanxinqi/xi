package com.xyb.service;

import com.xyb.domain.entity.FileEntity;
import com.xyb.domain.entity.LaundryExpertEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface LaundryExpectService {
    Page<LaundryExpertEntity> findAll(Pageable pageable);

    LaundryExpertEntity save(LaundryExpertEntity entity);

    void delete(LaundryExpertEntity entity);

    void deleteById(Long id);

    Optional<LaundryExpertEntity> findById(Long id);

    Page<LaundryExpertEntity> findByPhone(String phone, Pageable pageable);

    Page<LaundryExpertEntity> findByName(String name, Pageable pageable);

    LaundryExpertEntity findByName(String name);
    LaundryExpertEntity findByPhone(String phone);

    Page<LaundryExpertEntity> findByStoreId(long storeId, Pageable pageable);

    Page<LaundryExpertEntity> findByStoreIdAndPhone(long storeId, String phone, Pageable pageable);

    Page<LaundryExpertEntity> findByStoreIdAndName(long storeId, String name, Pageable pageable);

    LaundryExpertEntity findByStoreIdAndId(long storeId, long id);
}
