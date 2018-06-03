package com.xyb.service;

import com.xyb.domain.entity.AppendixEntity;
import com.xyb.domain.entity.ClothesGoodsEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface AppendixService {
    Page<AppendixEntity> findAll(Pageable pageable);

    AppendixEntity save(AppendixEntity entity);

    void delete(AppendixEntity entity);

    void deleteById(Long id);

    Optional<AppendixEntity> findById(Long id);

    AppendixEntity findByName(String name);
}
