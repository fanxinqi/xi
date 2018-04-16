package com.xyb.service.impl;

import com.xyb.domain.entity.MemberCategoryEntity;
import com.xyb.domain.repository.ClothesOrderRepository;
import com.xyb.domain.repository.MemberCategoryRepository;
import com.xyb.service.MemberCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class MemberCategoryImpl implements MemberCategoryService {
    @Autowired
    private MemberCategoryRepository memberCategoryRepository;
    @Override
    public List<MemberCategoryEntity> findAll() {
        return memberCategoryRepository.findAll();
    }

    @Override
    public MemberCategoryEntity save(MemberCategoryEntity entity) {
        return memberCategoryRepository.save(entity);
    }

    @Override
    public void delete(MemberCategoryEntity entity) {
         memberCategoryRepository.delete(entity);
    }

    @Override
    public void deleteById(Long id) {
         memberCategoryRepository.deleteById(id);
    }

    @Override
    public Optional<MemberCategoryEntity> findById(Long id) {
        return memberCategoryRepository.findById(id);
    }

    @Override
    public Optional<MemberCategoryEntity> findByName(String name) {
        return memberCategoryRepository.findByName(name);
    }
}
