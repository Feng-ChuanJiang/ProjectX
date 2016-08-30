package com.cci.projectx.core.service.impl;

import com.cci.projectx.core.entity.Company;
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

	@Transactional
	@Override
	public int create(CompanyModel companyModel) {
		return companyRepo.insert(beanMapper.map(companyModel, Company.class));
	}

	@Transactional
	@Override
	public int createSelective(CompanyModel companyModel) {
		return companyRepo.insertSelective(beanMapper.map(companyModel, Company.class));
	}

	@Transactional
	@Override
	public int deleteByPrimaryKey(Long id) {
		return companyRepo.deleteByPrimaryKey(id);
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
		return companyRepo.updateByPrimaryKey(beanMapper.map(companyModel, Company.class));
	}
	
	@Transactional
	@Override
	public int updateByPrimaryKeySelective(CompanyModel companyModel) {
		return companyRepo.updateByPrimaryKeySelective(beanMapper.map(companyModel, Company.class));
	}

}
