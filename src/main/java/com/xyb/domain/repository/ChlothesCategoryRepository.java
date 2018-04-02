package com.xyb.domain.repository;

import com.xyb.domain.entity.ClothesCategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * @Author lian
 * @Date 2018/3/20
 */

public interface ChlothesCategoryRepository extends JpaRepository<ClothesCategoryEntity, Long> {
    ClothesCategoryEntity findByName(String name);

    @Query("from ClothesCategoryEntity cc where cc.name=:name")
    ClothesCategoryEntity findUser(@Param("name") String name);
}
