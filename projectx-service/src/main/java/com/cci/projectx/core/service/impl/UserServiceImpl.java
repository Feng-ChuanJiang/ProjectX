package com.cci.projectx.core.service.impl;

import com.cci.projectx.core.ElasticSearchHelp;
import com.cci.projectx.core.FriendsType;
import com.cci.projectx.core.HRErrorCode;
import com.cci.projectx.core.JdbcTempateHelp;
import com.cci.projectx.core.entity.Friends;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
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
            userModel.setWorkingExperiences(w);
            userModel.setEducations(e);
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
     * 根据编号得到 最新的用户信息和公司背景简称
     *
     * @param id
     * @return
     */
    @Override
    public UserModel findUserShortById(Long id) {
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
    public int addFriends(Friends friends) {
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
        jdbcTemplate.update(sql, friends.getFriendId(), friends.getUserId());
        return jdbcTemplate.update(sql,friends.getUserId(), friends.getFriendId());
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

    /**
     * 根据用户编号得到好友总数
     * @param userId
     * @return
     */
    @Override
   public int  findfriendsCount(Long userId){
       String sql="SELECT count(1) FROM FRIENDS WHERE STATE=? AND  (USER_ID=? OR FRIEND_ID=?)";
       return jdbcTemplate.queryForObject(sql, Integer.class,FriendsType.ALREADYFRIENDS.getType(),userId,userId);
   }



    /**
     * 根据用户编号朋友
     *
     * @param userId
     * @return
     */
    @Override
    public  Page<UserModel> findUserFriendsById(Long userId,Pageable pageable) {
        String sql = "SELECT B.* FROM (\n" +
                     "SELECT FRIEND_ID AS FRIEND_ID  FROM FRIENDS WHERE STATE=? AND USER_ID=? \n" +
                     "UNION\n" +
                     "SELECT USER_ID AS FRIEND_ID FROM FRIENDS WHERE STATE=? AND FRIEND_ID=? \n" +
                     ")A,`USER` B WHERE A.FRIEND_ID =B.ID LIMIT ? , ? ";

        List<UserModel> content =jdbcTemplate.query(sql,new BeanPropertyRowMapper<>(UserModel.class),FriendsType.ALREADYFRIENDS.getType(),userId,FriendsType.ALREADYFRIENDS.getType(),userId,pageable.getOffset(),pageable.getPageSize());
        sql = "SELECT COUNT(1) FROM (\n" +
                "SELECT FRIEND_ID AS FRIEND_ID  FROM FRIENDS WHERE STATE=? AND USER_ID=? \n" +
                "UNION\n" +
                "SELECT USER_ID AS FRIEND_ID FROM FRIENDS WHERE STATE=? AND FRIEND_ID=? \n" +
                ")A,`USER` B WHERE A.FRIEND_ID =B.ID";

        long count =jdbcTemplate.queryForObject(sql,Long.class,FriendsType.ALREADYFRIENDS.getType(),userId,FriendsType.ALREADYFRIENDS.getType(),userId);
        Page<UserModel> page=new PageImpl<>(content,pageable,count);
        return page;
    }


    /**
     * 得到共同好友数量
     * @param userIdOne
     * @param userIdTwo
     * @return
     */
    @Override
    public int  findCommonFriendsCount(Long userIdOne,Long userIdTwo){
        String sql="SELECT COUNT(1) FROM (\n" +
                "SELECT FRIEND_ID AS FRIEND_ID  FROM FRIENDS WHERE STATE=1 AND USER_ID=10 \n" +
                "UNION\n" +
                "SELECT USER_ID AS FRIEND_ID FROM FRIENDS WHERE STATE=1 AND FRIEND_ID=10 \n" +
                ")A,(\n" +
                "SELECT FRIEND_ID AS FRIEND_ID  FROM FRIENDS WHERE STATE=1 AND USER_ID=11 \n" +
                "UNION\n" +
                "SELECT USER_ID AS FRIEND_ID FROM FRIENDS WHERE STATE=1 AND FRIEND_ID=11 \n" +
                ")B WHERE A.FRIEND_ID=B.FRIEND_ID";
        return jdbcTemplate.queryForObject(sql, Integer.class,FriendsType.ALREADYFRIENDS.getType(),userIdOne,userIdOne,FriendsType.ALREADYFRIENDS.getType(),userIdTwo,userIdTwo);
    }
    /**
     * 得到共同好友
     * @param userIdOne
     * @param userIdTwo
     * @return
     */
    @Override
    public Page<UserModel>  findCommonFriends(Long userIdOne,Long userIdTwo,Pageable pageable){


        String sql="SELECT D.* FROM (\n" +
                "SELECT A.FRIEND_ID FROM (\n" +
                "SELECT FRIEND_ID AS FRIEND_ID  FROM FRIENDS WHERE STATE=? AND USER_ID=? \n" +
                "UNION\n" +
                "SELECT USER_ID AS FRIEND_ID FROM FRIENDS WHERE STATE=? AND FRIEND_ID=? \n" +
                ")A,(\n" +
                "SELECT FRIEND_ID AS FRIEND_ID  FROM FRIENDS WHERE STATE=? AND USER_ID=? \n" +
                "UNION\n" +
                "SELECT USER_ID AS FRIEND_ID FROM FRIENDS WHERE STATE=? AND FRIEND_ID=? \n" +
                ")B WHERE A.FRIEND_ID=B.FRIEND_ID )C ,USER D WHERE C.FRIEND_ID=D.ID LIMIT ? , ?";

        List<UserModel> content =jdbcTemplate.query(sql,new BeanPropertyRowMapper<>(UserModel.class),FriendsType.ALREADYFRIENDS.getType(),userIdOne,
                FriendsType.ALREADYFRIENDS.getType(),userIdOne,FriendsType.ALREADYFRIENDS.getType(),userIdTwo,FriendsType.ALREADYFRIENDS.getType(),userIdTwo,pageable.getOffset(),pageable.getPageSize());
        sql="SELECT COUNT(1) FROM (\n" +
                "SELECT A.FRIEND_ID FROM (\n" +
                "SELECT FRIEND_ID AS FRIEND_ID  FROM FRIENDS WHERE STATE=? AND USER_ID=? \n" +
                "UNION\n" +
                "SELECT USER_ID AS FRIEND_ID FROM FRIENDS WHERE STATE=? AND FRIEND_ID=? \n" +
                ")A,(\n" +
                "SELECT FRIEND_ID AS FRIEND_ID  FROM FRIENDS WHERE STATE=? AND USER_ID=? \n" +
                "UNION\n" +
                "SELECT USER_ID AS FRIEND_ID FROM FRIENDS WHERE STATE=? AND FRIEND_ID=? \n" +
                ")B WHERE A.FRIEND_ID=B.FRIEND_ID )C ,USER D WHERE C.FRIEND_ID=D.ID";

        long count =jdbcTemplate.queryForObject(sql,Long.class,FriendsType.ALREADYFRIENDS.getType(),userIdOne,
                FriendsType.ALREADYFRIENDS.getType(),userIdOne,FriendsType.ALREADYFRIENDS.getType(),userIdTwo,FriendsType.ALREADYFRIENDS.getType(),userIdTwo);
        Page<UserModel> page=new PageImpl<>(content,pageable,count);
        return page;
    }


    /**
     * 根据教育编号得到校友数量
     * @param educationId
     * @return
     */
    @Override
    public int findSchoolfellowCount(Long educationId){
       EducationModel educationModel= educationService.findByPrimaryKey(educationId);
        Long userId=educationModel.getUserId();
        String sql="SELECT COUNT(1) FROM (\n" +
                "SELECT FRIEND_ID AS FRIEND_ID  FROM FRIENDS WHERE STATE=? AND USER_ID=? \n" +
                "UNION\n" +
                "SELECT USER_ID AS FRIEND_ID FROM FRIENDS WHERE STATE=? AND FRIEND_ID=? \n" +
                ")A,(SELECT USER_ID FROM EDUCATION WHERE UNIVERSITY=(SELECT UNIVERSITY FROM EDUCATION WHERE ID=?))B,USER C\n" +
                "WHERE A.FRIEND_ID=B.USER_ID AND B.USER_ID=C.ID";
        return jdbcTemplate.queryForObject(sql, Integer.class,FriendsType.ALREADYFRIENDS.getType(),userId,FriendsType.ALREADYFRIENDS.getType(),userId,educationId);

    }

    /**
     * 根据教育编号得到校友
     * @param educationId
     * @param pageable
     * @return
     */
    @Override
    public  Page<UserModel> findSchoolfellow(Long educationId,Pageable pageable) {
        EducationModel educationModel= educationService.findByPrimaryKey(educationId);
        Long userId=educationModel.getUserId();
        String sql="SELECT C.* FROM (\n" +
                "SELECT FRIEND_ID AS FRIEND_ID  FROM FRIENDS WHERE STATE=? AND USER_ID=? \n" +
                "UNION\n" +
                "SELECT USER_ID AS FRIEND_ID FROM FRIENDS WHERE STATE=? AND FRIEND_ID=? \n" +
                ")A,(SELECT USER_ID FROM EDUCATION WHERE UNIVERSITY=(SELECT UNIVERSITY FROM EDUCATION WHERE ID=?))B,USER C\n" +
                "WHERE A.FRIEND_ID=B.USER_ID AND B.USER_ID=C.ID LIMIT ? , ?";

        List<UserModel> content =jdbcTemplate.query(sql,new BeanPropertyRowMapper<>(UserModel.class),FriendsType.ALREADYFRIENDS.getType(),userId,FriendsType.ALREADYFRIENDS.getType(),userId,educationId,pageable.getOffset(),pageable.getPageSize());
         sql="SELECT COUNT(1) FROM (\n" +
                "SELECT FRIEND_ID AS FRIEND_ID  FROM FRIENDS WHERE STATE=? AND USER_ID=? \n" +
                "UNION\n" +
                "SELECT USER_ID AS FRIEND_ID FROM FRIENDS WHERE STATE=? AND FRIEND_ID=? \n" +
                ")A,(SELECT USER_ID FROM EDUCATION WHERE UNIVERSITY=(SELECT UNIVERSITY FROM EDUCATION WHERE ID=?))B,USER C\n" +
                "WHERE A.FRIEND_ID=B.USER_ID AND B.USER_ID=C.ID";
        long count =jdbcTemplate.queryForObject(sql,Long.class,FriendsType.ALREADYFRIENDS.getType(),userId,FriendsType.ALREADYFRIENDS.getType(),userId,educationId);
        Page<UserModel> page=new PageImpl<>(content,pageable,count);
        return page;
    }
    /**
     * 根据工作编号得到同事数量
     * @param workingId
     * @return
     */
    @Override
    public int findColleagueCount(Long workingId){
        WorkingExperienceModel workingExperienceModel= workingExperienceService.findByPrimaryKey(workingId);
        Long userId=workingExperienceModel.getUserId();
        String sql="SELECT COUNT(1) FROM (\n" +
                "SELECT FRIEND_ID AS FRIEND_ID  FROM FRIENDS WHERE STATE=? AND USER_ID=? \n" +
                "UNION\n" +
                "SELECT USER_ID AS FRIEND_ID FROM FRIENDS WHERE STATE=? AND FRIEND_ID=? \n" +
                ")A,(SELECT USER_ID FROM WORKING_EXPERIENCE WHERE COMPANY=(SELECT COMPANY FROM WORKING_EXPERIENCE WHERE ID=?))B,USER C\n" +
                "WHERE A.FRIEND_ID=B.USER_ID AND B.USER_ID=C.ID";
        return jdbcTemplate.queryForObject(sql, Integer.class,FriendsType.ALREADYFRIENDS.getType(),userId,FriendsType.ALREADYFRIENDS.getType(),userId,workingId);

    }

    /**
     * 根据工作编号得到同事
     * @param workingId
     * @param pageable
     * @return
     */
    @Override
    public  Page<UserModel> findColleague(Long workingId,Pageable pageable) {
        WorkingExperienceModel workingExperienceModel= workingExperienceService.findByPrimaryKey(workingId);
        Long userId=workingExperienceModel.getUserId();
        String sql="SELECT C.* FROM (\n" +
                "SELECT FRIEND_ID AS FRIEND_ID  FROM FRIENDS WHERE STATE=? AND USER_ID=? \n" +
                "UNION\n" +
                "SELECT USER_ID AS FRIEND_ID FROM FRIENDS WHERE STATE=? AND FRIEND_ID=? \n" +
                ")A,(SELECT USER_ID FROM WORKING_EXPERIENCE WHERE COMPANY=(SELECT COMPANY FROM WORKING_EXPERIENCE WHERE ID=?))B,USER C\n" +
                "WHERE A.FRIEND_ID=B.USER_ID AND B.USER_ID=C.ID LIMIT ? , ?";

        List<UserModel> content =jdbcTemplate.query(sql,new BeanPropertyRowMapper<>(UserModel.class),FriendsType.ALREADYFRIENDS.getType(),userId,FriendsType.ALREADYFRIENDS.getType(),userId,workingId,pageable.getOffset(),pageable.getPageSize());
        sql="SELECT COUNT(1) FROM (\n" +
                "SELECT FRIEND_ID AS FRIEND_ID  FROM FRIENDS WHERE STATE=? AND USER_ID=? \n" +
                "UNION\n" +
                "SELECT USER_ID AS FRIEND_ID FROM FRIENDS WHERE STATE=? AND FRIEND_ID=? \n" +
                ")A,(SELECT USER_ID FROM WORKING_EXPERIENCE WHERE COMPANY=(SELECT COMPANY FROM WORKING_EXPERIENCE WHERE ID=?))B,USER C\n" +
                "WHERE A.FRIEND_ID=B.USER_ID AND B.USER_ID=C.ID";
        long count =jdbcTemplate.queryForObject(sql,Long.class,FriendsType.ALREADYFRIENDS.getType(),userId,FriendsType.ALREADYFRIENDS.getType(),userId,workingId);
        Page<UserModel> page=new PageImpl<>(content,pageable,count);
        return page;
    }


    /**
     * 得到朋友信息，朋友信息不等于【单个或多个朋友编号】
     *
     * @param userId
     * @return
     */
    @Override
    public Page<UserModel> findUserFriendsNotId(Long userId, Long[] friendsId,Pageable pageable) {

        String sql = "SELECT B.* FROM (\n" +
                "SELECT FRIEND_ID AS FRIEND_ID  FROM FRIENDS WHERE STATE=? AND USER_ID=? \n" +
                "UNION\n" +
                "SELECT USER_ID AS FRIEND_ID FROM FRIENDS WHERE STATE=? AND FRIEND_ID=? \n" +
                ")A,`USER` B WHERE A.FRIEND_ID =B.ID AND B.ID NOT IN(" + StringUtils.join(friendsId) + ") LIMIT ? , ? ";

        List<UserModel> content =jdbcTemplate.query(sql,new BeanPropertyRowMapper<>(UserModel.class),FriendsType.ALREADYFRIENDS.getType(),userId,FriendsType.ALREADYFRIENDS.getType(),userId,pageable.getOffset(),pageable.getPageSize());
        sql = "SELECT COUNT(1) FROM (\n" +
                "SELECT FRIEND_ID AS FRIEND_ID  FROM FRIENDS WHERE STATE=? AND USER_ID=? \n" +
                "UNION\n" +
                "SELECT USER_ID AS FRIEND_ID FROM FRIENDS WHERE STATE=? AND FRIEND_ID=? \n" +
                ")A,`USER` B WHERE A.FRIEND_ID =B.ID AND B.ID NOT IN(" + StringUtils.join(friendsId) + ")";

        long count =jdbcTemplate.queryForObject(sql,Long.class,FriendsType.ALREADYFRIENDS.getType(),userId,FriendsType.ALREADYFRIENDS.getType(),userId);
        Page<UserModel> page=new PageImpl<>(content,pageable,count);
        return page;

    }

    /**
     * 得到正在申请的朋友（
     *
     * @param userId
     * @return
     */
    @Override
    public List<Map<String, Object>> findApplyforFriends(Long userId) {
        String sql = "SELECT U.* FROM USER U,FRIENDS F WHERE U.ID=F.FRIEND_ID AND F.USER_ID=? AND F.STATE=? ";
        BeanPropertyRowMapper<UserModel> argTypes = new BeanPropertyRowMapper<>(UserModel.class);
        List<UserModel> userlist = jdbcTemplate.query(sql,argTypes,userId,FriendsType.APPLYFRIENDS.getType());
        return getBackdropId(userlist);
    }

    /**
     * 得到等待验证的朋友
     *
     * @param userId
     * @return
     */
    @Override
    public List<Map<String, Object>> findWaitingFriends(Long userId) {
        String sql = "SELECT U.* FROM USER U,FRIENDS F WHERE U.ID=F.USER_ID AND F.FRIEND_ID=? AND F.STATE=? ";
        BeanPropertyRowMapper<UserModel> argTypes = new BeanPropertyRowMapper<>(UserModel.class);
        List<UserModel> userlist = jdbcTemplate.query(sql,argTypes,userId,FriendsType.APPLYFRIENDS.getType());
        return getBackdropId(userlist);
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
