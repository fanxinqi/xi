package com.xyb.service;

import com.xyb.domain.entity.CommonEnumEntity;

import java.util.List;
import java.util.Optional;

public interface CommonEnumService {
    List<CommonEnumEntity> findAll();

    List<CommonEnumEntity> findByType(int type);

    CommonEnumEntity save(CommonEnumEntity entity);

    void delete(CommonEnumEntity entity);

    void deleteById(Long id);

    Optional<CommonEnumEntity> findById(Long id);

    CommonEnumEntity findByName(String name);

}
