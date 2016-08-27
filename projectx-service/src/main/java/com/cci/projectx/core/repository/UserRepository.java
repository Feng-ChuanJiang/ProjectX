package com.cci.projectx.core.repository;

import com.cci.projectx.core.entity.User;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository {
    int deleteByPrimaryKey(@Param("id") Long id);

    int insert(@Param("user") User user);

    int insertSelective(@Param("user") User user);

    User selectByPrimaryKey(@Param("id") Long id);

    int updateByPrimaryKeySelective(@Param("user") User user);

    int updateByPrimaryKey(@Param("user") User user);

    int selectCount(@Param("user") User user);

    java.util.List<com.cci.projectx.core.entity.User> selectPage(@Param("user") User user, @Param("pageable") Pageable pageable);
}