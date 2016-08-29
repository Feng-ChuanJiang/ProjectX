package com.cci.projectx.core.repository;

import com.cci.projectx.core.entity.Photo;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PhotoRepository {
    int deleteByPrimaryKey(@Param("id") Long id);

    int insert(@Param("photo") Photo photo);

    int insertSelective(@Param("photo") Photo photo);

    Photo selectByPrimaryKey(@Param("id") Long id);

    int updateByPrimaryKeySelective(@Param("photo") Photo photo);

    int updateByPrimaryKey(@Param("photo") Photo photo);

    int selectCount(@Param("photo") Photo photo);

    java.util.List<com.cci.projectx.core.entity.Photo> selectPage(@Param("photo") Photo photo, @Param("pageable") Pageable pageable);
}