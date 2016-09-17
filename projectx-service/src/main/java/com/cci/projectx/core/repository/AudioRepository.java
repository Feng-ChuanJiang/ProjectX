package com.cci.projectx.core.repository;

import com.cci.projectx.core.entity.Audio;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AudioRepository {
    int deleteByPrimaryKey(@Param("id") Long id);

    int insert(@Param("audio") Audio audio);

    int insertSelective(@Param("audio") Audio audio);

    Audio selectByPrimaryKey(@Param("id") Long id);

    int updateByPrimaryKeySelective(@Param("audio") Audio audio);

    int updateByPrimaryKey(@Param("audio") Audio audio);

    int selectCount(@Param("audio") Audio audio);

    java.util.List<com.cci.projectx.core.entity.Audio> selectPage(@Param("audio") Audio audio, @Param("pageable") Pageable pageable);
}