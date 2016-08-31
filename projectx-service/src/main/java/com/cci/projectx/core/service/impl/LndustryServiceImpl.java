package com.cci.projectx.core.service.impl;

import com.cci.projectx.core.ElasticSearchHelp;
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

	@Autowired
	private ElasticSearchHelp elasticSearchHelp;
	@Transactional
	@Override
	public int create(LndustryModel lndustryModel) {
		Lndustry lndustry=beanMapper.map(lndustryModel,Lndustry.class);
		int id=lndustryRepo.insert(lndustry);
		if(lndustry.getId()!=null){
			elasticSearchHelp.mergeES(lndustry,lndustry.getId().toString());

		}
		return id;
	}

	@Transactional
	@Override
	public int createSelective(LndustryModel lndustryModel) {

		Lndustry lndustry=beanMapper.map(lndustryModel,Lndustry.class);
		int id=lndustryRepo.insertSelective(lndustry);
		if(lndustry.getId()!=null){
			elasticSearchHelp.mergeES(lndustry,lndustry.getId().toString());

		}
		return id;
	}

	@Transactional
	@Override
	public int deleteByPrimaryKey(Long id) {
		int pid=lndustryRepo.deleteByPrimaryKey(id);
		if(pid>0){
			elasticSearchHelp.deleteES(Lndustry.class,pid);
		}
		return pid;
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
		Lndustry lndustry=beanMapper.map(lndustryModel,Lndustry.class);
		int id=lndustryRepo.updateByPrimaryKey(lndustry);
		if(lndustry.getId()!=null){
			elasticSearchHelp.mergeES(lndustry,lndustry.getId().toString());

		}
		return id;
	}
	
	@Transactional
	@Override
	public int updateByPrimaryKeySelective(LndustryModel lndustryModel) {
		Lndustry lndustry=beanMapper.map(lndustryModel,Lndustry.class);
		int id=lndustryRepo.updateByPrimaryKeySelective(lndustry);
		if(lndustry.getId()!=null){
			elasticSearchHelp.mergeES(lndustry,lndustry.getId().toString());

		}
		return id;
	}

	@Transactional
	@Override
	public List<LndustryModel> getLndustry(LndustryModel lndustryModel){
		Lndustry lndustry=beanMapper.map(lndustryModel,Lndustry.class);
		List<LndustryModel> lndustryModelList=elasticSearchHelp.findESForList(lndustry);
		return  lndustryModelList;
	}

}
