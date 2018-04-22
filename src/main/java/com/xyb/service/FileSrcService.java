package com.xyb.service;

import com.xyb.domain.entity.ClothesOrderEntity;
import com.xyb.domain.entity.FileEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface FileSrcService {
    Page<FileEntity> findAll(Pageable pageable);

    FileEntity save(FileEntity entity);

    void delete(FileEntity entity);

    void deleteById(Long id);

    Optional<FileEntity> findById(Long id);
}
