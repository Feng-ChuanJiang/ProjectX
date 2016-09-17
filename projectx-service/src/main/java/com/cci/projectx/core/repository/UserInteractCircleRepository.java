package com.cci.projectx.core.repository;

import com.cci.projectx.core.entity.UserInteractCircle;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserInteractCircleRepository {
    int deleteByPrimaryKey(@Param("id") Long id);

    int insert(@Param("userinteractcircle") UserInteractCircle userinteractcircle);

    int insertSelective(@Param("userinteractcircle") UserInteractCircle userinteractcircle);

    UserInteractCircle selectByPrimaryKey(@Param("id") Long id);

    int updateByPrimaryKeySelective(@Param("userinteractcircle") UserInteractCircle userinteractcircle);

    int updateByPrimaryKey(@Param("userinteractcircle") UserInteractCircle userinteractcircle);

    int selectCount(@Param("userinteractcircle") UserInteractCircle userinteractcircle);

    java.util.List<com.cci.projectx.core.entity.UserInteractCircle> selectPage(@Param("userinteractcircle") UserInteractCircle userinteractcircle, @Param("pageable") Pageable pageable);
}