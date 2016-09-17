
package com.cci.projectx.core.service;

import com.cci.projectx.core.model.UserCircleModel;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserCircleService{

public int create(UserCircleModel userCircleModel);

public int createSelective(UserCircleModel userCircleModel);

public UserCircleModel findByPrimaryKey(Long id);

public int updateByPrimaryKey(UserCircleModel userCircleModel);

public int updateByPrimaryKeySelective(UserCircleModel userCircleModel);

public int deleteByPrimaryKey(Long id);

public long selectCount(UserCircleModel userCircleModel);

public List<UserCircleModel> selectPage(UserCircleModel userCircleModel, Pageable pageable);

}