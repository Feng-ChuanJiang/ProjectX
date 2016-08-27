package com.cci.projectx.core.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Pageable;

import com.cci.projectx.core.entity.Education;
import com.cci.projectx.core.repository.EducationRepository;
import com.cci.projectx.core.model.EducationModel;
import com.cci.projectx.core.service.EducationService;
import com.wlw.pylon.core.beans.mapping.BeanMapper;

import java.util.List;

@Service
public class EducationServiceImpl implements EducationService {

	@Autowired
	private BeanMapper beanMapper;

	@Autowired
	private EducationRepository educationRepo;

	@Transactional
	@Override
	public int create(EducationModel educationModel) {
		return educationRepo.insert(beanMapper.map(educationModel, Education.class));
	}

	@Transactional
	@Override
	public int createSelective(EducationModel educationModel) {
		return educationRepo.insertSelective(beanMapper.map(educationModel, Education.class));
	}

	@Transactional
	@Override
	public int deleteByPrimaryKey(Long id) {
		return educationRepo.deleteByPrimaryKey(id);
	}

	@Transactional(readOnly = true)
	@Override
	public EducationModel findByPrimaryKey(Long id) {
		Education education = educationRepo.selectByPrimaryKey(id);
		return beanMapper.map(education, EducationModel.class);
	}

	@Transactional(readOnly = true)
	@Override
	public long selectCount(EducationModel educationModel) {
		return educationRepo.selectCount(beanMapper.map(educationModel, Education.class));
	}

	@Transactional(readOnly = true)
	@Override
	public List<EducationModel> selectPage(EducationModel educationModel,Pageable pageable) {
		Education education = beanMapper.map(educationModel, Education.class);
		return beanMapper.mapAsList(educationRepo.selectPage(education,pageable),EducationModel.class);
	}

	@Transactional
	@Override
	public int updateByPrimaryKey(EducationModel educationModel) {
		return educationRepo.updateByPrimaryKey(beanMapper.map(educationModel, Education.class));
	}
	
	@Transactional
	@Override
	public int updateByPrimaryKeySelective(EducationModel educationModel) {
		return educationRepo.updateByPrimaryKeySelective(beanMapper.map(educationModel, Education.class));
	}

}
