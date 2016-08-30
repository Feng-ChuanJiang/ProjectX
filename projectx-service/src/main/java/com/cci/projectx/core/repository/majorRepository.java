package com.cci.projectx.core.repository;

import com.cci.projectx.core.entity.major;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface majorRepository {
    int deleteByPrimaryKey(@Param("id") Long id);

    int insert(@Param("major") major major);

    int insertSelective(@Param("major") major major);

    major selectByPrimaryKey(@Param("id") Long id);

    int updateByPrimaryKeySelective(@Param("major") major major);

    int updateByPrimaryKey(@Param("major") major major);

    int selectCount(@Param("major") major major);

    java.util.List<com.cci.projectx.core.entity.major> selectPage(@Param("major") major major, @Param("pageable") Pageable pageable);
}