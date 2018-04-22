package com.xyb.domain.repository;

import com.xyb.domain.entity.ClothesOrderEntity;
import com.xyb.domain.entity.FileEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileSrcRepository extends JpaRepository<FileEntity, Long> {

}
