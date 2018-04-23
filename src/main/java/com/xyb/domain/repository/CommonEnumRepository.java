package com.xyb.domain.repository;

import com.xyb.domain.entity.CommonEnumEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * A
 *
 * @Author fanxinqi
 * @Date 2018/3/20
 */

public interface CommonEnumRepository extends JpaRepository<CommonEnumEntity, Long> {
    CommonEnumEntity findByName(String name);

    List<CommonEnumEntity> findByType(int type);
}
