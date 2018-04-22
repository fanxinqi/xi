package com.xyb.service.impl;

import com.xyb.domain.entity.FileEntity;
import com.xyb.domain.repository.ClothesOrderRepository;
import com.xyb.domain.repository.FileSrcRepository;
import com.xyb.service.FileSrcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FileSrcImpl implements FileSrcService {
    @Autowired
    private FileSrcRepository fileSrcRepository;

    @Override
    public Page<FileEntity> findAll(Pageable pageable) {
        return fileSrcRepository.findAll(pageable);
    }

    @Override
    public FileEntity save(FileEntity entity) {
        return fileSrcRepository.save(entity);
    }

    @Override
    public void delete(FileEntity entity) {
        fileSrcRepository.delete(entity);

    }

    @Override
    public void deleteById(Long id) {
        fileSrcRepository.deleteById(id);
    }

    @Override
    public Optional<FileEntity> findById(Long id) {
        return fileSrcRepository.findById(id);
    }
}
