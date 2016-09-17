
package com.cci.projectx.core.service;

import com.cci.projectx.core.model.InteractPermissionModel;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface InteractPermissionService {

    public int create(InteractPermissionModel interactPermissionModel);

    public int createSelective(InteractPermissionModel interactPermissionModel);

    public InteractPermissionModel findByPrimaryKey(Long id);

    public int updateByPrimaryKey(InteractPermissionModel interactPermissionModel);

    public int updateByPrimaryKeySelective(InteractPermissionModel interactPermissionModel);

    public int deleteByPrimaryKey(Long id);

    public long selectCount(InteractPermissionModel interactPermissionModel);

    public List<InteractPermissionModel> selectPage(InteractPermissionModel interactPermissionModel, Pageable pageable);

    public int deleteByUserIdandInteractId(Long userId, Long interactId);


}