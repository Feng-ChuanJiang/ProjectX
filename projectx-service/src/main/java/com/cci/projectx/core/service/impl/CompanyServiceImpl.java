package com.cci.projectx.core.service.impl;

import com.cci.projectx.core.ElasticSearchHelp;
import com.cci.projectx.core.entity.Company;
import com.cci.projectx.core.model.CommentModel;
import com.cci.projectx.core.model.CompanyModel;
import com.cci.projectx.core.repository.CompanyRepository;
import com.cci.projectx.core.service.CompanyService;
import com.wlw.pylon.core.beans.mapping.BeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CompanyServiceImpl implements CompanyService {

	@Autowired
	private BeanMapper beanMapper;

	@Autowired
	private CompanyRepository companyRepo;

	@Autowired
	private ElasticSearchHelp elasticSearchHelp;

	/**
	 * 插入的的时候保存到es中去
	 * @param companyModel
	 * @return
	 */
	@Transactional
	@Override
	public int create(CompanyModel companyModel) {
		Company company=beanMapper.map(companyModel,Company.class);
		int id=companyRepo.insert(company);
		if(company.getId()!=null){
		elasticSearchHelp.mergeES(company,company.getId().toString());
		}
		return id;
	}

	/**
	 * 插入的时候保存到es中去
	 * @param companyModel
	 * @return
	 */
	@Transactional
	@Override
	public int createSelective(CompanyModel companyModel) {
		Company company=beanMapper.map(companyModel,Company.class);
		int id=companyRepo.insertSelective(company);
		if(company.getId()!=null){
			elasticSearchHelp.mergeES(company,company.getId().toString());
		}
		return id;
	}

	/**
	 * 同时清理es数据
	 * @param id
	 * @return
	 */
	@Transactional
	@Override
	public int deleteByPrimaryKey(Long id) {
		int pid=companyRepo.deleteByPrimaryKey(id);
		if(pid>0){
			elasticSearchHelp.deleteES(Company.class,id);
		}
		return pid;
	}

	@Transactional(readOnly = true)
	@Override
	public CompanyModel findByPrimaryKey(Long id) {
		Company company = companyRepo.selectByPrimaryKey(id);
		return beanMapper.map(company, CompanyModel.class);
	}

	@Transactional(readOnly = true)
	@Override
	public long selectCount(CompanyModel companyModel) {
		return companyRepo.selectCount(beanMapper.map(companyModel, Company.class));
	}

	@Transactional(readOnly = true)
	@Override
	public List<CompanyModel> selectPage(CompanyModel companyModel, Pageable pageable) {
		Company company = beanMapper.map(companyModel, Company.class);
		return beanMapper.mapAsList(companyRepo.selectPage(company,pageable),CompanyModel.class);
	}

	@Transactional
	@Override
	public int updateByPrimaryKey(CompanyModel companyModel) {

		Company company=beanMapper.map(companyModel,Company.class);
		int id=companyRepo.updateByPrimaryKey(company);
		if(company.getId()!=null){
			elasticSearchHelp.mergeES(company,company.getId().toString());
		}
		return id;
	}
	
	@Transactional
	@Override
	public int updateByPrimaryKeySelective(CompanyModel companyModel) {
		Company company=beanMapper.map(companyModel,Company.class);
		int id=companyRepo.updateByPrimaryKeySelective(company);
		if(company.getId()!=null){
			elasticSearchHelp.mergeES(company,company.getId().toString());
		}
		return id;
	}
	@Transactional
	@Override
	public List<CompanyModel> getCompany(CompanyModel companyModel){
    		Company company=beanMapper.map(companyModel,Company.class);
			List<CompanyModel> companyModelList=elasticSearchHelp.findESForList(company);

		return  companyModelList;
	}
}
