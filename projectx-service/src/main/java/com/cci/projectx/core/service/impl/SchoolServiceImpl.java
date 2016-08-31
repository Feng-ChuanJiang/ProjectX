package com.cci.projectx.core.service.impl;

import com.cci.projectx.core.ElasticSearchHelp;
import com.cci.projectx.core.entity.School;
import com.cci.projectx.core.model.SchoolModel;
import com.cci.projectx.core.repository.SchoolRepository;
import com.cci.projectx.core.service.SchoolService;
import com.wlw.pylon.core.beans.mapping.BeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SchoolServiceImpl implements SchoolService {

	@Autowired
	private BeanMapper beanMapper;

	@Autowired
	private SchoolRepository schoolRepo;

	@Autowired
	private ElasticSearchHelp elasticSearchHelp;
	@Transactional
	@Override
	public int create(SchoolModel schoolModel) {
		School school=beanMapper.map(schoolModel,School.class);
		int id=schoolRepo.insert(school);
		if(school.getId()!=null){
			elasticSearchHelp.mergeES(school,school.getId().toString());
		}
		return id;
	}

	@Transactional
	@Override
	public int createSelective(SchoolModel schoolModel) {
		School school=beanMapper.map(schoolModel,School.class);
		int id=schoolRepo.insertSelective(school);
		if(school.getId()!=null){
			elasticSearchHelp.mergeES(school,school.getId().toString());
		}
		return id;
	}

	@Transactional
	@Override
	public int deleteByPrimaryKey(Long id) {

		int sid=schoolRepo.deleteByPrimaryKey(id);
		if(sid>0){
			elasticSearchHelp.deleteES(School.class,sid);

		}
		return sid;
	}

	@Transactional(readOnly = true)
	@Override
	public SchoolModel findByPrimaryKey(Long id) {
		School school = schoolRepo.selectByPrimaryKey(id);
		return beanMapper.map(school, SchoolModel.class);
	}

	@Transactional(readOnly = true)
	@Override
	public long selectCount(SchoolModel schoolModel) {
		return schoolRepo.selectCount(beanMapper.map(schoolModel, School.class));
	}

	@Transactional(readOnly = true)
	@Override
	public List<SchoolModel> selectPage(SchoolModel schoolModel, Pageable pageable) {
		School school = beanMapper.map(schoolModel, School.class);
		return beanMapper.mapAsList(schoolRepo.selectPage(school,pageable),SchoolModel.class);
	}

	@Transactional
	@Override
	public int updateByPrimaryKey(SchoolModel schoolModel) {
		School school=beanMapper.map(schoolModel,School.class);
		int id=schoolRepo.updateByPrimaryKey(school);
		if(school.getId()!=null){
			elasticSearchHelp.mergeES(school,school.getId().toString());
		}
		return id;
	}
	
	@Transactional
	@Override
	public int updateByPrimaryKeySelective(SchoolModel schoolModel) {
		School school=beanMapper.map(schoolModel,School.class);
		int id=schoolRepo.updateByPrimaryKeySelective(school);
		if(school.getId()!=null){
			elasticSearchHelp.mergeES(school,school.getId().toString());
		}
		return id;
	}

	/**
	 * es模糊查询
	 * @param schoolModel
	 * @return
	 */
	@Transactional
	@Override
	public List<SchoolModel> getSchool(SchoolModel schoolModel){
		School school=beanMapper.map(schoolModel,School.class);
		List<SchoolModel> schoolModelList=elasticSearchHelp.findESForList(school);

		return  schoolModelList;
	}

}
