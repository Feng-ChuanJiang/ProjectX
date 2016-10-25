package com.cci.projectx.core.service.impl;

import com.cci.projectx.core.FriendsType;
import com.cci.projectx.core.HRErrorCode;
import com.cci.projectx.core.JPushPush;
import com.cci.projectx.core.JdbcTempateHelp;
import com.cci.projectx.core.entity.RunFriends;
import com.cci.projectx.core.entity.RunInfo;
import com.cci.projectx.core.model.FriendsModel;
import com.cci.projectx.core.model.RunFriendsModel;
import com.cci.projectx.core.model.RunInfoModel;
import com.cci.projectx.core.model.UserModel;
import com.cci.projectx.core.repository.RunInfoRepository;
import com.cci.projectx.core.service.RunInfoService;
import com.cci.projectx.core.service.UserService;
import com.wlw.pylon.core.beans.mapping.BeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class RunInfoServiceImpl implements RunInfoService {

    @Autowired
    private BeanMapper beanMapper;

    @Autowired
    private RunInfoRepository runInfoRepo;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private JdbcTempateHelp jdbcTempateHelp;

    @Autowired
    private UserService userService;

    @Autowired
    private JPushPush jPushPush;

    @Transactional
    @Override
    public int create(RunInfoModel runInfoModel) {
        RunInfo runInfo = beanMapper.map(runInfoModel, RunInfo.class);
        String sql = "select count(1) from runinfo where user_id=?";
        int count = jdbcTemplate.queryForObject(sql, Integer.class, runInfo.getUserId());
        if (count > 0) {
            HRErrorCode.throwBusinessException(HRErrorCode.RUNUSER_HAVE_EXISIED);
        }
        return runInfoRepo.insert(runInfo);
    }

    @Transactional
    @Override
    public int createSelective(RunInfoModel runInfoModel) {
        return runInfoRepo.insertSelective(beanMapper.map(runInfoModel, RunInfo.class));
    }

    @Transactional
    @Override
    public int deleteByPrimaryKey(Long id) {
        return runInfoRepo.deleteByPrimaryKey(id);
    }

    @Transactional(readOnly = true)
    @Override
    public RunInfoModel findByPrimaryKey(Long id) {
        RunInfo runInfo = runInfoRepo.selectByPrimaryKey(id);
        return beanMapper.map(runInfo, RunInfoModel.class);
    }

    @Transactional(readOnly = true)
    @Override
    public long selectCount(RunInfoModel runInfoModel) {
        return runInfoRepo.selectCount(beanMapper.map(runInfoModel, RunInfo.class));
    }

    @Transactional(readOnly = true)
    @Override
    public List<RunInfoModel> selectPage(RunInfoModel runInfoModel, Pageable pageable) {
        RunInfo runInfo = beanMapper.map(runInfoModel, RunInfo.class);
        return beanMapper.mapAsList(runInfoRepo.selectPage(runInfo, pageable), RunInfoModel.class);
    }

    @Transactional
    @Override
    public int updateByPrimaryKey(RunInfoModel runInfoModel) {
        return runInfoRepo.updateByPrimaryKey(beanMapper.map(runInfoModel, RunInfo.class));
    }

    @Transactional
    @Override
    public int updateByPrimaryKeySelective(RunInfoModel runInfoModel) {
        return runInfoRepo.updateByPrimaryKeySelective(beanMapper.map(runInfoModel, RunInfo.class));
    }

    /**
     * 添加跑友
     *
     * @param friend
     * @return
     */
    @Transactional
    @Override
    public int addFriends(RunFriendsModel friend) {
        RunFriends frien=beanMapper.map(friend,RunFriends.class);
        //推送
        UserModel userModel = userService.findUserShortById(friend.getUserId());
        jPushPush.buildPushObject_all_alias_alert(friend.getFriendId(), userModel.getName() + JPushPush.RUN_APPLY, jPushPush.convertBean(userModel));
        frien.setState(new Long(FriendsType.APPLYFRIENDS.getType()));
        return jdbcTempateHelp.add(frien);
    }

    /**
     * 通过验证
     *
     * @param friend
     * @return
     */
    @Transactional
    @Override
    public int updateFriends(RunFriendsModel friend) {
        RunFriends frien=beanMapper.map(friend,RunFriends.class);
        //推送
        UserModel userModel = userService.findUserShortById(friend.getUserId());
        jPushPush.buildPushObject_all_alias_alert(friend.getFriendId(), userModel.getName() + JPushPush.RUN_CONSENT, jPushPush.convertBean(userModel));
        friend.setState(new Long(FriendsType.APPLYFRIENDS.getType()));
        //查询是佛已经是好友
        String sql = "SELECT COUNT(1) FROM FRIENDS WHERE (USER_ID=? AND FRIEND_ID=? OR USER_ID=? AND FRIEND_ID=? )AND STATE =?";
        int userCount = jdbcTemplate.queryForObject(sql, Integer.class, friend.getUserId(), friend.getFriendId(), friend.getFriendId(), friend.getUserId(), FriendsType.ALREADYFRIENDS.getType());
        //查询是否等待验证
        sql = "SELECT COUNT(1) FROM FRIENDS WHERE (USER_ID=? AND FRIEND_ID=? OR USER_ID=? AND FRIEND_ID=? )AND STATE =?";
        int appUser = jdbcTemplate.queryForObject(sql, Integer.class, friend.getUserId(), friend.getFriendId(), friend.getFriendId(), friend.getUserId(), FriendsType.APPLYFRIENDS.getType());
        FriendsModel friendsModel = new FriendsModel();
        friendsModel.setFriendId(friend.getFriendId());
        friendsModel.setUserId(friend.getUserId());
        friendsModel.setState(new Long(FriendsType.ALREADYFRIENDS.getType()));
        //添加好友
        if (userCount == 0) {
            userService.addFriends(friendsModel);
        }
        //更新好友状态
        if (appUser == 0) {
            userService.updateFriends(friendsModel);
        }

        sql = "UPDATE run_friends SET state=? where (user_id=? and friend_id=? or  user_id=? and friend_id=?)";
        return jdbcTemplate.update(sql, FriendsType.ALREADYFRIENDS.getType(), friend.getUserId(), friend.getFriendId(), friend.getFriendId(), friend.getUserId());
    }


    /**
     * 根据用户编号得到全部跑友
     *
     * @param userId
     * @return
     */
    @Override
    public Page<UserModel> findUserRunFriendsById(Long userId, Pageable pageable) {
        String sql = "SELECT B.* FROM (\n" +
                "SELECT FRIEND_ID AS FRIEND_ID  FROM run_friends WHERE STATE=? AND USER_ID=? \n" +
                "UNION\n" +
                "SELECT USER_ID AS FRIEND_ID FROM run_friends WHERE STATE=? AND FRIEND_ID=? \n" +
                ")A,`USER` B WHERE A.FRIEND_ID =B.ID LIMIT ? , ? ";

        List<UserModel> content = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(UserModel.class), FriendsType.ALREADYFRIENDS.getType(), userId, FriendsType.ALREADYFRIENDS.getType(), userId, pageable.getOffset(), pageable.getPageSize());
        sql = "SELECT COUNT(1) FROM (\n" +
                "SELECT FRIEND_ID AS FRIEND_ID  FROM run_friends WHERE STATE=? AND USER_ID=? \n" +
                "UNION\n" +
                "SELECT USER_ID AS FRIEND_ID FROM run_friends WHERE STATE=? AND FRIEND_ID=? \n" +
                ")A,`USER` B WHERE A.FRIEND_ID =B.ID";

        long count = jdbcTemplate.queryForObject(sql, Long.class, FriendsType.ALREADYFRIENDS.getType(), userId, FriendsType.ALREADYFRIENDS.getType(), userId);
        Page<UserModel> page = new PageImpl<>(content, pageable, count);
        return page;
    }

    /**
     * 得到正在申请的朋友
     *
     * @param userId
     * @return
     */
    @Override
    public List<Map<String, Object>> findApplyforRunFriends(Long userId) {
        String sql = "SELECT U.* FROM USER U,run_friends F WHERE U.ID=F.FRIEND_ID AND F.USER_ID=? AND F.STATE=? ";
        BeanPropertyRowMapper<UserModel> argTypes = new BeanPropertyRowMapper<>(UserModel.class);
        List<UserModel> userlist = jdbcTemplate.query(sql, argTypes, userId, FriendsType.APPLYFRIENDS.getType());
        return userService.getBackdropId(userlist);
    }

    /**
     * 得到等待验证的朋友
     *
     * @param userId
     * @return
     */
    @Override
    public List<Map<String, Object>> findWaitingRunFriends(Long userId) {
        String sql = "SELECT U.* FROM USER U,run_friends F WHERE U.ID=F.USER_ID AND F.FRIEND_ID=? AND F.STATE=? ";
        BeanPropertyRowMapper<UserModel> argTypes = new BeanPropertyRowMapper<>(UserModel.class);
        List<UserModel> userlist = jdbcTemplate.query(sql, argTypes, userId, FriendsType.APPLYFRIENDS.getType());
        return userService.getBackdropId(userlist);
    }

    /**
     * 计算圈中跑友
     *
     * @param userId
     * @return
     */
    @Override
    public List<UserModel> findAssignRunFriends(Long userId, Pageable pageable) {
        RunInfoModel runInfoModel = new RunInfoModel();
        runInfoModel.setUserId(userId);
        List<RunInfoModel> runInfoModels = selectPage(runInfoModel, pageable);
        //区域
        RunInfoModel runInfoModel1 = runInfoModels.size() > 0 ? runInfoModels.get(0) : null;
        List<UserModel> userlist = new ArrayList<>();
        if (null != runInfoModel1) {
            //要验证的用户
            List<RunInfoModel> runInfoModelss = selectPage(new RunInfoModel(), pageable);
            for (RunInfoModel infoModels : runInfoModelss) {
                boolean ishave = isInPolygon(infoModels, runInfoModel1);
                if (ishave) {
                    //得到用户简称
                    UserModel userModel = userService.findUserShortById(infoModels.getUserId());
                    userlist.add(userModel);
                }
            }
        }
        return userlist;
    }


    /**
     * 判断当前位置是否在围栏内
     *
     * @param runInfo
     * @param runInfoTow
     * @return
     */
    private boolean isInPolygon(RunInfoModel runInfo, RunInfoModel runInfoTow) {
        if (runInfo.getLongitude() == null || runInfo.getLatitude() == null) {
            return false;
        }
        //需要计算的坐标
        double p_x = runInfo.getLongitude().doubleValue();
        double p_y = runInfo.getLatitude().doubleValue();
        Point2D.Double point = new Point2D.Double(p_x, p_y);
        //需要计算的区域
        List<Point2D.Double> pointList = new ArrayList<>();
        Point2D.Double polygonPoint1 = new Point2D.Double(runInfoTow.getLongitude1().doubleValue(), runInfoTow.getLatitude1().doubleValue());
        pointList.add(polygonPoint1);
        Point2D.Double polygonPoint2 = new Point2D.Double(runInfoTow.getLongitude2().doubleValue(), runInfoTow.getLatitude2().doubleValue());
        pointList.add(polygonPoint2);
        Point2D.Double polygonPoint3 = new Point2D.Double(runInfoTow.getLongitude3().doubleValue(), runInfoTow.getLatitude3().doubleValue());
        pointList.add(polygonPoint3);
        Point2D.Double polygonPoint4 = new Point2D.Double(runInfoTow.getLongitude4().doubleValue(), runInfoTow.getLatitude4().doubleValue());
        pointList.add(polygonPoint4);

        return checkWithJdkGeneralPath(point, pointList);
    }

    /**
     * 返回一个点是否在一个多边形区域内
     *
     * @param point
     * @param polygon
     * @return
     */
    private boolean checkWithJdkGeneralPath(Point2D.Double point, List<Point2D.Double> polygon) {
        java.awt.geom.GeneralPath p = new java.awt.geom.GeneralPath();

        Point2D.Double first = polygon.get(0);
        p.moveTo(first.x, first.y);
        polygon.remove(0);
        for (Point2D.Double d : polygon) {
            p.lineTo(d.x, d.y);
        }
        p.lineTo(first.x, first.y);
        p.closePath();
        return p.contains(point);

    }


}
