package com.cci.projectx.core.service.impl;

import com.cci.projectx.core.entity.Interact;
import com.cci.projectx.core.model.InteractModel;
import com.cci.projectx.core.repository.InteractRepository;
import com.cci.projectx.core.service.InteractService;
import com.wlw.pylon.core.beans.mapping.BeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class InteractServiceImpl implements InteractService {

	@Autowired
	private BeanMapper beanMapper;

	@Autowired
	private InteractRepository interactRepo;

	@Transactional
	@Override
	public int create(InteractModel interactModel) {
		return interactRepo.insert(beanMapper.map(interactModel, Interact.class));
	}

	@Transactional
	@Override
	public int createSelective(InteractModel interactModel) {
		return interactRepo.insertSelective(beanMapper.map(interactModel, Interact.class));
	}

	@Transactional
	@Override
	public int deleteByPrimaryKey(Long id) {
		return interactRepo.deleteByPrimaryKey(id);
	}

	@Transactional(readOnly = true)
	@Override
	public InteractModel findByPrimaryKey(Long id) {
		Interact interact = interactRepo.selectByPrimaryKey(id);
		return beanMapper.map(interact, InteractModel.class);
	}

	@Transactional(readOnly = true)
	@Override
	public long selectCount(InteractModel interactModel) {
		return interactRepo.selectCount(beanMapper.map(interactModel, Interact.class));
	}

	@Transactional(readOnly = true)
	@Override
	public List<InteractModel> selectPage(InteractModel interactModel,Pageable pageable) {
		Interact interact = beanMapper.map(interactModel, Interact.class);
		return beanMapper.mapAsList(interactRepo.selectPage(interact,pageable),InteractModel.class);
	}

	@Transactional
	@Override
	public int updateByPrimaryKey(InteractModel interactModel) {
		return interactRepo.updateByPrimaryKey(beanMapper.map(interactModel, Interact.class));
	}
	
	@Transactional
	@Override
	public int updateByPrimaryKeySelective(InteractModel interactModel) {
		return interactRepo.updateByPrimaryKeySelective(beanMapper.map(interactModel, Interact.class));
	}

}
