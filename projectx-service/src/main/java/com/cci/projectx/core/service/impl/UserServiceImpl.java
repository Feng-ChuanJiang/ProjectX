package com.cci.projectx.core.service.impl;

import com.cci.projectx.core.ElasticSearchHelp;
import com.cci.projectx.core.FriendsType;
import com.cci.projectx.core.HRErrorCode;
import com.cci.projectx.core.JdbcTempateHelp;
import com.cci.projectx.core.domain.LndustryNeo;
import com.cci.projectx.core.domain.UserNeo;
import com.cci.projectx.core.entity.Friends;
import com.cci.projectx.core.entity.User;
import com.cci.projectx.core.model.*;
import com.cci.projectx.core.neorepository.LndustryNeoRepository;
import com.cci.projectx.core.neorepository.UserNeoRepository;
import com.cci.projectx.core.repository.UserRepository;
import com.cci.projectx.core.service.EducationService;
import com.cci.projectx.core.service.LndustryService;
import com.cci.projectx.core.service.UserService;
import com.cci.projectx.core.service.WorkingExperienceService;
import com.easemob.server.example.api.IMUserAPI;
import com.easemob.server.example.comm.body.IMUserBody;
import com.easemob.server.example.comm.body.ModifyNicknameBody;
import com.easemob.server.example.comm.body.ResetPasswordBody;
import com.easemob.server.example.comm.wrapper.BodyWrapper;
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

    @Autowired
    private UserNeoRepository userNeoRepository;

    @Autowired
    private LndustryNeoRepository lndustryNeoRepository;

    @Autowired
    private LndustryService lndustryService;

    @Autowired
    IMUserAPI imUserAPI;

    @Transactional
    @Override
    public int create(UserModel userModel) {
        return createSelective(userModel);
    }

    @Transactional
    @Override
    public int createSelective(UserModel userModel) {
        UserModel existedUser = findUserByAccount(userModel.getMobilePhone());
        if (null != existedUser) {
            HRErrorCode.throwBusinessException(HRErrorCode.USER_HAVE_EXISTED);
        }
        userModel.setCreateTime(new Date());
        userModel.setPassword(DigestUtils.md5Hex(userModel.getPassword()));
        User user = beanMapper.map(userModel, User.class);
        int id = userRepo.insertSelective(user);
        //添加环信账号
        BodyWrapper userBody = new IMUserBody(user.getMobilePhone(), user.getPassword(), user.getName());
        imUserAPI.createNewIMUserSingle(userBody);
        //添加教育信息
        if (CollectionUtils.isNotEmpty(userModel.getEducations())) {
            for (EducationModel education : userModel.getEducations()) {
                education.setUserId(user.getId());
                educationService.create(education);
            }
        }
        //添加工作信息
        if (CollectionUtils.isNotEmpty(userModel.getWorkingExperiences())) {
            for (WorkingExperienceModel workingExperience : userModel.getWorkingExperiences()) {
                workingExperience.setUserId(user.getId());
                workingExperienceService.create(workingExperience);
            }
        }
        //添加EliasticSearchHelp
        if (user.getId() != null) {
            elasticSearchHelp.mergeES(user, user.getId().toString());
            //neo4j
            UserNeo userNeo = new UserNeo();
            userNeo.setName(user.getMobilePhone());
            userNeo.setUserId(user.getId());
            userNeoRepository.save(userNeo);
        }
        //添加行业关系
        if (!StringUtils.isEmpty(user.getLndustry())) {
            //查询mysql里面是否有
            if (lndustryService.findCountByName(user.getLndustry()) == 0) {
                LndustryModel lndustryModel = new LndustryModel();
                lndustryModel.setName(user.getLndustry());
                lndustryService.create(lndustryModel);
            }
            Long lndustyrId = lndustryService.findIdByName(user.getLndustry());
            //添加关系
            if (lndustyrId != null) {
                LndustryNeo lndustryNeo = lndustryNeoRepository.findByLndustryId(lndustyrId);
                lndustryNeoRepository.deleteLndustryRelat(user.getId(), lndustryNeo.getLndustryId());
                lndustryNeoRepository.addLndustryRelat(user.getId(), lndustryNeo.getLndustryId());
            }
        }


        return id;
    }

    @Transactional
    @Override
    public int deleteByPrimaryKey(Long id) {
        //删除行业关系
        UserModel user = findByPrimaryKey(id);
        if (StringUtils.isNotEmpty(user.getLndustry())) {
            //删除关系
            Long lndustyrId = lndustryService.findIdByName(user.getLndustry());
            if (lndustyrId != null) {
                LndustryNeo lndustryNeo = lndustryNeoRepository.findByLndustryId(lndustyrId);
                if (lndustryNeo != null) {
                    lndustryNeoRepository.deleteLndustryRelat(user.getId(), lndustryNeo.getLndustryId());
                }
            }
        }
        int uid = userRepo.deleteByPrimaryKey(id);
        //es
        elasticSearchHelp.deleteES(User.class, id);
        //neo4j
        UserNeo userNeo = userNeoRepository.findByUserId(id);
        userNeoRepository.delete(userNeo.getId());
        //删除环信账号
        imUserAPI.deleteIMUserByUserName(user.getMobilePhone());
        return uid;
    }

    @Transactional(readOnly = true)
    @Override
    public UserModel findByPrimaryKey(Long id) {
        User user = userRepo.selectByPrimaryKey(id);
        UserModel userModel = beanMapper.map(user, UserModel.class);
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
        List<UserModel> users = beanMapper.mapAsList(userRepo.selectPage(user, pageable), UserModel.class);
        for (UserModel u : users) {
            if (CollectionUtils.isNotEmpty(userModel.getEducations())) {
                EducationModel em = userModel.getEducations().get(0);
                em.setUserId(u.getId());
                u.setEducations(educationService.selectPage(em, new PageRequest(0, Integer.MAX_VALUE)));
            } else {
                u.setEducations(findEducationByUserId(u.getId()));
            }
            if (CollectionUtils.isNotEmpty(userModel.getWorkingExperiences())) {
                WorkingExperienceModel wem = userModel.getWorkingExperiences().get(0);
                wem.setUserId(u.getId());
                u.setWorkingExperiences(workingExperienceService.selectPage(wem, new PageRequest(0, Integer.MAX_VALUE)));
            } else {
                u.setWorkingExperiences(findworkingExperienceByUserId(u.getId()));
            }

        }
        return users;
    }

    @Transactional
    @Override
    public int updateByPrimaryKey(UserModel userModel) {
        return updateByPrimaryKeySelective(userModel);
    }

    @Transactional
    @Override
    public int updateByPrimaryKeySelective(UserModel userModel) {
        //更新行业关系
        UserModel userm = findByPrimaryKey(userModel.getId());
        if (!StringUtils.equals(userm.getLndustry(), userModel.getLndustry())) {
            //查询mysql里面是否有
            if (lndustryService.findCountByName(userModel.getLndustry()) == 0) {
                LndustryModel lndustryModel = new LndustryModel();
                lndustryModel.setName(userModel.getLndustry());
                lndustryService.create(lndustryModel);
            }
            Long lndustyrId = lndustryService.findIdByName(userm.getLndustry());
            Long lndustyrIdM = lndustryService.findIdByName(userModel.getLndustry());
            //修改关系
            if (lndustyrId != null && lndustyrIdM != null) {
                LndustryNeo lndustryNeo = lndustryNeoRepository.findByLndustryId(lndustyrId);
                LndustryNeo lndustryNeoM = lndustryNeoRepository.findByLndustryId(lndustyrIdM);
                lndustryNeoRepository.deleteLndustryRelat(userm.getId(), lndustryNeo.getLndustryId());
                lndustryNeoRepository.addLndustryRelat(userm.getId(), lndustryNeoM.getLndustryId());
            }

        }
        User user = beanMapper.map(userModel, User.class);
        int id = userRepo.updateByPrimaryKeySelective(user);
        //更新教育信息
        if (CollectionUtils.isNotEmpty(userModel.getEducations())) {
            for (EducationModel education : userModel.getEducations()) {
                education.setUserId(user.getId());
                educationService.updateByPrimaryKey(education);
            }
        }
        //更新工作信息
        if (CollectionUtils.isNotEmpty(userModel.getWorkingExperiences())) {
            for (WorkingExperienceModel workingExperience : userModel.getWorkingExperiences()) {
                workingExperience.setUserId(user.getId());
                workingExperienceService.updateByPrimaryKey(workingExperience);
            }
        }
        //更新EliasticSearchHelp
        if (user.getId() != null) {
            elasticSearchHelp.mergeES(user, user.getId().toString());
            //neo4j 用户名不能改
//            UserNeo userNeo = userNeoRepository.findByUserId(user.getId());
//            userNeo.setName(user.getMobilePhone());
//            userNeoRepository.save(userNeo);
            //修改环信信息 昵称
            BodyWrapper nicknameBody = new ModifyNicknameBody(user.getName());
            imUserAPI.modifyIMUserNickNameWithAdminToken(user.getMobilePhone(),nicknameBody);
            //修改环信信息 密码
            BodyWrapper passwordBody = new ResetPasswordBody(userModel.getPassword());
            imUserAPI.modifyIMUserPasswordWithAdminToken(userModel.getMobilePhone(),passwordBody);
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
        User user = beanMapper.map(userModel, User.class);
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
        UserModel userModel = beanMapper.map(user, UserModel.class);
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
        String sql = "SELECT * FROM EDUCATION WHERE USER_ID=? ORDER BY START_TIME DESC ";
        BeanPropertyRowMapper<EducationModel> argTypes = new BeanPropertyRowMapper<>(EducationModel.class);
        return jdbcTemplate.query(sql, new Object[]{userId}, argTypes);
    }

    /**
     * 添加好友
     *
     * @param friends
     * @return
     */
    @Override
    public int addFriends(FriendsModel friends) {
        friends.setState(new Long(FriendsType.APPLYFRIENDS.getType()));
        Friends friend = beanMapper.map(friends, Friends.class);
        String sql="SELECT COUNT(1) FROM FRIENDS WHERE (USER_ID=? AND FRIEND_ID=? OR USER_ID=? AND FRIEND_ID=?)";
        int count= jdbcTemplate.queryForObject(sql,Integer.class,friend.getUserId(),friend.getFriendId(),friend.getFriendId(),friend.getUserId());
        if(count>0){
            HRErrorCode.throwBusinessException(HRErrorCode.FRIEND_HAVE_EXISTED);
        }
        return jdbcTempateHelp.add(friend);
    }

    /**
     * 更新好友
     *
     * @param friends
     * @return
     */
    @Override
    public int updateFriends(FriendsModel friends) {
        Friends friend = beanMapper.map(friends, Friends.class);
        String sql = "UPDATE friends SET state=? where (user_id=? and friend_id=? or  user_id=? and friend_id=?)";
        int id = jdbcTemplate.update(sql, FriendsType.ALREADYFRIENDS.getType(), friend.getUserId(), friend.getFriendId(), friend.getFriendId(), friend.getUserId());
        if (id > 0) {
            userNeoRepository.deleteFriend(friend.getUserId(), friend.getFriendId());
            userNeoRepository.addFriend(friend.getUserId(), friend.getFriendId());
        }
        return id;
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
        return jdbcTemplate.update(sql, friends.getUserId(), friends.getFriendId());
    }

    /**
     * 根据用户编号得到工作背景
     *
     * @param userId
     * @return
     */
    @Override
    public List<WorkingExperienceModel> findworkingExperienceByUserId(Long userId) {
        String sql = "SELECT * FROM WORKING_EXPERIENCE WHERE USER_ID=? ORDER BY START_TIME DESC";
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
        String sql = "SELECT ID FROM EDUCATION WHERE USER_ID=? ORDER BY START_TIME DESC ";
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
        String sql = "SELECT ID FROM WORKING_EXPERIENCE WHERE USER_ID=? ORDER BY START_TIME DESC";
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
     * 根据用户编号得到好友总数
     *
     * @param userId
     * @return
     */
    @Override
    public int findfriendsCount(Long userId) {
        String sql = "SELECT count(1) FROM FRIENDS WHERE STATE=? AND  (USER_ID=? OR FRIEND_ID=?)";
        return jdbcTemplate.queryForObject(sql, Integer.class, FriendsType.ALREADYFRIENDS.getType(), userId, userId);
    }


    /**
     * 模糊查找用户根据名称
     *
     * @param name
     * @return
     */
    @Override
    public List<UserModel> findUserByLikeName(String name) {
        String sql = "SELECT * FROM USER WHERE NAME LIKE '%" + name + "%'";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(UserModel.class));
    }

    /**
     * 模糊查找好友根据名称
     *
     * @param name
     * @return
     */
    @Override
    public List<UserModel> findFriendUserByLikeName(Long userId, String name) {
        String sql = "SELECT B.* FROM (\n" +
                "SELECT FRIEND_ID AS FRIEND_ID  FROM FRIENDS WHERE STATE=1 AND USER_ID=?\n" +
                "UNION\n" +
                "SELECT USER_ID AS FRIEND_ID FROM FRIENDS WHERE STATE=1 AND FRIEND_ID=? \n" +
                " )A,`USER` B WHERE A.FRIEND_ID =B.ID AND b.`name` like  '%" + name + "%'";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(UserModel.class), userId, userId);
    }


    /**
     * 根据用户编号朋友
     *
     * @param userId
     * @return
     */
    @Override
    public boolean isUserFriends(Long userId, Long friendId) {
        String sql = "SELECT COUNT(1) FROM (\n" +
                "SELECT FRIEND_ID AS FRIEND_ID  FROM FRIENDS WHERE STATE=? AND USER_ID=? AND FRIEND_ID=? \n" +
                "UNION\n" +
                "SELECT USER_ID AS FRIEND_ID FROM FRIENDS WHERE STATE=? AND FRIEND_ID=? AND  USER_ID=?\n" +
                ")A ";

        long count = jdbcTemplate.queryForObject(sql, Long.class, FriendsType.ALREADYFRIENDS.getType(), userId, friendId, FriendsType.ALREADYFRIENDS.getType(), userId, friendId);
        return count > 0;
    }

    /**
     * 根据用户编号朋友
     *
     * @param userId
     * @return
     */
    @Override
    public Page<UserModel> findUserFriendsById(Long userId, Pageable pageable) {
        String sql = "SELECT B.* FROM (\n" +
                "SELECT FRIEND_ID AS FRIEND_ID  FROM FRIENDS WHERE STATE=? AND USER_ID=? \n" +
                "UNION\n" +
                "SELECT USER_ID AS FRIEND_ID FROM FRIENDS WHERE STATE=? AND FRIEND_ID=? \n" +
                ")A,`USER` B WHERE A.FRIEND_ID =B.ID LIMIT ? , ? ";

        List<UserModel> content = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(UserModel.class), FriendsType.ALREADYFRIENDS.getType(), userId, FriendsType.ALREADYFRIENDS.getType(), userId, pageable.getOffset(), pageable.getPageSize());
        sql = "SELECT COUNT(1) FROM (\n" +
                "SELECT FRIEND_ID AS FRIEND_ID  FROM FRIENDS WHERE STATE=? AND USER_ID=? \n" +
                "UNION\n" +
                "SELECT USER_ID AS FRIEND_ID FROM FRIENDS WHERE STATE=? AND FRIEND_ID=? \n" +
                ")A,`USER` B WHERE A.FRIEND_ID =B.ID";

        long count = jdbcTemplate.queryForObject(sql, Long.class, FriendsType.ALREADYFRIENDS.getType(), userId, FriendsType.ALREADYFRIENDS.getType(), userId);
        Page<UserModel> page = new PageImpl<>(content, pageable, count);
        return page;
    }


    /**
     * 得到共同好友数量
     *
     * @param userIdOne
     * @param userIdTwo
     * @return
     */
    @Override
    public int findCommonFriendsCount(Long userIdOne, Long userIdTwo) {
        String sql = "SELECT COUNT(1) FROM ( " +
                "SELECT FRIEND_ID AS FRIEND_ID  FROM FRIENDS WHERE STATE=? AND USER_ID=? \n" +
                "UNION \n" +
                "SELECT USER_ID AS FRIEND_ID FROM FRIENDS WHERE STATE=? AND FRIEND_ID=? \n" +
                ")A,( \n" +
                "SELECT FRIEND_ID AS FRIEND_ID  FROM FRIENDS WHERE STATE=? AND USER_ID=? \n" +
                "UNION \n" +
                "SELECT USER_ID AS FRIEND_ID FROM FRIENDS WHERE STATE=? AND FRIEND_ID=? \n" +
                " )B WHERE A.FRIEND_ID=B.FRIEND_ID";
        return jdbcTemplate.queryForObject(sql, Integer.class, FriendsType.ALREADYFRIENDS.getType(), userIdOne, FriendsType.ALREADYFRIENDS.getType(), userIdOne, FriendsType.ALREADYFRIENDS.getType(), userIdTwo, FriendsType.ALREADYFRIENDS.getType(), userIdTwo);
    }

    /**
     * 得到共同好友
     *
     * @param userIdOne
     * @param userIdTwo
     * @return
     */
    @Override
    public Page<UserModel> findCommonFriends(Long userIdOne, Long userIdTwo, Pageable pageable) {


        String sql = "SELECT D.* FROM (\n" +
                "SELECT A.FRIEND_ID FROM (\n" +
                "SELECT FRIEND_ID AS FRIEND_ID  FROM FRIENDS WHERE STATE=? AND USER_ID=? \n" +
                "UNION\n" +
                "SELECT USER_ID AS FRIEND_ID FROM FRIENDS WHERE STATE=? AND FRIEND_ID=? \n" +
                ")A,(\n" +
                "SELECT FRIEND_ID AS FRIEND_ID  FROM FRIENDS WHERE STATE=? AND USER_ID=? \n" +
                "UNION\n" +
                "SELECT USER_ID AS FRIEND_ID FROM FRIENDS WHERE STATE=? AND FRIEND_ID=? \n" +
                ")B WHERE A.FRIEND_ID=B.FRIEND_ID )C ,USER D WHERE C.FRIEND_ID=D.ID LIMIT ? , ?";

        List<UserModel> content = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(UserModel.class), FriendsType.ALREADYFRIENDS.getType(), userIdOne,
                FriendsType.ALREADYFRIENDS.getType(), userIdOne, FriendsType.ALREADYFRIENDS.getType(), userIdTwo, FriendsType.ALREADYFRIENDS.getType(), userIdTwo, pageable.getOffset(), pageable.getPageSize());
        sql = "SELECT COUNT(1) FROM (\n" +
                "SELECT A.FRIEND_ID FROM (\n" +
                "SELECT FRIEND_ID AS FRIEND_ID  FROM FRIENDS WHERE STATE=? AND USER_ID=? \n" +
                "UNION\n" +
                "SELECT USER_ID AS FRIEND_ID FROM FRIENDS WHERE STATE=? AND FRIEND_ID=? \n" +
                ")A,(\n" +
                "SELECT FRIEND_ID AS FRIEND_ID  FROM FRIENDS WHERE STATE=? AND USER_ID=? \n" +
                "UNION\n" +
                "SELECT USER_ID AS FRIEND_ID FROM FRIENDS WHERE STATE=? AND FRIEND_ID=? \n" +
                ")B WHERE A.FRIEND_ID=B.FRIEND_ID )C ,USER D WHERE C.FRIEND_ID=D.ID";

        long count = jdbcTemplate.queryForObject(sql, Long.class, FriendsType.ALREADYFRIENDS.getType(), userIdOne,
                FriendsType.ALREADYFRIENDS.getType(), userIdOne, FriendsType.ALREADYFRIENDS.getType(), userIdTwo, FriendsType.ALREADYFRIENDS.getType(), userIdTwo);
        Page<UserModel> page = new PageImpl<>(content, pageable, count);
        return page;
    }


    /**
     * 根据教育编号得到校友数量
     *
     * @param educationId
     * @return
     */
    @Override
    public int findSchoolfellowCount(Long educationId) {
        EducationModel educationModel = educationService.findByPrimaryKey(educationId);
        Long userId = educationModel.getUserId();
        String sql = "SELECT COUNT(1) FROM (\n" +
                "SELECT FRIEND_ID AS FRIEND_ID  FROM FRIENDS WHERE STATE=? AND USER_ID=? \n" +
                "UNION\n" +
                "SELECT USER_ID AS FRIEND_ID FROM FRIENDS WHERE STATE=? AND FRIEND_ID=? \n" +
                ")A,(SELECT USER_ID FROM EDUCATION WHERE UNIVERSITY=(SELECT UNIVERSITY FROM EDUCATION WHERE ID=?))B,USER C\n" +
                "WHERE A.FRIEND_ID=B.USER_ID AND B.USER_ID=C.ID";
        return jdbcTemplate.queryForObject(sql, Integer.class, FriendsType.ALREADYFRIENDS.getType(), userId, FriendsType.ALREADYFRIENDS.getType(), userId, educationId);

    }

    /**
     * 根据教育编号得到校友
     *
     * @param educationId
     * @param pageable
     * @return
     */
    @Override
    public Page<UserModel> findSchoolfellow(Long educationId, Pageable pageable) {
        EducationModel educationModel = educationService.findByPrimaryKey(educationId);
        Long userId = educationModel.getUserId();
        String sql = "SELECT C.* FROM (\n" +
                "SELECT FRIEND_ID AS FRIEND_ID  FROM FRIENDS WHERE STATE=? AND USER_ID=? \n" +
                "UNION\n" +
                "SELECT USER_ID AS FRIEND_ID FROM FRIENDS WHERE STATE=? AND FRIEND_ID=? \n" +
                ")A,(SELECT USER_ID FROM EDUCATION WHERE UNIVERSITY=(SELECT UNIVERSITY FROM EDUCATION WHERE ID=?))B,USER C\n" +
                "WHERE A.FRIEND_ID=B.USER_ID AND B.USER_ID=C.ID LIMIT ? , ?";

        List<UserModel> content = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(UserModel.class), FriendsType.ALREADYFRIENDS.getType(), userId, FriendsType.ALREADYFRIENDS.getType(), userId, educationId, pageable.getOffset(), pageable.getPageSize());
        sql = "SELECT COUNT(1) FROM (\n" +
                "SELECT FRIEND_ID AS FRIEND_ID  FROM FRIENDS WHERE STATE=? AND USER_ID=? \n" +
                "UNION\n" +
                "SELECT USER_ID AS FRIEND_ID FROM FRIENDS WHERE STATE=? AND FRIEND_ID=? \n" +
                ")A,(SELECT USER_ID FROM EDUCATION WHERE UNIVERSITY=(SELECT UNIVERSITY FROM EDUCATION WHERE ID=?))B,USER C\n" +
                "WHERE A.FRIEND_ID=B.USER_ID AND B.USER_ID=C.ID";
        long count = jdbcTemplate.queryForObject(sql, Long.class, FriendsType.ALREADYFRIENDS.getType(), userId, FriendsType.ALREADYFRIENDS.getType(), userId, educationId);
        Page<UserModel> page = new PageImpl<>(content, pageable, count);
        return page;
    }

    /**
     * 根据工作编号得到同事数量
     *
     * @param workingId
     * @return
     */
    @Override
    public int findColleagueCount(Long workingId) {
        WorkingExperienceModel workingExperienceModel = workingExperienceService.findByPrimaryKey(workingId);
        Long userId = workingExperienceModel.getUserId();
        String sql = "SELECT COUNT(1) FROM (\n" +
                "SELECT FRIEND_ID AS FRIEND_ID  FROM FRIENDS WHERE STATE=? AND USER_ID=? \n" +
                "UNION\n" +
                "SELECT USER_ID AS FRIEND_ID FROM FRIENDS WHERE STATE=? AND FRIEND_ID=? \n" +
                ")A,(SELECT USER_ID FROM WORKING_EXPERIENCE WHERE COMPANY=(SELECT COMPANY FROM WORKING_EXPERIENCE WHERE ID=?))B,USER C\n" +
                "WHERE A.FRIEND_ID=B.USER_ID AND B.USER_ID=C.ID";
        return jdbcTemplate.queryForObject(sql, Integer.class, FriendsType.ALREADYFRIENDS.getType(), userId, FriendsType.ALREADYFRIENDS.getType(), userId, workingId);

    }

    /**
     * 根据工作编号得到同事
     *
     * @param workingId
     * @param pageable
     * @return
     */
    @Override
    public Page<UserModel> findColleague(Long workingId, Pageable pageable) {
        WorkingExperienceModel workingExperienceModel = workingExperienceService.findByPrimaryKey(workingId);
        Long userId = workingExperienceModel.getUserId();
        String sql = "SELECT C.* FROM (\n" +
                "SELECT FRIEND_ID AS FRIEND_ID  FROM FRIENDS WHERE STATE=? AND USER_ID=? \n" +
                "UNION\n" +
                "SELECT USER_ID AS FRIEND_ID FROM FRIENDS WHERE STATE=? AND FRIEND_ID=? \n" +
                ")A,(SELECT USER_ID FROM WORKING_EXPERIENCE WHERE COMPANY=(SELECT COMPANY FROM WORKING_EXPERIENCE WHERE ID=?))B,USER C\n" +
                "WHERE A.FRIEND_ID=B.USER_ID AND B.USER_ID=C.ID LIMIT ? , ?";

        List<UserModel> content = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(UserModel.class), FriendsType.ALREADYFRIENDS.getType(), userId, FriendsType.ALREADYFRIENDS.getType(), userId, workingId, pageable.getOffset(), pageable.getPageSize());
        sql = "SELECT COUNT(1) FROM (\n" +
                "SELECT FRIEND_ID AS FRIEND_ID  FROM FRIENDS WHERE STATE=? AND USER_ID=? \n" +
                "UNION\n" +
                "SELECT USER_ID AS FRIEND_ID FROM FRIENDS WHERE STATE=? AND FRIEND_ID=? \n" +
                ")A,(SELECT USER_ID FROM WORKING_EXPERIENCE WHERE COMPANY=(SELECT COMPANY FROM WORKING_EXPERIENCE WHERE ID=?))B,USER C\n" +
                "WHERE A.FRIEND_ID=B.USER_ID AND B.USER_ID=C.ID";
        long count = jdbcTemplate.queryForObject(sql, Long.class, FriendsType.ALREADYFRIENDS.getType(), userId, FriendsType.ALREADYFRIENDS.getType(), userId, workingId);
        Page<UserModel> page = new PageImpl<>(content, pageable, count);
        return page;
    }


    /**
     * 得到朋友信息，朋友信息不等于【单个或多个朋友编号】
     *
     * @param userId
     * @return
     */
    @Override
    public Page<UserModel> findUserFriendsNotId(Long userId, Long[] friendsId, Pageable pageable) {

        String sql = "SELECT B.* FROM (\n" +
                "SELECT FRIEND_ID AS FRIEND_ID  FROM FRIENDS WHERE STATE=? AND USER_ID=? \n" +
                "UNION\n" +
                "SELECT USER_ID AS FRIEND_ID FROM FRIENDS WHERE STATE=? AND FRIEND_ID=? \n" +
                ")A,`USER` B WHERE A.FRIEND_ID =B.ID AND B.ID NOT IN(" + StringUtils.join(friendsId) + ") LIMIT ? , ? ";

        List<UserModel> content = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(UserModel.class), FriendsType.ALREADYFRIENDS.getType(), userId, FriendsType.ALREADYFRIENDS.getType(), userId, pageable.getOffset(), pageable.getPageSize());
        sql = "SELECT COUNT(1) FROM (\n" +
                "SELECT FRIEND_ID AS FRIEND_ID  FROM FRIENDS WHERE STATE=? AND USER_ID=? \n" +
                "UNION\n" +
                "SELECT USER_ID AS FRIEND_ID FROM FRIENDS WHERE STATE=? AND FRIEND_ID=? \n" +
                ")A,`USER` B WHERE A.FRIEND_ID =B.ID AND B.ID NOT IN(" + StringUtils.join(friendsId) + ")";

        long count = jdbcTemplate.queryForObject(sql, Long.class, FriendsType.ALREADYFRIENDS.getType(), userId, FriendsType.ALREADYFRIENDS.getType(), userId);
        Page<UserModel> page = new PageImpl<>(content, pageable, count);
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
        List<UserModel> userlist = jdbcTemplate.query(sql, argTypes, userId, FriendsType.APPLYFRIENDS.getType());
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
        List<UserModel> userlist = jdbcTemplate.query(sql, argTypes, userId, FriendsType.APPLYFRIENDS.getType());
        return getBackdropId(userlist);
    }

    /**
     * 分析朋友关系
     *
     * @param userId
     * @param list
     * @return
     */
    public Map<String, Object> findRelation(Long userId, List<String> list) {
        Map<String, Object> relation = new HashedMap();
        List<UserModel> friend = new ArrayList<>();
        List<UserModel> noFriend = new ArrayList<>();
        List<String> noRegister = new ArrayList<>();
        String sql = "";
        for (String s : list) {
            sql = "select count(1) from `user` where mobile_phone =?";
            int count = jdbcTemplate.queryForObject(sql, Integer.class, s);
            if (count > 0) {
                sql = "select * from `user` where mobile_phone =?";
                BeanPropertyRowMapper<UserModel> bp = new BeanPropertyRowMapper<>(UserModel.class);
                UserModel userModel = jdbcTemplate.query(sql, bp, s).get(0);
                if (userModel != null) {
                    List<WorkingExperienceModel> w = findworkingExperienceByUserId(userModel.getId());
                    List<EducationModel> e = findEducationByUserId(userModel.getId());
                    userModel.setWorkingExperiences(w.size() > 0 ? w.subList(0, 1) : w);
                    userModel.setEducations(e.size() > 0 ? e.subList(0, 1) : e);
                }
                sql = "          SELECT count(1) FROM (\n" +
                        "                SELECT FRIEND_ID AS FRIEND_ID  FROM FRIENDS WHERE STATE=1 AND USER_ID=? and friend_id =?\n" +
                        "                UNION\n" +
                        "                SELECT USER_ID AS FRIEND_ID FROM FRIENDS WHERE STATE=1 AND FRIEND_ID=? and user_id=?\n" +
                        "                )A";
                int fcount = jdbcTemplate.queryForObject(sql, Integer.class, userModel.getId(), userId, userModel.getId(), userId);
                if (fcount > 0) {
                    friend.add(userModel);
                } else {
                    noFriend.add(userModel);
                }
            } else {
                noRegister.add(s);
            }

        }
        relation.put("friend", friend);
        relation.put("notFriend", noFriend);
        relation.put("notRegister", noRegister);
        return relation;
    }

    /**
     * 登录
     *
     * @param user
     * @return
     */
    @Override
    public UserModel login(UserModel user) {
        String sql = "select * from user where mobile_phone =?";
        BeanPropertyRowMapper<UserModel> bprm = new BeanPropertyRowMapper<>(UserModel.class);
        List<UserModel> users = jdbcTemplate.query(sql, new Object[]{user.getMobilePhone()}, bprm);
        UserModel existedUser = users.size() > 0 ? users.get(0) : null;
        if (null == existedUser) {
            HRErrorCode.throwBusinessException(HRErrorCode.USER_NOT_EXISTED);
        }

        if (!DigestUtils.md5Hex(user.getPassword()).equals(existedUser.getPassword())) {
            HRErrorCode.throwBusinessException(HRErrorCode.PASSWORD_INCORRECT);
        }

        return users.size() > 0 ? users.get(0) : null;
    }

    /**
     * 注册
     *
     * @param user
     * @return
     */
    @Override
    public void register(UserModel user, String captcha) {
        if (null == captcha || captcha.equals("")) {
            HRErrorCode.throwBusinessException(HRErrorCode.CAPTCHA_IS_NULL);
        }
        if (null == user.getMobilePhone() || user.getMobilePhone().equals("")) {
            HRErrorCode.throwBusinessException(HRErrorCode.USER_IS_NULL);
        }
        if (null == user.getPassword() || user.getPassword().equals("")) {
            HRErrorCode.throwBusinessException(HRErrorCode.PASSWORD_ID_NULL);
        }


        create(user);

    }

    @Override
    public UserModel findUserByAccount(String mobilePhone) {
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
