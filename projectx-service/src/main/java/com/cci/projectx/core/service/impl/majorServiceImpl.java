package com.cci.projectx.core.service.impl;

import com.cci.projectx.core.ElasticSearchHelp;
import com.cci.projectx.core.entity.major;
import com.cci.projectx.core.model.majorModel;
import com.cci.projectx.core.repository.majorRepository;
import com.cci.projectx.core.service.majorService;
import com.wlw.pylon.core.beans.mapping.BeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class majorServiceImpl implements majorService {

	@Autowired
	private BeanMapper beanMapper;

	@Autowired
	private majorRepository majorRepo;

	@Autowired
	private ElasticSearchHelp elasticSearchHelp;
	@Transactional
	@Override
	public int create(majorModel majorModel) {
		major m=beanMapper.map(majorModel,major.class);
		int id=majorRepo.insert(m);
		if(m.getId()!=null){
			elasticSearchHelp.mergeES(m,m.getId().toString());
		}
		return id;
	}

	@Transactional
	@Override
	public int createSelective(majorModel majorModel) {
		major m=beanMapper.map(majorModel,major.class);
		int id=majorRepo.insertSelective(m);
		if(m.getId()!=null){
			elasticSearchHelp.mergeES(m,m.getId().toString());
		}
		return id;
	}

	@Transactional
	@Override
	public int deleteByPrimaryKey(Long id) {
		int mid=majorRepo.deleteByPrimaryKey(id);
		if(mid>0){
			elasticSearchHelp.deleteES(major.class,id);
		}

		return mid;
	}

	@Transactional(readOnly = true)
	@Override
	public majorModel findByPrimaryKey(Long id) {
		major major = majorRepo.selectByPrimaryKey(id);
		return beanMapper.map(major, majorModel.class);
	}

	@Transactional(readOnly = true)
	@Override
	public long selectCount(majorModel majorModel) {
		return majorRepo.selectCount(beanMapper.map(majorModel, major.class));
	}

	@Transactional(readOnly = true)
	@Override
	public List<majorModel> selectPage(majorModel majorModel, Pageable pageable) {
		major major = beanMapper.map(majorModel, com.cci.projectx.core.entity.major.class);
		return beanMapper.mapAsList(majorRepo.selectPage(major,pageable),majorModel.class);
	}

	@Transactional
	@Override
	public int updateByPrimaryKey(majorModel majorModel) {
		major m=beanMapper.map(majorModel,major.class);
		int id=majorRepo.updateByPrimaryKey(m);
		if(m.getId()!=null){
			elasticSearchHelp.mergeES(m,m.getId().toString());
		}
		return id;
	}
	
	@Transactional
	@Override
	public int updateByPrimaryKeySelective(majorModel majorModel) {
		major m=beanMapper.map(majorModel,major.class);
		int id=majorRepo.updateByPrimaryKeySelective(m);
		if(m.getId()!=null){
			elasticSearchHelp.mergeES(m,m.getId().toString());
		}
		return id;
	}

	@Override
	public  List<majorModel> getMajor(majorModel model){
		major m=beanMapper.map(model,major.class);
		List<majorModel> modelList=elasticSearchHelp.findESForList(m);

		return modelList;
	}

}
