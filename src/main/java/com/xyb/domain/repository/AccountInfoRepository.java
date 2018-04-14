package com.xyb.domain.repository;

import com.xyb.domain.entity.AccountInfoEntity;
import com.xyb.domain.entity.ClothesCategoryEntity;
import org.hibernate.sql.Update;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountInfoRepository extends JpaRepository<AccountInfoEntity, Long> {
 AccountInfoEntity findByName(String name);
}
