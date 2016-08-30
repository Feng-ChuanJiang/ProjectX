package com.cci.projectx.core.service.impl;

import com.cci.projectx.core.entity.InteractGroup;
import com.cci.projectx.core.model.InteractGroupModel;
import com.cci.projectx.core.repository.InteractGroupRepository;
import com.cci.projectx.core.service.InteractGroupService;
import com.wlw.pylon.core.beans.mapping.BeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class InteractGroupServiceImpl implements InteractGroupService {

	@Autowired
	private BeanMapper beanMapper;

	@Autowired
	private InteractGroupRepository interactGroupRepo;

	@Transactional
	@Override
	public int create(InteractGroupModel interactGroupModel) {
		return interactGroupRepo.insert(beanMapper.map(interactGroupModel, InteractGroup.class));
	}

	@Transactional
	@Override
	public int createSelective(InteractGroupModel interactGroupModel) {
		return interactGroupRepo.insertSelective(beanMapper.map(interactGroupModel, InteractGroup.class));
	}

	@Transactional
	@Override
	public int deleteByPrimaryKey(Long id) {
		return interactGroupRepo.deleteByPrimaryKey(id);
	}

	@Transactional(readOnly = true)
	@Override
	public InteractGroupModel findByPrimaryKey(Long id) {
		InteractGroup interactGroup = interactGroupRepo.selectByPrimaryKey(id);
		return beanMapper.map(interactGroup, InteractGroupModel.class);
	}

	@Transactional(readOnly = true)
	@Override
	public long selectCount(InteractGroupModel interactGroupModel) {
		return interactGroupRepo.selectCount(beanMapper.map(interactGroupModel, InteractGroup.class));
	}

	@Transactional(readOnly = true)
	@Override
	public List<InteractGroupModel> selectPage(InteractGroupModel interactGroupModel, Pageable pageable) {
		InteractGroup interactGroup = beanMapper.map(interactGroupModel, InteractGroup.class);
		return beanMapper.mapAsList(interactGroupRepo.selectPage(interactGroup,pageable),InteractGroupModel.class);
	}

	@Transactional
	@Override
	public int updateByPrimaryKey(InteractGroupModel interactGroupModel) {
		return interactGroupRepo.updateByPrimaryKey(beanMapper.map(interactGroupModel, InteractGroup.class));
	}
	
	@Transactional
	@Override
	public int updateByPrimaryKeySelective(InteractGroupModel interactGroupModel) {
		return interactGroupRepo.updateByPrimaryKeySelective(beanMapper.map(interactGroupModel, InteractGroup.class));
	}

}
