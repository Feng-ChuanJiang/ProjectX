package com.cci.projectx.core.service.impl;

import com.cci.projectx.core.entity.UserInteractCircle;
import com.cci.projectx.core.model.UserInteractCircleModel;
import com.cci.projectx.core.repository.UserInteractCircleRepository;
import com.cci.projectx.core.service.UserInteractCircleService;
import com.wlw.pylon.core.beans.mapping.BeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserInteractCircleServiceImpl implements UserInteractCircleService {

	@Autowired
	private BeanMapper beanMapper;

	@Autowired
	private UserInteractCircleRepository userInteractCircleRepo;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Transactional
	@Override
	public int create(UserInteractCircleModel userInteractCircleModel) {
		return userInteractCircleRepo.insert(beanMapper.map(userInteractCircleModel, UserInteractCircle.class));
	}

	@Transactional
	@Override
	public int createSelective(UserInteractCircleModel userInteractCircleModel) {
		return userInteractCircleRepo.insertSelective(beanMapper.map(userInteractCircleModel, UserInteractCircle.class));
	}

	@Transactional
	@Override
	public int deleteByPrimaryKey(Long id) {
		return userInteractCircleRepo.deleteByPrimaryKey(id);
	}

	@Transactional(readOnly = true)
	@Override
	public UserInteractCircleModel findByPrimaryKey(Long id) {
		UserInteractCircle userInteractCircle = userInteractCircleRepo.selectByPrimaryKey(id);
		return beanMapper.map(userInteractCircle, UserInteractCircleModel.class);
	}

	@Transactional(readOnly = true)
	@Override
	public long selectCount(UserInteractCircleModel userInteractCircleModel) {
		return userInteractCircleRepo.selectCount(beanMapper.map(userInteractCircleModel, UserInteractCircle.class));
	}

	@Transactional(readOnly = true)
	@Override
	public List<UserInteractCircleModel> selectPage(UserInteractCircleModel userInteractCircleModel, Pageable pageable) {
		UserInteractCircle userInteractCircle = beanMapper.map(userInteractCircleModel, UserInteractCircle.class);
		return beanMapper.mapAsList(userInteractCircleRepo.selectPage(userInteractCircle,pageable),UserInteractCircleModel.class);
	}

	@Transactional
	@Override
	public int updateByPrimaryKey(UserInteractCircleModel userInteractCircleModel) {
		return userInteractCircleRepo.updateByPrimaryKey(beanMapper.map(userInteractCircleModel, UserInteractCircle.class));
	}
	
	@Transactional
	@Override
	public int updateByPrimaryKeySelective(UserInteractCircleModel userInteractCircleModel) {
		return userInteractCircleRepo.updateByPrimaryKeySelective(beanMapper.map(userInteractCircleModel, UserInteractCircle.class));
	}

	@Transactional
	@Override
	public int deleteByUserIdandInteractId(Long userId ,Long interactId) {
		String sql ="DELETE FROM USER_INTERACT_CIRCLE WHERE USER_ID=? AND INTERACT_ID=?";
		return jdbcTemplate.update(sql,userId,interactId);
	}

}
