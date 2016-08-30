package com.cci.projectx.core.repository;

import com.cci.projectx.core.entity.Lndustry;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface LndustryRepository {
    int deleteByPrimaryKey(@Param("id") Long id);

    int insert(@Param("lndustry") Lndustry lndustry);

    int insertSelective(@Param("lndustry") Lndustry lndustry);

    Lndustry selectByPrimaryKey(@Param("id") Long id);

    int updateByPrimaryKeySelective(@Param("lndustry") Lndustry lndustry);

    int updateByPrimaryKey(@Param("lndustry") Lndustry lndustry);

    int selectCount(@Param("lndustry") Lndustry lndustry);

    java.util.List<com.cci.projectx.core.entity.Lndustry> selectPage(@Param("lndustry") Lndustry lndustry, @Param("pageable") Pageable pageable);
}