package com.cci.projectx.core.repository;

import com.cci.projectx.core.entity.Post;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository {
    int deleteByPrimaryKey(@Param("id") Long id);

    int insert(@Param("post") Post post);

    int insertSelective(@Param("post") Post post);

    Post selectByPrimaryKey(@Param("id") Long id);

    int updateByPrimaryKeySelective(@Param("post") Post post);

    int updateByPrimaryKey(@Param("post") Post post);

    int selectCount(@Param("post") Post post);

    java.util.List<com.cci.projectx.core.entity.Post> selectPage(@Param("post") Post post, @Param("pageable") Pageable pageable);
}