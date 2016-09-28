
package com.cci.projectx.core.service;

import com.cci.projectx.core.model.DiscussPermissionModel;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface DiscussPermissionService {

    public int create(DiscussPermissionModel discussPermissionModel);

    public int createSelective(DiscussPermissionModel discussPermissionModel);

    public DiscussPermissionModel findByPrimaryKey(Long id);

    public int updateByPrimaryKey(DiscussPermissionModel discussPermissionModel);

    public int updateByPrimaryKeySelective(DiscussPermissionModel discussPermissionModel);

    public int deleteByPrimaryKey(Long id);

    public long selectCount(DiscussPermissionModel discussPermissionModel);

    public List<DiscussPermissionModel> selectPage(DiscussPermissionModel discussPermissionModel, Pageable pageable);

    public int deleteByPrimary(DiscussPermissionModel permissionModel);

    public int deleteByPrimaryDiscussId(Long discussId);

}