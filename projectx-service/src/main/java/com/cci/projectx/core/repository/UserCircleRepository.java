package com.cci.projectx.core.repository;

import com.cci.projectx.core.entity.UserCircle;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserCircleRepository {
    int deleteByPrimaryKey(@Param("id") Long id);

    int insert(@Param("usercircle") UserCircle usercircle);

    int insertSelective(@Param("usercircle") UserCircle usercircle);

    UserCircle selectByPrimaryKey(@Param("id") Long id);

    int updateByPrimaryKeySelective(@Param("usercircle") UserCircle usercircle);

    int updateByPrimaryKey(@Param("usercircle") UserCircle usercircle);

    int selectCount(@Param("usercircle") UserCircle usercircle);

    java.util.List<com.cci.projectx.core.entity.UserCircle> selectPage(@Param("usercircle") UserCircle usercircle, @Param("pageable") Pageable pageable);
}