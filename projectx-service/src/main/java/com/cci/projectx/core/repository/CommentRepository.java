package com.cci.projectx.core.repository;

import com.cci.projectx.core.entity.Comment;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository {
    int deleteByPrimaryKey(@Param("id") Long id);

    int insert(@Param("comment") Comment comment);

    int insertSelective(@Param("comment") Comment comment);

    Comment selectByPrimaryKey(@Param("id") Long id);

    int updateByPrimaryKeySelective(@Param("comment") Comment comment);

    int updateByPrimaryKey(@Param("comment") Comment comment);

    int selectCount(@Param("comment") Comment comment);

    java.util.List<com.cci.projectx.core.entity.Comment> selectPage(@Param("comment") Comment comment, @Param("pageable") Pageable pageable);
}