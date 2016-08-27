package com.cci.projectx.core.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Pageable;

import com.cci.projectx.core.entity.S;
import com.cci.projectx.core.repository.SRepository;
import com.cci.projectx.core.model.SModel;
import com.cci.projectx.core.service.SService;
import com.wlw.pylon.core.beans.mapping.BeanMapper;

import java.util.List;

@Service
public class SServiceImpl implements SService {

	@Autowired
	private BeanMapper beanMapper;

	@Autowired
	private SRepository sRepo;

	@Transactional
	@Override
	public int create(SModel sModel) {
		return sRepo.insert(beanMapper.map(sModel, S.class));
	}

	@Transactional
	@Override
	public int createSelective(SModel sModel) {
		return sRepo.insertSelective(beanMapper.map(sModel, S.class));
	}

	@Transactional
	@Override
	public int deleteByPrimaryKey(Long id) {
		return sRepo.deleteByPrimaryKey(id);
	}

	@Transactional(readOnly = true)
	@Override
	public SModel findByPrimaryKey(Long id) {
		S s = sRepo.selectByPrimaryKey(id);
		return beanMapper.map(s, SModel.class);
	}

	@Transactional(readOnly = true)
	@Override
	public long selectCount(SModel sModel) {
		return sRepo.selectCount(beanMapper.map(sModel, S.class));
	}

	@Transactional(readOnly = true)
	@Override
	public List<SModel> selectPage(SModel sModel,Pageable pageable) {
		S s = beanMapper.map(sModel, S.class);
		return beanMapper.mapAsList(sRepo.selectPage(s,pageable),SModel.class);
	}

	@Transactional
	@Override
	public int updateByPrimaryKey(SModel sModel) {
		return sRepo.updateByPrimaryKey(beanMapper.map(sModel, S.class));
	}
	
	@Transactional
	@Override
	public int updateByPrimaryKeySelective(SModel sModel) {
		return sRepo.updateByPrimaryKeySelective(beanMapper.map(sModel, S.class));
	}

}
