package com.cci.projectx.core.repository;

import com.cci.projectx.core.entity.DiscussInvite;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DiscussInviteRepository {
    int deleteByPrimaryKey(@Param("id") Long id);

    int insert(@Param("discussinvite") DiscussInvite discussinvite);

    int insertSelective(@Param("discussinvite") DiscussInvite discussinvite);

    DiscussInvite selectByPrimaryKey(@Param("id") Long id);

    int updateByPrimaryKeySelective(@Param("discussinvite") DiscussInvite discussinvite);

    int updateByPrimaryKey(@Param("discussinvite") DiscussInvite discussinvite);

    int selectCount(@Param("discussinvite") DiscussInvite discussinvite);

    List<DiscussInvite> selectPage(@Param("discussinvite") DiscussInvite discussinvite, @Param("pageable") Pageable pageable);
}