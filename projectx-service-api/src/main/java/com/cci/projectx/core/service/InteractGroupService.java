
package com.cci.projectx.core.service;

import com.cci.projectx.core.model.InteractGroupModel;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface InteractGroupService {

public int create(InteractGroupModel interactGroupModel);

public int createSelective(InteractGroupModel interactGroupModel);

public InteractGroupModel findByPrimaryKey(Long id);

public int updateByPrimaryKey(InteractGroupModel interactGroupModel);

public int updateByPrimaryKeySelective(InteractGroupModel interactGroupModel);

public int deleteByPrimaryKey(Long id);

public long selectCount(InteractGroupModel interactGroupModel);

public List<InteractGroupModel> selectPage(InteractGroupModel interactGroupModel, Pageable pageable);

}