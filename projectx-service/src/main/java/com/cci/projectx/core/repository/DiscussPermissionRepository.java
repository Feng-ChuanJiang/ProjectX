package com.cci.projectx.core.repository;

import com.cci.projectx.core.entity.DiscussPermission;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DiscussPermissionRepository {
    int deleteByPrimaryKey(@Param("id") Long id);

    int insert(@Param("discusspermission") DiscussPermission discusspermission);

    int insertSelective(@Param("discusspermission") DiscussPermission discusspermission);

    DiscussPermission selectByPrimaryKey(@Param("id") Long id);

    int updateByPrimaryKeySelective(@Param("discusspermission") DiscussPermission discusspermission);

    int updateByPrimaryKey(@Param("discusspermission") DiscussPermission discusspermission);

    int selectCount(@Param("discusspermission") DiscussPermission discusspermission);

    List<DiscussPermission> selectPage(@Param("discusspermission") DiscussPermission discusspermission, @Param("pageable") Pageable pageable);
}