
package com.cci.projectx.core.service;

import com.cci.projectx.core.model.UserInteractCircleModel;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserInteractCircleService {

    public int create(UserInteractCircleModel userInteractCircleModel);

    public int createSelective(UserInteractCircleModel userInteractCircleModel);

    public UserInteractCircleModel findByPrimaryKey(Long id);

    public int updateByPrimaryKey(UserInteractCircleModel userInteractCircleModel);

    public int updateByPrimaryKeySelective(UserInteractCircleModel userInteractCircleModel);

    public int deleteByPrimaryKey(Long id);

    public long selectCount(UserInteractCircleModel userInteractCircleModel);

    public List<UserInteractCircleModel> selectPage(UserInteractCircleModel userInteractCircleModel, Pageable pageable);

    public int deleteByUserIdandInteractId(Long userId, Long interactId);
}