package com.xyb.domain.repository;

import com.xyb.domain.entity.LaundryExpertEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LaundryExpertRepository extends JpaRepository<LaundryExpertEntity, Long> {
    Page<LaundryExpertEntity> findByPhone(String phone, Pageable pageable);

    Page<LaundryExpertEntity> findByName(String name, Pageable pageable);

    LaundryExpertEntity findByName(String name);

    LaundryExpertEntity findByPhone(String phone);

    Page<LaundryExpertEntity> findByStoreId(long storeId, Pageable pageable);

    Page<LaundryExpertEntity> findByStoreIdAndPhone(long storeId, String phone, Pageable pageable);

    Page<LaundryExpertEntity> findByStoreIdAndName(long storeId, String name, Pageable pageable);
    Page<LaundryExpertEntity> findByPhoneAndName(String phone, String name, Pageable pageable);

    LaundryExpertEntity findByStoreIdAndId(long storeId, long id);
    Page<LaundryExpertEntity> findByStoreIdAndPhoneAndName(long storeId, String phone, String name, Pageable pageable);
}
