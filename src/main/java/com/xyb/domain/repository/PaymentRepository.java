package com.xyb.domain.repository;

import com.xyb.domain.entity.MemberEntity;
import com.xyb.domain.entity.PaymentEntity;
import com.xyb.domain.entity.RoleInfoEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

/**
 * A
 *
 * @Author fanxinqi
 * @Date 2018/3/20
 */

public interface PaymentRepository extends JpaRepository<PaymentEntity, Long> {
    PaymentEntity findByName(String name);
}
