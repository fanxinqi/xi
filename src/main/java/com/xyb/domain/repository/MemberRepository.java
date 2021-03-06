package com.xyb.domain.repository;

import com.xyb.domain.entity.MemberCategoryEntity;
import com.xyb.domain.entity.MemberEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**
 * A
 *
 * @Author fanxinqi
 * @Date 2018/3/20
 */

public interface MemberRepository extends JpaRepository<MemberEntity, Long> {
    Page<MemberEntity> findByName(String name, Pageable pageable);

    @Query("from MemberEntity u where u.name =:name ")
    Optional<MemberEntity> findUser(@Param("name") String name);

    Page<MemberEntity> findByStoreId(long storeId, Pageable pageable);

    MemberEntity findTopByStoreId(long storeId);

    MemberEntity findByStoreIdAndPhone(long storeId, String phone);

    Page<MemberEntity> findByPhone(String phone, Pageable pageable);

    MemberEntity findByStoreIdAndId(long storeId, long id);

    Page<MemberEntity> findByStoreIdAndName(long storeId, String name, Pageable pageable);
//
//    @Query("from MemberEntity m  inner join MemberCategoryEntity mc where m.member_category_id = mc.id")
//    public List<Object[]> findList();
}
