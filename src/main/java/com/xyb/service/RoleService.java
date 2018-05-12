package com.xyb.service;


import com.xyb.domain.entity.RoleInfoEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface  RoleService {
    Page<RoleInfoEntity> findAll(Pageable pageable);
    List<RoleInfoEntity> findAll();
    RoleInfoEntity save(RoleInfoEntity entity);

    void delete(RoleInfoEntity entity);

    void deleteById(Long id);

    Optional<RoleInfoEntity> findById(Long id);
    Page<RoleInfoEntity> findByName(String name, Pageable pageable);
}
