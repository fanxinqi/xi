package com.xyb.domain.repository;

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

public interface StorageEntityRepository extends JpaRepository<StoreEntity, Long> {
    StoreEntity findByName(String name);

    List<StoreEntity> findFirst10ByStoreIdAndUsableState(long storeId,int usableState);

}
