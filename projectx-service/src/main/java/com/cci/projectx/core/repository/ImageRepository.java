package com.cci.projectx.core.repository;

import com.cci.projectx.core.entity.Image;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepository {
    int deleteByPrimaryKey(@Param("id") Long id);

    int insert(@Param("image") Image image);

    int insertSelective(@Param("image") Image image);

    Image selectByPrimaryKey(@Param("id") Long id);

    int updateByPrimaryKeySelective(@Param("image") Image image);

    int updateByPrimaryKey(@Param("image") Image image);

    int selectCount(@Param("image") Image image);

    java.util.List<com.cci.projectx.core.entity.Image> selectPage(@Param("image") Image image, @Param("pageable") Pageable pageable);
}