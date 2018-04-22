package com.xyb.service.impl;

import com.xyb.domain.entity.PaymentEntity;
import com.xyb.domain.repository.PaymentRepository;
import com.xyb.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class PaymentImpl implements PaymentService {
    @Autowired
    private PaymentRepository paymentRepository;

    @Override
    public List<PaymentEntity> findAll() {
        return paymentRepository.findAll();
    }

    @Override
    public PaymentEntity save(PaymentEntity entity) {
        return paymentRepository.save(entity);
    }

    @Override
    public void delete(PaymentEntity entity) {
paymentRepository.delete(entity);
    }

    @Override
    public void deleteById(Long id) {
        paymentRepository.deleteById(id);
    }

    @Override
    public Optional<PaymentEntity> findById(Long id) {
        return  paymentRepository.findById(id);
    }

    @Override
    public PaymentEntity findByName(String name) {
        return paymentRepository.findByName(name);
    }
}
