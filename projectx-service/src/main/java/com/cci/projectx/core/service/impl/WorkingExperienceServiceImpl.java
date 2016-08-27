package com.cci.projectx.core.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Pageable;

import com.cci.projectx.core.entity.WorkingExperience;
import com.cci.projectx.core.repository.WorkingExperienceRepository;
import com.cci.projectx.core.model.WorkingExperienceModel;
import com.cci.projectx.core.service.WorkingExperienceService;
import com.wlw.pylon.core.beans.mapping.BeanMapper;

import java.util.List;

@Service
public class WorkingExperienceServiceImpl implements WorkingExperienceService {

	@Autowired
	private BeanMapper beanMapper;

	@Autowired
	private WorkingExperienceRepository workingExperienceRepo;

	@Transactional
	@Override
	public int create(WorkingExperienceModel workingExperienceModel) {
		return workingExperienceRepo.insert(beanMapper.map(workingExperienceModel, WorkingExperience.class));
	}

	@Transactional
	@Override
	public int createSelective(WorkingExperienceModel workingExperienceModel) {
		return workingExperienceRepo.insertSelective(beanMapper.map(workingExperienceModel, WorkingExperience.class));
	}

	@Transactional
	@Override
	public int deleteByPrimaryKey(Long id) {
		return workingExperienceRepo.deleteByPrimaryKey(id);
	}

	@Transactional(readOnly = true)
	@Override
	public WorkingExperienceModel findByPrimaryKey(Long id) {
		WorkingExperience workingExperience = workingExperienceRepo.selectByPrimaryKey(id);
		return beanMapper.map(workingExperience, WorkingExperienceModel.class);
	}

	@Transactional(readOnly = true)
	@Override
	public long selectCount(WorkingExperienceModel workingExperienceModel) {
		return workingExperienceRepo.selectCount(beanMapper.map(workingExperienceModel, WorkingExperience.class));
	}

	@Transactional(readOnly = true)
	@Override
	public List<WorkingExperienceModel> selectPage(WorkingExperienceModel workingExperienceModel,Pageable pageable) {
		WorkingExperience workingExperience = beanMapper.map(workingExperienceModel, WorkingExperience.class);
		return beanMapper.mapAsList(workingExperienceRepo.selectPage(workingExperience,pageable),WorkingExperienceModel.class);
	}

	@Transactional
	@Override
	public int updateByPrimaryKey(WorkingExperienceModel workingExperienceModel) {
		return workingExperienceRepo.updateByPrimaryKey(beanMapper.map(workingExperienceModel, WorkingExperience.class));
	}
	
	@Transactional
	@Override
	public int updateByPrimaryKeySelective(WorkingExperienceModel workingExperienceModel) {
		return workingExperienceRepo.updateByPrimaryKeySelective(beanMapper.map(workingExperienceModel, WorkingExperience.class));
	}

}
