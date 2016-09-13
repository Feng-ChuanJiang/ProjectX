
package com.cci.projectx.core.service;

import com.cci.projectx.core.model.DepartmentModel;
import com.cci.projectx.core.model.UserModel;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface DepartmentService {

    public int create(DepartmentModel departmentModel);

    public int createSelective(DepartmentModel departmentModel);

    public DepartmentModel findByPrimaryKey(Long id);

    public int updateByPrimaryKey(DepartmentModel departmentModel);

    public int updateByPrimaryKeySelective(DepartmentModel departmentModel);

    public int deleteByPrimaryKey(Long id);

    public long selectCount(DepartmentModel departmentModel);

    public List<DepartmentModel> selectPage(DepartmentModel departmentModel, Pageable pageable);

    public List<DepartmentModel> getDepartment(DepartmentModel departmentModel);

    public int findCountByName(String name);

    public Long findIdByName(String name);

    public List<UserModel> getOneRelatCompany(Long userId, String name);

    public List<UserModel> getTwoRelatCompany(Long userId, String name);
}