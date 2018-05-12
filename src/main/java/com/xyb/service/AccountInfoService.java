package com.xyb.service;

import com.xyb.domain.entity.AccountInfoEntity;
import com.xyb.domain.entity.ClothesCategoryEntity;
import com.xyb.domain.entity.MemberEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface AccountInfoService {
    List<AccountInfoEntity> findAll();
    Page<AccountInfoEntity> findAll(Pageable pageable);

    AccountInfoEntity save(AccountInfoEntity entity);

    void delete(AccountInfoEntity entity);

    void deleteById(Long id);

    Optional<AccountInfoEntity> findById(Long id);

    AccountInfoEntity findByName(String name);
    Page<AccountInfoEntity> findByName(String name,Pageable pageable);
}
