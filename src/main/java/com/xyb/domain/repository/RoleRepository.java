package com.xyb.domain.repository;

import com.xyb.domain.entity.AccountInfoEntity;
import com.xyb.domain.entity.RoleInfoEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface RoleRepository extends JpaRepository<RoleInfoEntity, Long> {
 Page<RoleInfoEntity> findByName(String name, Pageable pageable);
   RoleInfoEntity findById(long id);
}
