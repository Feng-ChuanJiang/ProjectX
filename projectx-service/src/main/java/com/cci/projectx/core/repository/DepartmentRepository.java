package com.cci.projectx.core.repository;

import com.cci.projectx.core.entity.Department;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartmentRepository {
    int deleteByPrimaryKey(@Param("id") Long id);

    int insert(@Param("department") Department department);

    int insertSelective(@Param("department") Department department);

    Department selectByPrimaryKey(@Param("id") Long id);

    int updateByPrimaryKeySelective(@Param("department") Department department);

    int updateByPrimaryKey(@Param("department") Department department);

    int selectCount(@Param("department") Department department);

    java.util.List<com.cci.projectx.core.entity.Department> selectPage(@Param("department") Department department, @Param("pageable") Pageable pageable);
}