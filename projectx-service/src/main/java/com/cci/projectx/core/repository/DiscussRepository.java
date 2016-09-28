package com.cci.projectx.core.repository;

import com.cci.projectx.core.entity.Discuss;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DiscussRepository {
    int deleteByPrimaryKey(@Param("id") Long id);

    int insert(@Param("discuss") Discuss discuss);

    int insertSelective(@Param("discuss") Discuss discuss);

    Discuss selectByPrimaryKey(@Param("id") Long id);

    int updateByPrimaryKeySelective(@Param("discuss") Discuss discuss);

    int updateByPrimaryKey(@Param("discuss") Discuss discuss);

    int selectCount(@Param("discuss") Discuss discuss);

    List<Discuss> selectPage(@Param("discuss") Discuss discuss, @Param("pageable") Pageable pageable);
}