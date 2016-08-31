package com.cci.projectx.core.repository;

import com.cci.projectx.core.entity.WorkingExperience;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkingExperienceRepository {
    int deleteByPrimaryKey(@Param("id") Long id);

    int insert(@Param("workingexperience") WorkingExperience workingexperience);

    int insertSelective(@Param("workingexperience") WorkingExperience workingexperience);

    WorkingExperience selectByPrimaryKey(@Param("id") Long id);

    int updateByPrimaryKeySelective(@Param("workingexperience") WorkingExperience workingexperience);

    int updateByPrimaryKey(@Param("workingexperience") WorkingExperience workingexperience);

    int selectCount(@Param("workingexperience") WorkingExperience workingexperience);

    java.util.List<com.cci.projectx.core.entity.WorkingExperience> selectPage(@Param("workingexperience") WorkingExperience workingexperience, @Param("pageable") Pageable pageable);
}