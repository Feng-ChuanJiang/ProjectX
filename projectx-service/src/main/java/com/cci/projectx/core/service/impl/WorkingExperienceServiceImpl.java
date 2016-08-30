package com.cci.projectx.core.service.impl;

import com.cci.projectx.core.ElasticSearchHelp;
import com.cci.projectx.core.entity.WorkingExperience;
import com.cci.projectx.core.model.WorkingExperienceModel;
import com.cci.projectx.core.repository.WorkingExperienceRepository;
import com.cci.projectx.core.service.WorkingExperienceService;
import com.wlw.pylon.core.beans.mapping.BeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class WorkingExperienceServiceImpl implements WorkingExperienceService {

	@Autowired
	private BeanMapper beanMapper;

	@Autowired
	private WorkingExperienceRepository workingExperienceRepo;

	@Autowired
	private ElasticSearchHelp elasticSearchBase;

	@Transactional
	@Override
	public int create(WorkingExperienceModel workingExperienceModel) {
		WorkingExperience workingExperience = beanMapper.map(workingExperienceModel, WorkingExperience.class);
		int nid = workingExperienceRepo.insert(workingExperience);
		if (workingExperience.getId()!=null) {
			elasticSearchBase.mergeES(workingExperience, String.valueOf(workingExperience.getId()));
		}
		return nid;
	}

	@Transactional
	@Override
	public int createSelective(WorkingExperienceModel workingExperienceModel) {
		WorkingExperience workingExperience = beanMapper.map(workingExperienceModel, WorkingExperience.class);
		int id = workingExperienceRepo.insertSelective(workingExperience);
		if (workingExperience.getId()!=null) {
			elasticSearchBase.mergeES(workingExperience, String.valueOf(workingExperience.getId()));
		}
		return id;
	}

	@Transactional
	@Override
	public int deleteByPrimaryKey(Long id) {
		int wid = workingExperienceRepo.deleteByPrimaryKey(id);
		if (wid > 0) {
			elasticSearchBase.deleteES(WorkingExperience.class, id);
		}
		return wid;
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
		WorkingExperience workingExperience = beanMapper.map(workingExperienceModel, WorkingExperience.class);
		int id = workingExperienceRepo.updateByPrimaryKey(workingExperience);
		if (workingExperience.getId()!=null) {
			elasticSearchBase.mergeES(workingExperience, workingExperience.getId().toString());
		}
		return id;
	}
	
	@Transactional
	@Override
	public int updateByPrimaryKeySelective(WorkingExperienceModel workingExperienceModel) {
		WorkingExperience workingExperience = beanMapper.map(workingExperienceModel, WorkingExperience.class);
		int id = workingExperienceRepo.updateByPrimaryKeySelective(workingExperience);
		if (workingExperience.getId()!=null) {
			elasticSearchBase.mergeES(workingExperience, workingExperience.getId().toString());
		}
		return id;
	}

	/**
	 * 通过对象模糊查询教育背景
	 * @param workingExperience
	 * @return
	 */
	@Transactional
	@Override
	public List<WorkingExperienceModel> getWorkingByWorkInfo(WorkingExperienceModel workingExperience){
		List<WorkingExperienceModel> workingExperiences = elasticSearchBase.findESForList(workingExperience);
		return workingExperiences;
	}

}
