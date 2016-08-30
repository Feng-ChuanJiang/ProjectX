package com.cci.projectx.core.repository;

import com.cci.projectx.core.entity.Company;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepository {
    int deleteByPrimaryKey(@Param("id") Long id);

    int insert(@Param("company") Company company);

    int insertSelective(@Param("company") Company company);

    Company selectByPrimaryKey(@Param("id") Long id);

    int updateByPrimaryKeySelective(@Param("company") Company company);

    int updateByPrimaryKey(@Param("company") Company company);

    int selectCount(@Param("company") Company company);

    java.util.List<com.cci.projectx.core.entity.Company> selectPage(@Param("company") Company company, @Param("pageable") Pageable pageable);
}