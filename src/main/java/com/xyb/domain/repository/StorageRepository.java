package com.xyb.domain.repository;

import com.xyb.domain.entity.StorageEntity;
import com.xyb.domain.entity.StoreEntity;
import com.xyb.domain.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @Author lian
 * @Date 2018/3/20
 */

public interface StorageRepository extends JpaRepository<StorageEntity, Long> {
    StorageEntity findByName(String name);

    List<StorageEntity> findByStoreIdAndUsableState(long storeId,int usableState);

}
