package com.cci.projectx.core.service.impl;

import com.cci.projectx.core.JdbcTempateHelp;
import com.cci.projectx.core.entity.S;
import com.cci.projectx.core.model.EducationModel;
import com.cci.projectx.core.model.SModel;
import com.cci.projectx.core.model.WorkingExperienceModel;
import com.cci.projectx.core.repository.SRepository;
import com.cci.projectx.core.service.SService;
import com.cci.projectx.core.service.UserService;
import com.wlw.pylon.core.beans.mapping.BeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class SServiceImpl implements SService {

	@Autowired
	private BeanMapper beanMapper;

	@Autowired
	private SRepository sRepo;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	private JdbcTempateHelp jdbcTempateHelp;

	@Autowired
	private UserService userService;

	@Transactional
	@Override
	public int create(SModel sModel) {
		return sRepo.insert(beanMapper.map(sModel, S.class));
	}

	@Transactional
	@Override
	public int createSelective(SModel sModel) {
		return sRepo.insertSelective(beanMapper.map(sModel, S.class));
	}

	@Transactional
	@Override
	public int deleteByPrimaryKey(Long id) {
		return sRepo.deleteByPrimaryKey(id);
	}

	@Transactional(readOnly = true)
	@Override
	public SModel findByPrimaryKey(Long id) {
		S s = sRepo.selectByPrimaryKey(id);
		return beanMapper.map(s, SModel.class);
	}

	@Transactional(readOnly = true)
	@Override
	public long selectCount(SModel sModel) {
		return sRepo.selectCount(beanMapper.map(sModel, S.class));
	}

	@Transactional(readOnly = true)
	@Override
	public List<SModel> selectPage(SModel sModel,Pageable pageable) {
		S s = beanMapper.map(sModel, S.class);
		return beanMapper.mapAsList(sRepo.selectPage(s,pageable),SModel.class);
	}

	@Transactional
	@Override
	public int updateByPrimaryKey(SModel sModel) {
		return sRepo.updateByPrimaryKey(beanMapper.map(sModel, S.class));
	}
	
	@Transactional
	@Override
	public int updateByPrimaryKeySelective(SModel sModel) {
		return sRepo.updateByPrimaryKeySelective(beanMapper.map(sModel, S.class));
	}


	@Value("${s.L}")
	private  double L;
	@Value("${s.Q}")
	private  double Q;
	/**
	 * 根据录入的参数更新关系表数据
	 * @param userId
	 * @param relationId
	 * @param userTime
	 * @param relationTime
	 * @param data
	 * @return
	 */
	public int updateSRelation(Long userId, Long relationId, Date userTime, Date relationTime, String data) {
		String sql = "";
		if (data.toLowerCase().equals("x")) {
			sql = " UPDATE RELATION SET DATAX='X' WHERE S_ID=( SELECT ID FROM S WHERE USER_ID=? AND CREAT_TIME=? )\n" +
					" AND RELATION_ID=(SELECT ID FROM S WHERE USER_ID=? AND CREAT_TIME=?)";
		} else {
			sql = " UPDATE RELATION SET DATAY='Y' WHERE S_ID=( SELECT ID FROM S WHERE USER_ID=? AND CREAT_TIME=? )\n" +
					" AND RELATION_ID=(SELECT ID FROM S WHERE USER_ID=? AND CREAT_TIME=?)";
		}
		return jdbcTemplate.update(sql, new Object[]{userId, userTime, relationId, relationTime});
	}

	/**
	 * 根据userId删除S及S的关系信息
	 * 带反向删除
	 * @param id
	 * @return
	 */
	public int delSByUserId(Long id) {
		String sql = "DELETE FROM S WHERE USER_ID=?";
		jdbcTemplate.update(sql, new Object[]{id});
		sql = "DELETE FROM RELATION WHERE S_ID=? OR RELATION_ID=?";
		return jdbcTemplate.update(sql, new Object[]{id, id});

	}

	/**
	 * 根据用户编号得到和自己有关系的用户
	 * @param id
	 * @return
	 */
	public Page<Map<String,Object>> getUserBySObjcet(Long id, Pageable pageable) {
		String sql = "SELECT A.ID USERID,A.PHOTOS,A.NAME  FROM USER A INNER JOIN RELATION B ON A.ID=B.RELATION_ID WHERE B.S_ID=?";
		Page<Map<String,Object>> pages=jdbcTempateHelp.findBySqlForPage(sql, new Object[]{id},pageable);
		List<Map<String, Object>> maps=pages.getContent();
		for (Map<String, Object> map : maps) {
			Long userId=Long.parseLong(map.get("USERID").toString());
			if(userId!=null&&!userId.toString().equals("")){
				List<WorkingExperienceModel> workingExperiences = userService.findworkingExperienceByUserId(userId);
				List<EducationModel> educations = userService.findEducationByUserId(userId);
				map.put("friendSize", getFriendSize(userId));//全部好友总数
				map.put("joinFriendSize", getJoinFriend(userId, id).size());//共同好友总数
				map.put("joinFriend", getJoinFriend(userId, id));//共同好友编号
				map.put("education", educations.size() > 0 ? educations.subList(0, 1) : educations);//最新教育背景
				map.put("workingExperience", workingExperiences.size() > 0 ? workingExperiences.subList(0, 1) : workingExperiences);//最新工作背景
			}
		}
		return pages;
	}

	/**
	 * 根据用户编号得到全部好友总数
	 * @param userId
	 * @return
	 */
	public int getFriendSize(Long userId){
		String sql="SELECT COUNT(1) COUNT FROM FRIENDS WHERE STATE=1 AND  USER_ID=? OR FRIEND_ID=? ";
		List<Integer> count=jdbcTemplate.queryForList(sql, new Object[]{userId,userId}, Integer.class);
		return count.size()>0?count.get(0):0;
	}

	/**
	 * 根据用户编号和朋友编号得到全部共同好友的编号
	 * @param userId
	 * @return
	 */
	public List<Integer> getJoinFriend(Long userId,Long friendId){
		String sql="SELECT CASE WHEN T.USERID IS NULL THEN T.FRIENID ELSE T.USERID END AS FRIENDID FROM (\n" +
				"SELECT  CASE A.USER_ID WHEN B.USER_ID THEN A.USER_ID WHEN B.FRIEND_ID THEN A.USER_ID END AS USERID,\n" +
				"        CASE A.FRIEND_ID WHEN B.USER_ID THEN A.FRIEND_ID WHEN B.FRIEND_ID THEN A.FRIEND_ID END AS FRIENID FROM \n" +
				"(SELECT USER_ID,FRIEND_ID  FROM FRIENDS WHERE STATE=1 AND( USER_ID=? OR FRIEND_ID=?)AND USER_ID<>? AND FRIEND_ID <>?) AS A,\n" +
				"(SELECT USER_ID,FRIEND_ID  FROM FRIENDS WHERE STATE=1 AND( USER_ID=? OR FRIEND_ID=?)AND USER_ID<>? AND FRIEND_ID <>?) AS B\n" +
				"WHERE A.USER_ID=B.USER_ID OR A.USER_ID=B.FRIEND_ID OR A.FRIEND_ID=B.USER_ID OR A.FRIEND_ID=B.FRIEND_ID)T";
		List<Integer> count=jdbcTemplate.queryForList(sql, new Object[]{userId,userId,friendId,friendId,friendId,friendId,userId,userId}, Integer.class);
		return count;
	}


	/**
	 * 筛选有关系的朋友
	 * @param s
	 */
	public  void friendSearch(S s){
		StringBuffer sql=new StringBuffer("  ");
		sql.append("SELECT * FROM ( SELECT ID,USER_ID,CREAT_TIME,U,V,M,N,A,B,C,D,TEST1-TEST2 TEST FROM (" );
		//筛选取出test1
		sql.append(" SELECT T.*,CASE WHEN V> "+s.getV()+" THEN V WHEN V<"+s.getV()+" THEN "+s.getV()+" END TEST1," );
		//筛选取出test2
		sql.append(" CASE WHEN U<"+s.getU()+" THEN "+s.getU()+" WHEN U>"+s.getU()+" THEN U END TEST2 FROM S AS T WHERE " );
		//筛选取出M区间的S
		sql.append(" M < "+(s.getM()+L)+" AND M >  "+(s.getM()-L));
		//筛选取出N区间的S
		sql.append(" AND N < "+(s.getN()+L)+" AND N >  "+(s.getN()-L));
		//筛选取出V大于U的S
		sql.append(" AND V > "+(s.getU()));
		//筛选取出U小于V的S
		sql.append(" AND U < "+(s.getV()));
		//筛选取出TEST大于Q
		sql.append(")A ) B WHERE TEST >"+Q);
		//筛选取出A小于B
		sql.append(" AND A < "+(s.getB()));
		//筛选取出B大于A
		sql.append(" AND B > "+(s.getA()));
		//筛选取出C小于D
		sql.append(" AND C < "+(s.getD()));
		//筛选取出D大于C
		sql.append(" AND D > "+(s.getC()));
		BeanPropertyRowMapper<S> bpr=new BeanPropertyRowMapper<>(S.class);
		List<S> ss=jdbcTemplate.query(sql.toString(),bpr);
		saveRelation(ss,s.getId());
	}

	/**
	 * 插入关系信息
	 * @param ss
	 * @param SId
	 * @return
	 */
	public void saveRelation(List<S> ss,Long SId){
		if(ss.size()>0){
			String sql="INSERT INTO RELATION (S_ID,RELATION_ID) VALUES(?,?)";
			for (S s1 : ss) {
				Long relationId=s1.getId();
				jdbcTemplate.update(sql,new Object[]{SId,relationId});
				jdbcTemplate.update(sql,new Object[]{relationId,SId});
			}
		}
	}

}
