
package com.cci.projectx.core.service;

import com.cci.projectx.core.model.RunFriendsModel;
import com.cci.projectx.core.model.RunInfoModel;
import com.cci.projectx.core.model.UserModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface RunInfoService {

    public int create(RunInfoModel runInfoModel);

    public int createSelective(RunInfoModel runInfoModel);

    public RunInfoModel findByPrimaryKey(Long id);

    public int updateByPrimaryKey(RunInfoModel runInfoModel);

    public int updateByPrimaryKeySelective(RunInfoModel runInfoModel);

    public int deleteByPrimaryKey(Long id);

    public long selectCount(RunInfoModel runInfoModel);

    public List<RunInfoModel> selectPage(RunInfoModel runInfoModel, Pageable pageable);

    public int addFriends(RunFriendsModel friend);

    public int updateFriends(RunFriendsModel friend);

    public Page<UserModel> findUserRunFriendsById(Long userId, Pageable pageable);

    public List<Map<String, Object>> findApplyforRunFriends(Long userId);

    public List<Map<String, Object>> findWaitingRunFriends(Long userId);

    public List<UserModel> findAssignRunFriends(Long userId,Pageable pageable);

}