package com.cci.projectx.core.service.impl;

import com.cci.projectx.core.entity.UserCircle;
import com.cci.projectx.core.model.UserCircleModel;
import com.cci.projectx.core.repository.UserCircleRepository;
import com.cci.projectx.core.service.UserCircleService;
import com.wlw.pylon.core.beans.mapping.BeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserCircleServiceImpl implements UserCircleService {

	@Autowired
	private BeanMapper beanMapper;

	@Autowired
	private UserCircleRepository userCircleRepo;

	@Transactional
	@Override
	public int create(UserCircleModel userCircleModel) {
		return userCircleRepo.insert(beanMapper.map(userCircleModel, UserCircle.class));
	}

	@Transactional
	@Override
	public int createSelective(UserCircleModel userCircleModel) {
		return userCircleRepo.insertSelective(beanMapper.map(userCircleModel, UserCircle.class));
	}

	@Transactional
	@Override
	public int deleteByPrimaryKey(Long id) {
		return userCircleRepo.deleteByPrimaryKey(id);
	}

	@Transactional(readOnly = true)
	@Override
	public UserCircleModel findByPrimaryKey(Long id) {
		UserCircle userCircle = userCircleRepo.selectByPrimaryKey(id);
		return beanMapper.map(userCircle, UserCircleModel.class);
	}

	@Transactional(readOnly = true)
	@Override
	public long selectCount(UserCircleModel userCircleModel) {
		return userCircleRepo.selectCount(beanMapper.map(userCircleModel, UserCircle.class));
	}

	@Transactional(readOnly = true)
	@Override
	public List<UserCircleModel> selectPage(UserCircleModel userCircleModel, Pageable pageable) {
		UserCircle userCircle = beanMapper.map(userCircleModel, UserCircle.class);
		return beanMapper.mapAsList(userCircleRepo.selectPage(userCircle,pageable),UserCircleModel.class);
	}

	@Transactional
	@Override
	public int updateByPrimaryKey(UserCircleModel userCircleModel) {
		return userCircleRepo.updateByPrimaryKey(beanMapper.map(userCircleModel, UserCircle.class));
	}
	
	@Transactional
	@Override
	public int updateByPrimaryKeySelective(UserCircleModel userCircleModel) {
		return userCircleRepo.updateByPrimaryKeySelective(beanMapper.map(userCircleModel, UserCircle.class));
	}

}
