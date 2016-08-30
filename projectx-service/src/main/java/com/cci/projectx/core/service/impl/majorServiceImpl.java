package com.cci.projectx.core.service.impl;

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

	@Transactional
	@Override
	public int create(majorModel majorModel) {
		return majorRepo.insert(beanMapper.map(majorModel, major.class));
	}

	@Transactional
	@Override
	public int createSelective(majorModel majorModel) {
		return majorRepo.insertSelective(beanMapper.map(majorModel, major.class));
	}

	@Transactional
	@Override
	public int deleteByPrimaryKey(Long id) {
		return majorRepo.deleteByPrimaryKey(id);
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
		return majorRepo.updateByPrimaryKey(beanMapper.map(majorModel, major.class));
	}
	
	@Transactional
	@Override
	public int updateByPrimaryKeySelective(majorModel majorModel) {
		return majorRepo.updateByPrimaryKeySelective(beanMapper.map(majorModel, major.class));
	}

}
