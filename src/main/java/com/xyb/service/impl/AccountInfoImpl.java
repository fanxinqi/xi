package com.xyb.service.impl;

import com.xyb.domain.entity.AccountInfoEntity;
import com.xyb.domain.repository.AccountInfoRepository;
import com.xyb.service.AccountInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AccountInfoImpl implements AccountInfoService {
    @Autowired
    private AccountInfoRepository accountInfoRepository;

    @Override
    public List<AccountInfoEntity> findAll() {
        return accountInfoRepository.findAll();
    }

    @Override
    public AccountInfoEntity save(AccountInfoEntity entity) {
        return accountInfoRepository.save(entity);
    }

    @Override
    public void delete(AccountInfoEntity entity) {
        accountInfoRepository.delete(entity);

    }

    @Override
    public void deleteById(Long id) {
        accountInfoRepository.deleteById(id);
    }

    @Override
    public Optional<AccountInfoEntity> findById(Long id) {
        return accountInfoRepository.findById(id);
    }

    @Override
    public AccountInfoEntity findByName(String name) {
        return accountInfoRepository.findByName(name);
    }

}
