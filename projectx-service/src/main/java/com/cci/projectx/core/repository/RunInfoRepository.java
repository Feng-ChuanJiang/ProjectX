package com.cci.projectx.core.repository;

import com.cci.projectx.core.entity.RunInfo;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RunInfoRepository {
    int deleteByPrimaryKey(@Param("id") Long id);

    int insert(@Param("runinfo") RunInfo runinfo);

    int insertSelective(@Param("runinfo") RunInfo runinfo);

    RunInfo selectByPrimaryKey(@Param("id") Long id);

    int updateByPrimaryKeySelective(@Param("runinfo") RunInfo runinfo);

    int updateByPrimaryKey(@Param("runinfo") RunInfo runinfo);

    int selectCount(@Param("runinfo") RunInfo runinfo);

    java.util.List<com.cci.projectx.core.entity.RunInfo> selectPage(@Param("runinfo") RunInfo runinfo, @Param("pageable") Pageable pageable);
}