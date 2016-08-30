package com.cci.projectx.core.service.impl;

import com.cci.projectx.core.ElasticSearchHelp;
import com.cci.projectx.core.FriendsType;
import com.cci.projectx.core.HRErrorCode;
import com.cci.projectx.core.JdbcTempateHelp;
import com.cci.projectx.core.entity.User;
import com.cci.projectx.core.model.EducationModel;
import com.cci.projectx.core.model.FriendsModel;
import com.cci.projectx.core.model.UserModel;
import com.cci.projectx.core.model.WorkingExperienceModel;
import com.cci.projectx.core.repository.UserRepository;
import com.cci.projectx.core.service.EducationService;
import com.cci.projectx.core.service.UserService;
import com.cci.projectx.core.service.WorkingExperienceService;
import com.wlw.pylon.core.beans.mapping.BeanMapper;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private BeanMapper beanMapper;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private ElasticSearchHelp elasticSearchHelp;

    @Autowired
    private EducationService educationService;

    @Autowired
    private WorkingExperienceService workingExperienceService;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private JdbcTempateHelp jdbcTempateHelp;

    @Transactional
    @Override
    public int create(UserModel userModel) {
        UserModel existedUser = findUserByAccount(userModel.getMobilePhone());
        if (null != existedUser) {
            HRErrorCode.throwBusinessException(HRErrorCode.USER_HAVE_EXISTED);
        }
        userModel.setCreateTime(new Date());
        userModel.setPassword(DigestUtils.md5Hex(userModel.getPassword()));
        return createSelective(userModel);
    }

    @Transactional
    @Override
    public int createSelective(UserModel userModel) {
        User user = beanMapper.map(userModel, User.class);
        int id = userRepo.insertSelective(user);
        //添加教育信息
        if(CollectionUtils.isNotEmpty(userModel.getEducations())) {
            for (EducationModel education : userModel.getEducations()) {
                education.setUserId(user.getId());
                educationService.create(education);
            }
        }
        //添加工作信息
        if(CollectionUtils.isNotEmpty(userModel.getWorkingExperiences())) {
            for (WorkingExperienceModel workingExperience : userModel.getWorkingExperiences()) {
                workingExperience.setUserId(user.getId());
                workingExperienceService.create(workingExperience);
            }
        }
        //添加EliasticSearchHelp
        if (user.getId()!=null) {
            elasticSearchHelp.mergeES(user, user.getId().toString());
        }
        return id;
    }

    @Transactional
    @Override
    public int deleteByPrimaryKey(Long id) {
        int uid = userRepo.deleteByPrimaryKey(id);
        if (uid > 0) {
            elasticSearchHelp.deleteES(User.class, id);
        }
        return uid;
    }

    @Transactional(readOnly = true)
    @Override
    public UserModel findByPrimaryKey(Long id) {
        User user = userRepo.selectByPrimaryKey(id);
        UserModel userModel=beanMapper.map(user, UserModel.class);
        if (user != null) {
            List<WorkingExperienceModel> w = findworkingExperienceByUserId(user.getId());
            List<EducationModel> e = findEducationByUserId(user.getId());
            userModel.setWorkingExperiences(w.size() > 0 ? w.subList(0, 1) : w);
            userModel.setEducations(e.size() > 0 ? e.subList(0, 1) : e);
        }
        return userModel;
    }

    @Transactional(readOnly = true)
    @Override
    public long selectCount(UserModel userModel) {
        return userRepo.selectCount(beanMapper.map(userModel, User.class));
    }

    /**
     * 分页得到用户信息 包括教育背景和工作背景
     *
     * @param pageable
     * @return
     */
    @Transactional(readOnly = true)
    @Override
    public List<UserModel> selectPage(UserModel userModel, Pageable pageable) {
        User user = beanMapper.map(userModel, User.class);
        List<UserModel> users= beanMapper.mapAsList(userRepo.selectPage(user, pageable), UserModel.class);
        for (UserModel u : users) {
            if(CollectionUtils.isNotEmpty(userModel.getEducations())){
                EducationModel em=userModel.getEducations().get(0);
                em.setUserId(u.getId());
                u.setEducations(educationService.selectPage(em, new PageRequest(0, Integer.MAX_VALUE)));
            }else{
                u.setEducations(findEducationByUserId(u.getId()));
            }
            if(CollectionUtils.isNotEmpty(userModel.getWorkingExperiences())) {
                WorkingExperienceModel wem=userModel.getWorkingExperiences().get(0);
                wem.setUserId(u.getId());
                u.setWorkingExperiences(workingExperienceService.selectPage(wem, new PageRequest(0, Integer.MAX_VALUE)));
            }else{
                u.setWorkingExperiences(findworkingExperienceByUserId(u.getId()));
            }

        }
        return users;
    }

    @Transactional
    @Override
    public int updateByPrimaryKey(UserModel userModel) {
        User user = beanMapper.map(userModel, User.class);
        int id = userRepo.updateByPrimaryKey(user);
        //更新教育信息
        if(CollectionUtils.isNotEmpty(userModel.getEducations())) {
            for (EducationModel education : userModel.getEducations()) {
                education.setUserId(user.getId());
                educationService.updateByPrimaryKey(education);
            }
        }
        //更新工作信息
        if(CollectionUtils.isNotEmpty(userModel.getWorkingExperiences())) {
            for (WorkingExperienceModel workingExperience : userModel.getWorkingExperiences()) {
                workingExperience.setUserId(user.getId());
                workingExperienceService.updateByPrimaryKey(workingExperience);
            }
        }
        //更新EliasticSearchHelp
        if (user.getId()!=null) {
            elasticSearchHelp.mergeES(user, user.getId().toString());
        }
        return id;
    }

    @Transactional
    @Override
    public int updateByPrimaryKeySelective(UserModel userModel) {
        User user = beanMapper.map(userModel, User.class);
        int id = userRepo.updateByPrimaryKeySelective(user);
        //更新教育信息
        if(CollectionUtils.isNotEmpty(userModel.getEducations())) {
            for (EducationModel education : userModel.getEducations()) {
                education.setUserId(user.getId());
                educationService.updateByPrimaryKey(education);
            }
        }
        //更新工作信息
        if(CollectionUtils.isNotEmpty(userModel.getWorkingExperiences())) {
            for (WorkingExperienceModel workingExperience : userModel.getWorkingExperiences()) {
                workingExperience.setUserId(user.getId());
                workingExperienceService.updateByPrimaryKey(workingExperience);
            }
        }
        //更新EliasticSearchHelp
        if (user.getId()!=null) {
            elasticSearchHelp.mergeES(user, user.getId().toString());
        }
        return id;
    }

    /**
     * 通过对象模糊查询用户
     *
     * @param
     * @return
     */
    @Override
    public List<UserModel> getUserByUserProfile(UserModel userModel) {
        User user = beanMapper.map(userModel,User.class);
        List<UserModel> users = elasticSearchHelp.findESForList(user);
        return users;
    }

    /**
     * 根据编号得到用户信息和公司背景简称
     *
     * @param id
     * @return
     */
    @Override
    public Map<String, Object> findUserShortById(Long id) {
        String sql = "SELECT  U.ID,U.NAME,U.PHOTOS,W.COMPANY_FOR_SHORT COMPANYFORSHORT,W.DEPARTMENT_FOR_SHORT DEPARTMENTFORSHORT,W.TITLE_FOR_SHORT TITLEFORSHORT FROM \n" +
                     "USER U ,(SELECT * FROM WORKING_EXPERIENCE W WHERE W.USER_ID=? ORDER BY SORT LIMIT 1)W WHERE U.ID=W.USER_ID";
        return  jdbcTemplate.queryForMap(sql, id);

    }

    /**
     * 得到朋友信息（每个用户背景信息对象的指针）
     *
     * @param userId
     * @return
     */
    @Override
    public List<Map<String, Object>> findUserFriendsById(Long userId) {
        String sql = "SELECT U.* FROM USER U,FRIENDS F WHERE U.ID=F.FRIEND_ID AND F.STATE=? AND F.USER_ID=?";
        BeanPropertyRowMapper<UserModel> argTypes = new BeanPropertyRowMapper<>(UserModel.class);
        List<UserModel> userlist = jdbcTemplate.query(sql, new Object[]{FriendsType.ALREADYFRIENDS.getType(),userId}, argTypes);
        return getBackdropId(userlist);
    }


    /**
     * 得到朋友信息，朋友信息不等于【单个或多个朋友编号】（每个用户背景信息对象的指针）
     *
     * @param userId
     * @return
     */
    @Override
    public List<Map<String, Object>> findUserFriendsNotId(Long userId, Long[] friendsId) {
        String sql = "SELECT U.* FROM USER U,FRIENDS F WHERE U.ID=F.FRIEND_ID AND F.USER_ID=? AND F.STATE=? AND F.FRIEND_ID NOT IN(" + StringUtils.join(friendsId) + ")";
        BeanPropertyRowMapper<UserModel> argTypes = new BeanPropertyRowMapper<>(UserModel.class);
        List<UserModel> userlist = jdbcTemplate.query(sql, new Object[]{FriendsType.ALREADYFRIENDS.getType(),userId}, argTypes);
        return getBackdropId(userlist);
    }

    /**
     * 得到正在申请的朋友（每个用户背景信息对象的指针）
     *
     * @param userId
     * @return
     */
    @Override
    public List<Map<String, Object>> findApplyforFriends(Long userId) {
        String sql = "SELECT U.* FROM USER U,FRIENDS F WHERE U.ID=F.FRIEND_ID AND F.USER_ID=? AND F.STATE=? ";
        BeanPropertyRowMapper<UserModel> argTypes = new BeanPropertyRowMapper<>(UserModel.class);
        List<UserModel> userlist = jdbcTemplate.query(sql, new Object[]{FriendsType.APPLYFRIENDS.getType(),userId}, argTypes);
        return getBackdropId(userlist);
    }

    /**
     * 得到等待验证的朋友（每个用户背景信息对象的指针）
     *
     * @param userId
     * @return
     */
    @Override
    public List<Map<String, Object>> findWaitingFriends(Long userId) {
        String sql = "SELECT U.* FROM USER U,FRIENDS F WHERE U.ID=F.USER_ID AND F.FRIEND_ID=? AND F.STATE=? ";
        BeanPropertyRowMapper<UserModel> argTypes = new BeanPropertyRowMapper<>(UserModel.class);
        List<UserModel> userlist = jdbcTemplate.query(sql, new Object[]{FriendsType.APPLYFRIENDS.getType(),userId}, argTypes);
        return getBackdropId(userlist);
    }

    /**
     * 根据用户编号得到学习背景信息
     *
     * @param userId
     * @return
     */
    @Override
    public List<EducationModel> findEducationByUserId(Long userId) {
        String sql = "SELECT * FROM EDUCATION WHERE USER_ID=? ORDER BY SORT ";
        BeanPropertyRowMapper<EducationModel> argTypes = new BeanPropertyRowMapper<>(EducationModel.class);
        return jdbcTemplate.query(sql, new Object[]{userId}, argTypes);
    }

    /**
     * 添加好友
     *
     * @param friends
     * @return
     */
    public int addFriends(FriendsModel friends) {
        return jdbcTempateHelp.add(friends);
    }

    /**
     * 删除好友
     *
     * @param friends
     * @return
     */
    @Override
    public int deleteFriends(FriendsModel friends) {
        String sql = "DELETE FROM FRIENDS  WHERE USER_ID=? AND FRIEND_ID=?";
        return jdbcTemplate.update(sql, new Object[]{friends.getUserId(), friends.getFriendId()});
    }

    /**
     * 根据用户编号得到工作背景
     *
     * @param userId
     * @return
     */
    @Override
    public List<WorkingExperienceModel> findworkingExperienceByUserId(Long userId) {
        String sql = "SELECT * FROM WORKING_EXPERIENCE WHERE USER_ID=? ORDER BY SORT";
        BeanPropertyRowMapper<WorkingExperienceModel> argTypes = new BeanPropertyRowMapper<>(WorkingExperienceModel.class);
        return jdbcTemplate.query(sql, new Object[]{userId}, argTypes);
    }

    /**
     * 根据用户编号得到学习背景信息编号
     *
     * @param id
     * @return
     */
    @Override
    public List<Long> findEducationIdByUserId(Long id) {
        String sql = "SELECT ID FROM EDUCATION WHERE USER_ID=? ORDER BY SORT ";
        return jdbcTemplate.queryForList(sql, new Object[]{id}, Long.class);
    }

    /**
     * 根据用户编号得到工作背景编号
     *
     * @param id
     * @return
     */
    @Override
    public List<Long> findworkingExperienceIdByUserId(Long id) {
        String sql = "SELECT ID FROM WORKING_EXPERIENCE WHERE USER_ID=? ORDER BY SORT";
        return jdbcTemplate.queryForList(sql, new Object[]{id}, Long.class);
    }

    /**
     * 得到每个用户的背景编号
     *
     * @param userlist
     * @return
     */
    @Override
    public List<Map<String, Object>> getBackdropId(List<UserModel> userlist) {
        List<Map<String, Object>> list = new ArrayList<>();
        List<Long> education = new ArrayList<>();
        List<Long> workingExperience = new ArrayList<>();
        for (UserModel user : userlist) {
            Map<String, Object> map = new HashedMap();
            education = findEducationIdByUserId(user.getId());
            workingExperience = findworkingExperienceIdByUserId(user.getId());
            map.put("user", user);
            map.put("educations", education);
            map.put("workingExperiences", workingExperience);
            list.add(map);
        }
        return list;
    }

    /**
     * 登录
     * @param user
     * @return
     */
    @Override
    public UserModel login(UserModel user){
        String sql="select * from user where mobile_phone =?";
        BeanPropertyRowMapper<UserModel> bprm=new BeanPropertyRowMapper<>(UserModel.class);
        List<UserModel> users=jdbcTemplate.query(sql,new Object[]{user.getMobilePhone()},bprm);
         UserModel existedUser=users.size()>0?users.get(0):null;
        if (null == existedUser) {
            HRErrorCode.throwBusinessException(HRErrorCode.USER_NOT_EXISTED);
        }

        if (!DigestUtils.md5Hex(user.getPassword()).equals(existedUser.getPassword())) {
            HRErrorCode.throwBusinessException(HRErrorCode.PASSWORD_INCORRECT);
        }

        return users.size()>0?users.get(0):null;
    }

    private UserModel findUserByAccount(String mobilePhone) {
        String sql = "select * from user where mobile_phone = ?";
        UserModel userModel = null;
        try {
            userModel = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(UserModel.class),
                    mobilePhone);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }

        return userModel;
    }
}
