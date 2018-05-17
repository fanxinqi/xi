package com.xyb.service.impl;

import com.xyb.domain.entity.CommonEnumEntity;
import com.xyb.domain.repository.CommonEnumRepository;
import com.xyb.service.CommonEnumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
class CommonEnumImpl implements CommonEnumService{
    @Autowired
    private CommonEnumRepository paymentRepository;

    @Override
    public List<CommonEnumEntity> findAll() {
        return paymentRepository.findAll();
    }

    @Override
    public List<CommonEnumEntity> findByType(int type) {
        return paymentRepository.findByType(type);
    }

    @Override
    public CommonEnumEntity findByTypeAndIsDefault(int type, int defaulted) {
        return paymentRepository.findByTypeAndIsDefault(type,defaulted);
    }


    @Override
    public CommonEnumEntity save(CommonEnumEntity entity) {
        return paymentRepository.save(entity);
    }

    @Override
    public void delete(CommonEnumEntity entity) {
paymentRepository.delete(entity);
    }

    @Override
    public void deleteById(Long id) {
        paymentRepository.deleteById(id);
    }

    @Override
    public Optional<CommonEnumEntity> findById(Long id) {
        return  paymentRepository.findById(id);
    }

    @Override
    public CommonEnumEntity findByName(String name) {
        return paymentRepository.findByName(name);
    }
}
