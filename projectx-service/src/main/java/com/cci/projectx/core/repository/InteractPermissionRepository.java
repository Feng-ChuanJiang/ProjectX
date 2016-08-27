package com.cci.projectx.core.repository;

import com.cci.projectx.core.entity.InteractPermission;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface InteractPermissionRepository {
    int deleteByPrimaryKey(@Param("id") Long id);

    int insert(@Param("interactpermission") InteractPermission interactpermission);

    int insertSelective(@Param("interactpermission") InteractPermission interactpermission);

    InteractPermission selectByPrimaryKey(@Param("id") Long id);

    int updateByPrimaryKeySelective(@Param("interactpermission") InteractPermission interactpermission);

    int updateByPrimaryKey(@Param("interactpermission") InteractPermission interactpermission);

    int selectCount(@Param("interactpermission") InteractPermission interactpermission);

    java.util.List<com.cci.projectx.core.entity.InteractPermission> selectPage(@Param("interactpermission") InteractPermission interactpermission, @Param("pageable") Pageable pageable);
}