package com.cci.projectx.core.service.impl;

import com.cci.projectx.core.ElasticSearchHelp;
import com.cci.projectx.core.entity.Education;
import com.cci.projectx.core.model.EducationModel;
import com.cci.projectx.core.model.WorkingExperienceModel;
import com.cci.projectx.core.repository.EducationRepository;
import com.cci.projectx.core.service.EducationService;
import com.wlw.pylon.core.beans.mapping.BeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EducationServiceImpl implements EducationService {

	@Autowired
	private BeanMapper beanMapper;

	@Autowired
	private EducationRepository educationRepo;

	@Autowired
	private ElasticSearchHelp elasticSearchBase;

	@Transactional
	@Override
	public int create(EducationModel educationModel) {
		Education education = beanMapper.map(educationModel, Education.class);
		int nid=educationRepo.insert(education);
		if (education.getId()!=null) {
			elasticSearchBase.mergeES(education, String.valueOf(education.getId()));
		}
		return nid;
	}

	@Transactional
	@Override
	public int createSelective(EducationModel educationModel) {
		Education education = beanMapper.map(educationModel, Education.class);
		int nid = educationRepo.insertSelective(education);
		if (education.getId()!=null) {
			elasticSearchBase.mergeES(education, String.valueOf(education.getId()));
		}
		return nid;
	}

	@Transactional
	@Override
	public int deleteByPrimaryKey(Long id) {
		int eid = educationRepo.deleteByPrimaryKey(id);
		if (eid > 0) {
			elasticSearchBase.deleteES(Education.class, id);
		}
		return eid;
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
		Education education = beanMapper.map(educationModel, Education.class);
		int id = educationRepo.updateByPrimaryKey(education);
		if (id > 0) {
			elasticSearchBase.mergeES(education,education.getId().toString());
		}
		return id;
	}
	
	@Transactional
	@Override
	public int updateByPrimaryKeySelective(EducationModel educationModel) {
		Education education = beanMapper.map(educationModel, Education.class);
		int id = educationRepo.updateByPrimaryKeySelective(education);
		if (id > 0) {
			elasticSearchBase.mergeES(education,education.getId().toString());
		}
		return id;
	}

	/**
	 * 通过对象模糊查询教育背景
	 * @param education
	 * @return
	 */
	@Transactional
	@Override
	public List<EducationModel> getEducationByEducationInfo(EducationModel education){
		List<EducationModel> educations = elasticSearchBase.findESForList(education);
		return educations;
	}

}
