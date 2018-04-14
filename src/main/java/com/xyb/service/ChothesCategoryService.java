package com.xyb.service;

import com.xyb.domain.entity.ClothesCategoryEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @Author fanxinqi
 * @Date 2018/3/29
 */

public interface ChothesCategoryService {
    Page<ClothesCategoryEntity> findAll(Pageable pageable);

    ClothesCategoryEntity save(ClothesCategoryEntity entity);

    void delete(ClothesCategoryEntity entity);

    void deleteById(Long id);

    Optional<ClothesCategoryEntity> findById(Long id);
    Optional<ClothesCategoryEntity> findByName(String name);
}
