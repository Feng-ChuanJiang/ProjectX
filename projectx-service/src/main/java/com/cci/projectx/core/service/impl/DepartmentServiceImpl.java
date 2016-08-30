package com.cci.projectx.core.service.impl;

import com.cci.projectx.core.entity.Department;
import com.cci.projectx.core.model.DepartmentModel;
import com.cci.projectx.core.repository.DepartmentRepository;
import com.cci.projectx.core.service.DepartmentService;
import com.wlw.pylon.core.beans.mapping.BeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DepartmentServiceImpl implements DepartmentService {

	@Autowired
	private BeanMapper beanMapper;

	@Autowired
	private DepartmentRepository departmentRepo;

	@Transactional
	@Override
	public int create(DepartmentModel departmentModel) {
		return departmentRepo.insert(beanMapper.map(departmentModel, Department.class));
	}

	@Transactional
	@Override
	public int createSelective(DepartmentModel departmentModel) {
		return departmentRepo.insertSelective(beanMapper.map(departmentModel, Department.class));
	}

	@Transactional
	@Override
	public int deleteByPrimaryKey(Long id) {
		return departmentRepo.deleteByPrimaryKey(id);
	}

	@Transactional(readOnly = true)
	@Override
	public DepartmentModel findByPrimaryKey(Long id) {
		Department department = departmentRepo.selectByPrimaryKey(id);
		return beanMapper.map(department, DepartmentModel.class);
	}

	@Transactional(readOnly = true)
	@Override
	public long selectCount(DepartmentModel departmentModel) {
		return departmentRepo.selectCount(beanMapper.map(departmentModel, Department.class));
	}

	@Transactional(readOnly = true)
	@Override
	public List<DepartmentModel> selectPage(DepartmentModel departmentModel, Pageable pageable) {
		Department department = beanMapper.map(departmentModel, Department.class);
		return beanMapper.mapAsList(departmentRepo.selectPage(department,pageable),DepartmentModel.class);
	}

	@Transactional
	@Override
	public int updateByPrimaryKey(DepartmentModel departmentModel) {
		return departmentRepo.updateByPrimaryKey(beanMapper.map(departmentModel, Department.class));
	}
	
	@Transactional
	@Override
	public int updateByPrimaryKeySelective(DepartmentModel departmentModel) {
		return departmentRepo.updateByPrimaryKeySelective(beanMapper.map(departmentModel, Department.class));
	}

}
