package com.cci.projectx.core.service.impl;

import com.cci.projectx.core.ElasticSearchHelp;
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

	@Autowired
	private ElasticSearchHelp elasticSearchHelp;
	@Transactional
	@Override
	public int create(DepartmentModel departmentModel) {
		Department department=beanMapper.map(departmentModel,Department.class);
		int id=departmentRepo.insert(department);
		if(department.getId()!=null){
			elasticSearchHelp.mergeES(department,department.getId().toString());
		}
		return id;
	}

	@Transactional
	@Override
	public int createSelective(DepartmentModel departmentModel) {
		Department department=beanMapper.map(departmentModel,Department.class);
		int id =departmentRepo.insertSelective(department);
		if(department.getId()!=null){
			elasticSearchHelp.mergeES(department,department.getId().toString());
		}
		return id;
	}

	@Transactional
	@Override
	public int deleteByPrimaryKey(Long id) {
		int pid=departmentRepo.deleteByPrimaryKey(id);
		if(pid>0){
			elasticSearchHelp.deleteES(Department.class,id);
		}

		return pid;
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

		Department department=beanMapper.map(departmentModel,Department.class);
		int id =departmentRepo.updateByPrimaryKey(department);
		if(department.getId()!=null){
			elasticSearchHelp.mergeES(department,department.getId().toString());
		}
		return id;
	}
	
	@Transactional
	@Override
	public int updateByPrimaryKeySelective(DepartmentModel departmentModel) {

		Department department=beanMapper.map(departmentModel,Department.class);
		int id =departmentRepo.updateByPrimaryKeySelective(department);
		if(department.getId()!=null){
			elasticSearchHelp.mergeES(department,department.getId().toString());
		}
		return id;
	}
	@Transactional
	@Override
	public  List<DepartmentModel> getDepartment(DepartmentModel departmentModel){
		Department department=beanMapper.map(departmentModel,Department.class);
		List<DepartmentModel> departmentModels=elasticSearchHelp.findESForList(department);
		return  departmentModels;
	}

}
