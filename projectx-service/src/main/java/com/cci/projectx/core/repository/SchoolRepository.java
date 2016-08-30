package com.cci.projectx.core.repository;

import com.cci.projectx.core.entity.School;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SchoolRepository {
    int deleteByPrimaryKey(@Param("id") Long id);

    int insert(@Param("school") School school);

    int insertSelective(@Param("school") School school);

    School selectByPrimaryKey(@Param("id") Long id);

    int updateByPrimaryKeySelective(@Param("school") School school);

    int updateByPrimaryKey(@Param("school") School school);

    int selectCount(@Param("school") School school);

    java.util.List<com.cci.projectx.core.entity.School> selectPage(@Param("school") School school, @Param("pageable") Pageable pageable);
}