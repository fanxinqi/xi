package com.xyb.service.impl;

import com.xyb.domain.entity.ClothesCategoryEntity;
import com.xyb.domain.repository.ChlothesCategoryRepository;
import com.xyb.service.ChothesCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @Author fanxinqi
 * @Date 2018/3/20
 */
@Service
public class ChothesCategoryImpl implements ChothesCategoryService {
    @Autowired
    private ChlothesCategoryRepository chlothesCategoryRepository;
    @Override
    public List<ClothesCategoryEntity> findAll() {
        return chlothesCategoryRepository.findAll();
    }

    @Override
    public ClothesCategoryEntity save(ClothesCategoryEntity entity) {
        return chlothesCategoryRepository.save(entity);
    }

    @Override
    public void delete(ClothesCategoryEntity entity) {
        chlothesCategoryRepository.delete(entity);
    }

    @Override
    public void deleteById(Long id) {
        chlothesCategoryRepository.deleteById(id);
    }

    @Override
    public Optional<ClothesCategoryEntity> findById(Long id) {
        return chlothesCategoryRepository.findById(id);
    }
}
