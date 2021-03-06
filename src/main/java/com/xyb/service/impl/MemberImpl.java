package com.xyb.service.impl;

import com.xyb.domain.entity.MemberEntity;
import com.xyb.domain.entity.UserEntity;
import com.xyb.domain.repository.MemberRepository;
import com.xyb.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

/**
 * @Author fanxinqi
 * @Date 2018/3/20
 */
@Service
public class MemberImpl implements MemberService {
    @Autowired
    private MemberRepository memberRepository;

    @Override
    public Page<MemberEntity> findAll(Pageable pageable) {
        return memberRepository.findAll(pageable);
    }

    @Override
    public MemberEntity save(MemberEntity entity) {
        return memberRepository.save(entity);
    }

    @Override
    public void delete(MemberEntity entity) {
        memberRepository.delete(entity);
    }

    @Override
    public void deleteById(Long id) {
        memberRepository.deleteById(id);
    }

    @Override
    public Optional<MemberEntity> findById(Long id) {
        return memberRepository.findById(id);
    }

    @Override
    public Page<MemberEntity> findByName(String name, Pageable pageable) {
        return memberRepository.findByName(name, pageable);
    }

    @Override
    public Page<MemberEntity> findByStoreId(long storeId, Pageable pageable) {
        return memberRepository.findByStoreId(storeId, pageable);
    }

    @Override
    public MemberEntity findTopByStoreId(long storeId) {
        return memberRepository.findTopByStoreId(storeId);
    }

    @Override
    public Page<MemberEntity> findByPhone(String phone, Pageable pageable) {
        return memberRepository.findByPhone(phone, pageable);
    }

    @Override
    public MemberEntity findByStoreIdAndPhone(long storeId, String phone) {
        return memberRepository.findByStoreIdAndPhone(storeId, phone);
    }

    @Override
    public MemberEntity findByStoreIdAndId(long storeId, long id) {
        return memberRepository.findByStoreIdAndId(storeId, id);
    }

    @Override
    public Page<MemberEntity> findByStoreIdAndName(long storeId, String name, Pageable pageable) {
        return memberRepository.findByStoreIdAndName(storeId, name, pageable);
    }
}
