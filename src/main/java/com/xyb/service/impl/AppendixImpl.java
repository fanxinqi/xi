package com.xyb.service.impl;

import com.xyb.domain.entity.AppendixEntity;
import com.xyb.domain.repository.AccountInfoRepository;
import com.xyb.domain.repository.AppendixRepository;
import com.xyb.service.AccountInfoService;
import com.xyb.service.AppendixService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AppendixImpl implements AppendixService {
    @Autowired
    private AppendixRepository appendixRepository;
    @Override
    public Page<AppendixEntity> findAll(Pageable pageable) {
        return appendixRepository.findAll(pageable);
    }

    @Override
    public AppendixEntity save(AppendixEntity entity) {
        return appendixRepository.save(entity);
    }

    @Override
    public void delete(AppendixEntity entity) {
        appendixRepository.delete(entity);
    }

    @Override
    public void deleteById(Long id) {
        appendixRepository.deleteById(id);
    }

    @Override
    public Optional<AppendixEntity> findById(Long id) {
        return appendixRepository.findById(id);
    }

    @Override
    public AppendixEntity findByName(String name) {
        return appendixRepository.findByName(name);
    }
}
