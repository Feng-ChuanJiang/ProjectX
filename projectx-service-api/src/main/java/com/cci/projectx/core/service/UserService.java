
package com.cci.projectx.core.service;

import com.cci.projectx.core.entity.User;
import com.cci.projectx.core.model.EducationModel;
import com.cci.projectx.core.model.FriendsModel;
import com.cci.projectx.core.model.UserModel;
import com.cci.projectx.core.model.WorkingExperienceModel;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface UserService {

    public int create(UserModel userModel);

    public int createSelective(UserModel userModel);

    public UserModel findByPrimaryKey(Long id);

    public int updateByPrimaryKey(UserModel userModel);

    public int updateByPrimaryKeySelective(UserModel userModel);

    public int deleteByPrimaryKey(Long id);

    public long selectCount(UserModel userModel);

    public List<UserModel> selectPage(UserModel userModel, Pageable pageable);

    public UserModel login(UserModel user);

    public List<WorkingExperienceModel> findworkingExperienceByUserId(Long userId);

    public List<EducationModel> findEducationByUserId(Long userId);

    public int addFriends(FriendsModel friends);

    public int deleteFriends(FriendsModel friends);

    public List<Long> findEducationIdByUserId(Long id);

    public List<Long> findworkingExperienceIdByUserId(Long id);

    public List<Map<String, Object>> getBackdropId(List<UserModel> userlist);

    public List<User> getUserByUserProfile(User user);

    public Map<String, Object> findUserShortById(Long id);

    public List<Map<String, Object>> findUserFriendsNotId(Long userId, Long[] friendsId);

    public List<Map<String, Object>> findUserFriendsById(Long userId);

    public List<Map<String, Object>> findApplyforFriends(Long userId);

    public List<Map<String, Object>> findWaitingFriends(Long userId);


}