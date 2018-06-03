package com.xyb.domain.repository;

import com.xyb.domain.entity.AccountInfoEntity;
import com.xyb.domain.entity.AppendixEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppendixRepository extends JpaRepository<AppendixEntity, Long> {
    AppendixEntity findByName(String name);
}
