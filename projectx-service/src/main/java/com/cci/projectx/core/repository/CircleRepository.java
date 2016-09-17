package com.cci.projectx.core.repository;

import com.cci.projectx.core.entity.Circle;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CircleRepository {
    int deleteByPrimaryKey(@Param("id") Long id);

    int insert(@Param("circle") Circle circle);

    int insertSelective(@Param("circle") Circle circle);

    Circle selectByPrimaryKey(@Param("id") Long id);

    int updateByPrimaryKeySelective(@Param("circle") Circle circle);

    int updateByPrimaryKey(@Param("circle") Circle circle);

    int selectCount(@Param("circle") Circle circle);

    java.util.List<com.cci.projectx.core.entity.Circle> selectPage(@Param("circle") Circle circle, @Param("pageable") Pageable pageable);
}