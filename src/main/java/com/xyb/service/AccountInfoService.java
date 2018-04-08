package com.xyb.service;

import com.xyb.domain.entity.AccountInfoEntity;
import com.xyb.domain.entity.ClothesCategoryEntity;

import java.util.List;
import java.util.Optional;

public interface AccountInfoService {
    List<AccountInfoEntity> findAll();

    AccountInfoEntity save(AccountInfoEntity entity);

    void delete(AccountInfoEntity entity);

    void deleteById(Long id);

    Optional<AccountInfoEntity> findById(Long id);

    AccountInfoEntity findByName(String name);
}
