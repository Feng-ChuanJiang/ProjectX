package com.cci.projectx.core.repository;

import com.cci.projectx.core.entity.S;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SRepository {
    int deleteByPrimaryKey(@Param("id") Long id);

    int insert(@Param("s") S s);

    int insertSelective(@Param("s") S s);

    S selectByPrimaryKey(@Param("id") Long id);

    int updateByPrimaryKeySelective(@Param("s") S s);

    int updateByPrimaryKey(@Param("s") S s);

    int selectCount(@Param("s") S s);

    java.util.List<com.cci.projectx.core.entity.S> selectPage(@Param("s") S s, @Param("pageable") Pageable pageable);
}