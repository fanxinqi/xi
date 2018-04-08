package com.xyb.service.impl;

import com.xyb.domain.entity.StoreEntity;
import com.xyb.domain.repository.MemberRepository;
import com.xyb.domain.repository.StoreRepository;
import com.xyb.service.MemberService;
import com.xyb.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StoreImpl implements StoreService {
    @Autowired
    private StoreRepository storeRepository;

    @Override
    public List<StoreEntity> findAll() {
        return storeRepository.findAll();
    }

    @Override
    public StoreEntity save(StoreEntity entity) {
        return storeRepository.save(entity);
    }

    @Override
    public void delete(StoreEntity entity) {
        storeRepository.delete(entity);
    }

    @Override
    public void deleteById(Long id) {
        storeRepository.deleteById(id);
    }

    @Override
    public Optional<StoreEntity> findById(Long id) {
        return storeRepository.findById(id);
    }

    @Override
    public Optional<StoreEntity> findByName(String name) {
        return storeRepository.findByName(name);
    }
}
