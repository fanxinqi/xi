package com.xyb.service.impl;

import com.xyb.domain.entity.RoleInfoEntity;
import com.xyb.domain.repository.RoleRepository;
import com.xyb.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoleImpl implements RoleService {
    @Autowired
    private RoleRepository roleRepository;

    @Override
    public Page<RoleInfoEntity> findAll(Pageable pageable) {
        return roleRepository.findAll(pageable);
    }

    @Override
    public List<RoleInfoEntity> findAll() {
        return roleRepository.findAll();
    }

    @Override
    public RoleInfoEntity save(RoleInfoEntity entity) {
        return roleRepository.save(entity);
    }

    @Override
    public void delete(RoleInfoEntity entity) {
        roleRepository.delete(entity);
    }

    @Override
    public void deleteById(Long id) {
roleRepository.deleteById(id);
    }

    @Override
    public Optional<RoleInfoEntity> findById(Long id) {
        return roleRepository.findById(id);
    }

    @Override
    public Page<RoleInfoEntity> findByName(String name, Pageable pageable) {
        return roleRepository.findByName(name,pageable);
    }
}
