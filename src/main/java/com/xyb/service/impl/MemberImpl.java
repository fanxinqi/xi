package com.xyb.service.impl;

import com.xyb.domain.entity.MemberEntity;
import com.xyb.domain.entity.UserEntity;
import com.xyb.domain.repository.MemberRepository;
import com.xyb.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

/**
 * @Author fanxinqi
 * @Date 2018/3/20
 */
@Service
public class MemberImpl implements  MemberService {
    @Autowired
    private MemberRepository memberRepository;
    @Override
    public List<MemberEntity>findAll() {
        return memberRepository.findAll();
    }

    @Override
    public MemberEntity save(MemberEntity entity) {
        return memberRepository.save(entity);
    }

    @Override
    public void delete(MemberEntity entity) {

    }

    @Override
    public void deleteById(Long id) {

    }

    @Override
    public Optional<MemberEntity> findById(Long id) {
        return null;
    }
}
