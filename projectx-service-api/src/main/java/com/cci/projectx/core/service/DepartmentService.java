
package com.cci.projectx.core.service;

import com.cci.projectx.core.model.DepartmentModel;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface DepartmentService{

public int create(DepartmentModel departmentModel);

public int createSelective(DepartmentModel departmentModel);

public DepartmentModel findByPrimaryKey(Long id);

public int updateByPrimaryKey(DepartmentModel departmentModel);

public int updateByPrimaryKeySelective(DepartmentModel departmentModel);

public int deleteByPrimaryKey(Long id);

public long selectCount(DepartmentModel departmentModel);

public List<DepartmentModel> selectPage(DepartmentModel departmentModel, Pageable pageable);

}