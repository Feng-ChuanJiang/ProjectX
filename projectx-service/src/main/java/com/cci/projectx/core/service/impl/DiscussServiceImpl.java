package com.cci.projectx.core.service.impl;

import com.cci.projectx.core.HRErrorCode;
import com.cci.projectx.core.entity.Discuss;
import com.cci.projectx.core.model.*;
import com.cci.projectx.core.repository.DiscussRepository;
import com.cci.projectx.core.service.DiscussInviteService;
import com.cci.projectx.core.service.DiscussPermissionService;
import com.cci.projectx.core.service.DiscussService;
import com.cci.projectx.core.service.UserService;
import com.wlw.pylon.core.beans.mapping.BeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class DiscussServiceImpl implements DiscussService {

	@Autowired
	private BeanMapper beanMapper;

	@Autowired
	private DiscussRepository discussRepo;

	@Autowired
	private DiscussPermissionService permissionService;

	@Autowired
	private DiscussInviteService inviteService;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	private UserService userService;

	@Transactional
	@Override
	public int create(DiscussModel discussModel) {
		return createSelective(discussModel);
	}

	@Transactional
	@Override
	public int createSelective(DiscussModel discussModel) {
		Discuss discuss=beanMapper.map(discussModel, Discuss.class);
		int tag=discussRepo.insertSelective(discuss);
		discussModel.setId(discuss.getId());
		List<Long> permissions=discussModel.getPermissionUserIds();
		List<Long> incites=discussModel.getInviteUserIds();
		if(discussModel.getPermissionType()==2){
			if(permissions.size()==0){
				HRErrorCode.throwBusinessException(HRErrorCode.Discuss_NOT_USERID);
			}
			//添加权限
			for (Long userId : permissions) {
				DiscussPermissionModel discussPermission=new DiscussPermissionModel();
				discussPermission.setUserId(userId);
				discussPermission.setDiscussId(discuss.getId());
				permissionService.create(discussPermission);
			}
		}
		if(incites!=null&&incites.size()>0){
			//添加邀请
			for (Long incite : incites) {
				DiscussInviteModel discussInvite=new DiscussInviteModel();
				discussInvite.setDiscussId(discuss.getId());
				discussInvite.setUserId(discuss.getUserId());
				discussInvite.setFriendId(incite);
				inviteService.create(discussInvite);
			}
		}

		return tag;
	}

	@Transactional
	@Override
	public int deleteByPrimaryKey(Long id) {
		permissionService.deleteByPrimaryDiscussId(id);
		inviteService.deleteByPrimaryDiscussId(id);
		return discussRepo.deleteByPrimaryKey(id);
	}

	@Transactional(readOnly = true)
	@Override
	public DiscussModel findByPrimaryKey(Long id) {
		Discuss discuss = discussRepo.selectByPrimaryKey(id);
		return beanMapper.map(discuss, DiscussModel.class);
	}
	@Transactional(readOnly = true)
	@Override
	public DiscussModel findByPrimaryKey(Long userId,String  name) {
		String sql="select * from discuss where user_id=? and title =?";
		List<DiscussModel> discuss=jdbcTemplate.query(sql,new BeanPropertyRowMapper<>(DiscussModel.class),userId,name);
		return discuss.size()>0?discuss.get(0):null;
	}


	@Transactional(readOnly = true)
	@Override
	public long selectCount(DiscussModel discussModel) {
		return discussRepo.selectCount(beanMapper.map(discussModel, Discuss.class));
	}

	@Transactional(readOnly = true)
	@Override
	public List<DiscussMyModel> selectPage(DiscussModel discussModel, Pageable pageable) {
		Discuss discuss = beanMapper.map(discussModel, Discuss.class);
		return beanMapper.mapAsList(discussRepo.selectPage(discuss,pageable),DiscussMyModel.class);
	}

	@Transactional
	@Override
	public int updateByPrimaryKey(DiscussModel discussModel) {
		return updateByPrimaryKeySelective(discussModel);
	}
	
	@Transactional
	@Override
	public int updateByPrimaryKeySelective(DiscussModel discussModel) {
		Discuss discuss=beanMapper.map(discussModel, Discuss.class);
		int tag=discussRepo.updateByPrimaryKeySelective(discuss);
		List<Long> permissions=discussModel.getPermissionUserIds();
		List<Long> incites=discussModel.getInviteUserIds();

		if(discussModel.getType()!=null&&discussModel.getType()==2){
			if(permissions.size()>0){
				//修改权限
				for (Long userId : permissions) {
					DiscussPermissionModel discussPermission=new DiscussPermissionModel();
					discussPermission.setUserId(userId);
					discussPermission.setDiscussId(discuss.getId());
					permissionService.deleteByPrimary(discussPermission);
					permissionService.create(discussPermission);
				}
			}
		}
		if(incites!=null&&incites.size()>0){
			//修改邀请
			for (Long incite : incites) {
				DiscussInviteModel discussInvite=new DiscussInviteModel();
				discussInvite.setDiscussId(discuss.getId());
				discussInvite.setUserId(discuss.getUserId());
				discussInvite.setFriendId(incite);
				inviteService.deleteByPrimary(discussInvite);
				inviteService.create(discussInvite);
			}
		}


		return tag;
	}

	/**
	 * 根据用户编号和研讨会名称得到研讨会的所有用户
	 * @param userId
	 * @param title
     * @return
     */
	@Transactional(readOnly = true)
	@Override
	public List<DiscussMyModel> findUserByPrimary(Long userId,String title) {
        String sql="SELECT A.*,B.`NAME` USERNAME,B.photos USERPHOTO ,B.mobile_phone mobilePhone FROM DISCUSS A,USER B WHERE A.USER_ID IN(             \n" +
				"SELECT FRIEND_ID AS FRIEND_ID  FROM FRIENDS WHERE STATE=1 AND USER_ID=? \n" +
				"UNION\n" +
				"SELECT USER_ID AS FRIEND_ID FROM FRIENDS WHERE STATE=1 AND FRIEND_ID=?\n" +
				") AND A.TITLE=? AND A.USER_ID=B.ID";
		BeanPropertyRowMapper<DiscussMyModel> bpr=new BeanPropertyRowMapper<>(DiscussMyModel.class);
		List<DiscussMyModel> discussMyModels=jdbcTemplate.query(sql,bpr,userId,userId,title);

		return discussMyModels;
	}

	@Transactional(readOnly = true)
	@Override
	public List<DiscussMyModel> selectDiscussAll(Long userId) {
		String sql="SELECT E.* FROM (\n" +
				"SELECT * FROM DISCUSS A WHERE A.PERMISSION_TYPE=1 AND A.USER_ID IN(             \n" +
				"SELECT FRIEND_ID AS FRIEND_ID  FROM FRIENDS WHERE STATE=1 AND USER_ID=? \n" +
				"UNION\n" +
				"SELECT USER_ID AS FRIEND_ID FROM FRIENDS WHERE STATE=1 AND FRIEND_ID=?\n" +
				")\n" +
				"UNION ALL\n" +
				"SELECT B.* FROM DISCUSS B,DISCUSS_PERMISSION C WHERE   b.type=2 and B.PERMISSION_TYPE=2 \n" +
				"AND B.ID=C.DISCUSS_ID AND C.USER_ID=?\n" +
				")E WHERE E.USER_ID<>?";
		BeanPropertyRowMapper<DiscussMyModel> bpr=new BeanPropertyRowMapper<>(DiscussMyModel.class);
		//全部研讨会
		List<DiscussMyModel> discussModels=jdbcTemplate.query(sql,bpr,userId,userId,userId,userId);
        sql="SELECT * FROM DISCUSS WHERE USER_ID=? and type=1";
		//我的研讨会
		List<DiscussMyModel> discussMyModels=jdbcTemplate.query(sql,bpr,userId);
		Map<String ,DiscussMyModel> discussMyModelMap =new LinkedHashMap<>();
		//设置未加入的研讨会
		for (DiscussMyModel discussModelm : discussModels) {
			//用map去重
			DiscussMyModel discussModel=null;
			if(discussMyModelMap.get(discussModelm.getTitle())!=null){
				discussModel=discussMyModelMap.get(discussModelm.getTitle());
			}else{
				discussModel=discussModelm;
			}
			//验证我有没有加入这个研讨会
			DiscussModel discussModel1=findByPrimaryKey(userId,discussModel.getTitle());
			if(discussModel1!=null){
				discussModel.setJoinType(1);
			}else{
				discussModel.setJoinType(0);
			}
			//验证这条研讨会有没有人邀请我
          Long inciteUserId=getInciteUserId(userId,discussModel.getId());
			if(inciteUserId!=null){
				//查询邀请人
				UserModel user=userService.findUserShortById(inciteUserId);
				List<UserModel> inviteUser=discussModel.getInviteUsers();
				if(inviteUser!=null){
					inviteUser.add(user);
				}else{
					inviteUser=new ArrayList<>();
					inviteUser.add(user);
				}
				discussModel.setInviteUsers(inviteUser);
			}
			//覆盖重复的
			discussMyModelMap.put(discussModel.getTitle(),discussModel);

		}
		//重新赋值
		List<DiscussMyModel> discussMyModelList=new ArrayList<>();
		for (String s : discussMyModelMap.keySet()) {
			discussMyModelList.add(discussMyModelMap.get(s));
		}
		//设置自己原创的研讨会
		for (DiscussMyModel discussModel : discussMyModels) {
			discussModel.setJoinType(1);
		}
		discussMyModelList.addAll(discussMyModels);

		return discussMyModelList;
	}

	private Long getInciteUserId(Long userId,Long discussId){
		String sql="select user_id from discuss_invite where friend_id=? and discuss_id=?";
		List<Long> list=jdbcTemplate.queryForList(sql,Long.class,userId,discussId);
		return list.size()>0?list.get(0):null;
	}
}
