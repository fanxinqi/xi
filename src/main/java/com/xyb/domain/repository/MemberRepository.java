package com.xyb.domain.repository;

import com.xyb.domain.entity.MemberCategoryEntity;
import com.xyb.domain.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
/**
 * @Author fanxinqi
 * @Date 2018/3/20
 */

public interface MemberRepository extends JpaRepository<MemberEntity, Long> {
    MemberEntity findByName(String name);

    @Query("from MemberEntity u where u.name=:name")
    MemberEntity findUser(@Param("name") String name);

//
//    @Query("from MemberEntity m  inner join MemberCategoryEntity mc where m.member_category_id = mc.id")
//    public List<Object[]> findList();
}
