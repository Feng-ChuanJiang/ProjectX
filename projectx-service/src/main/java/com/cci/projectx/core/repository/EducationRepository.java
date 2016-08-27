package com.cci.projectx.core.repository;

import com.cci.projectx.core.entity.Education;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface EducationRepository {
    int deleteByPrimaryKey(@Param("id") Long id);

    int insert(@Param("education") Education education);

    int insertSelective(@Param("education") Education education);

    Education selectByPrimaryKey(@Param("id") Long id);

    int updateByPrimaryKeySelective(@Param("education") Education education);

    int updateByPrimaryKey(@Param("education") Education education);

    int selectCount(@Param("education") Education education);

    java.util.List<com.cci.projectx.core.entity.Education> selectPage(@Param("education") Education education, @Param("pageable") Pageable pageable);
}