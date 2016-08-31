
package com.cci.projectx.core.service;

import com.cci.projectx.core.entity.Friends;
import com.cci.projectx.core.model.EducationModel;
import com.cci.projectx.core.model.FriendsModel;
import com.cci.projectx.core.model.UserModel;
import com.cci.projectx.core.model.WorkingExperienceModel;
import org.springframework.data.domain.Page;
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

    public int addFriends(Friends friends);

    public int deleteFriends(FriendsModel friends);

    public List<Long> findEducationIdByUserId(Long id);

    public List<Long> findworkingExperienceIdByUserId(Long id);

    public List<Map<String, Object>> getBackdropId(List<UserModel> userlist);

    public List<UserModel> getUserByUserProfile(UserModel user);

    public UserModel findUserShortById(Long id) ;

    public Page<UserModel> findUserFriendsNotId(Long userId, Long[] friendsId,Pageable pageable);

    public Page<UserModel> findUserFriendsById(Long userId, Pageable pageable) ;

    public List<Map<String, Object>> findApplyforFriends(Long userId);

    public List<Map<String, Object>> findWaitingFriends(Long userId);

    public  Page<UserModel> findColleague(Long workingId,Pageable pageable);

    public int findColleagueCount(Long workingId);

    public  Page<UserModel> findSchoolfellow(Long educationId,Pageable pageable);

    public int findSchoolfellowCount(Long educationId);

    public Page<UserModel>  findCommonFriends(Long userIdOne,Long userIdTwo,Pageable pageable);

    public int  findCommonFriendsCount(Long userIdOne,Long userIdTwo);

    public int  findfriendsCount(Long userId);



}