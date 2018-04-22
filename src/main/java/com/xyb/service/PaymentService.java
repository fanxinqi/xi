package com.xyb.service;

import com.xyb.domain.entity.PaymentEntity;

import java.util.List;
import java.util.Optional;

public interface PaymentService {
    List<PaymentEntity> findAll();

    PaymentEntity save(PaymentEntity entity);

    void delete(PaymentEntity entity);

    void deleteById(Long id);

   Optional<PaymentEntity> findById(Long id);

    PaymentEntity findByName(String name);

}
