package com.cci.projectx.core.service.impl;

import com.cci.projectx.core.entity.FriendSeting;
import com.cci.projectx.core.model.FriendSetingModel;
import com.cci.projectx.core.repository.FriendSetingRepository;
import com.cci.projectx.core.service.FriendSetingService;
import com.wlw.pylon.core.beans.mapping.BeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class FriendSetingServiceImpl implements FriendSetingService {

	@Autowired
	private BeanMapper beanMapper;

	@Autowired
	private FriendSetingRepository friendSetingRepo;

	@Transactional
	@Override
	public int create(FriendSetingModel friendSetingModel) {
		return friendSetingRepo.insert(beanMapper.map(friendSetingModel, FriendSeting.class));
	}

	@Transactional
	@Override
	public int createSelective(FriendSetingModel friendSetingModel) {
		return friendSetingRepo.insertSelective(beanMapper.map(friendSetingModel, FriendSeting.class));
	}

	@Transactional
	@Override
	public int deleteByPrimaryKey(Long id) {
		return friendSetingRepo.deleteByPrimaryKey(id);
	}

	@Transactional(readOnly = true)
	@Override
	public FriendSetingModel findByPrimaryKey(Long id) {
		FriendSeting friendSeting = friendSetingRepo.selectByPrimaryKey(id);
		return beanMapper.map(friendSeting, FriendSetingModel.class);
	}

	@Transactional(readOnly = true)
	@Override
	public long selectCount(FriendSetingModel friendSetingModel) {
		return friendSetingRepo.selectCount(beanMapper.map(friendSetingModel, FriendSeting.class));
	}

	@Transactional(readOnly = true)
	@Override
	public List<FriendSetingModel> selectPage(FriendSetingModel friendSetingModel, Pageable pageable) {
		FriendSeting friendSeting = beanMapper.map(friendSetingModel, FriendSeting.class);
		return beanMapper.mapAsList(friendSetingRepo.selectPage(friendSeting,pageable),FriendSetingModel.class);
	}

	@Transactional
	@Override
	public int updateByPrimaryKey(FriendSetingModel friendSetingModel) {
		return friendSetingRepo.updateByPrimaryKey(beanMapper.map(friendSetingModel, FriendSeting.class));
	}
	
	@Transactional
	@Override
	public int updateByPrimaryKeySelective(FriendSetingModel friendSetingModel) {
		return friendSetingRepo.updateByPrimaryKeySelective(beanMapper.map(friendSetingModel, FriendSeting.class));
	}

}
