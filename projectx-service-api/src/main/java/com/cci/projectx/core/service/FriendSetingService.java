
package com.cci.projectx.core.service;

import com.cci.projectx.core.model.FriendSetingModel;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface FriendSetingService{

public int create(FriendSetingModel friendSetingModel);

public int createSelective(FriendSetingModel friendSetingModel);

public FriendSetingModel findByPrimaryKey(Long id);

public int updateByPrimaryKey(FriendSetingModel friendSetingModel);

public int updateByPrimaryKeySelective(FriendSetingModel friendSetingModel);

public int deleteByPrimaryKey(Long id);

public long selectCount(FriendSetingModel friendSetingModel);

public List<FriendSetingModel> selectPage(FriendSetingModel friendSetingModel, Pageable pageable);

}