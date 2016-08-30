package com.cci.projectx.core.service.impl;

import com.cci.projectx.core.entity.Lndustry;
import com.cci.projectx.core.model.LndustryModel;
import com.cci.projectx.core.repository.LndustryRepository;
import com.cci.projectx.core.service.LndustryService;
import com.wlw.pylon.core.beans.mapping.BeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class LndustryServiceImpl implements LndustryService {

	@Autowired
	private BeanMapper beanMapper;

	@Autowired
	private LndustryRepository lndustryRepo;

	@Transactional
	@Override
	public int create(LndustryModel lndustryModel) {
		return lndustryRepo.insert(beanMapper.map(lndustryModel, Lndustry.class));
	}

	@Transactional
	@Override
	public int createSelective(LndustryModel lndustryModel) {
		return lndustryRepo.insertSelective(beanMapper.map(lndustryModel, Lndustry.class));
	}

	@Transactional
	@Override
	public int deleteByPrimaryKey(Long id) {
		return lndustryRepo.deleteByPrimaryKey(id);
	}

	@Transactional(readOnly = true)
	@Override
	public LndustryModel findByPrimaryKey(Long id) {
		Lndustry lndustry = lndustryRepo.selectByPrimaryKey(id);
		return beanMapper.map(lndustry, LndustryModel.class);
	}

	@Transactional(readOnly = true)
	@Override
	public long selectCount(LndustryModel lndustryModel) {
		return lndustryRepo.selectCount(beanMapper.map(lndustryModel, Lndustry.class));
	}

	@Transactional(readOnly = true)
	@Override
	public List<LndustryModel> selectPage(LndustryModel lndustryModel, Pageable pageable) {
		Lndustry lndustry = beanMapper.map(lndustryModel, Lndustry.class);
		return beanMapper.mapAsList(lndustryRepo.selectPage(lndustry,pageable),LndustryModel.class);
	}

	@Transactional
	@Override
	public int updateByPrimaryKey(LndustryModel lndustryModel) {
		return lndustryRepo.updateByPrimaryKey(beanMapper.map(lndustryModel, Lndustry.class));
	}
	
	@Transactional
	@Override
	public int updateByPrimaryKeySelective(LndustryModel lndustryModel) {
		return lndustryRepo.updateByPrimaryKeySelective(beanMapper.map(lndustryModel, Lndustry.class));
	}

}
