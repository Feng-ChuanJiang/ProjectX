package com.cci.projectx.core.service.impl;

import com.cci.projectx.core.ElasticSearchHelp;
import com.cci.projectx.core.domain.DepartmentNeo;
import com.cci.projectx.core.domain.UserNeo;
import com.cci.projectx.core.entity.Department;
import com.cci.projectx.core.model.DepartmentModel;
import com.cci.projectx.core.model.UserModel;
import com.cci.projectx.core.neorepository.DepartmentNeoRepository;
import com.cci.projectx.core.repository.DepartmentRepository;
import com.cci.projectx.core.service.DepartmentService;
import com.cci.projectx.core.service.UserService;
import com.wlw.pylon.core.beans.mapping.BeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    @Autowired
    private BeanMapper beanMapper;

    @Autowired
    private DepartmentRepository departmentRepo;

    @Autowired
    private ElasticSearchHelp elasticSearchHelp;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private DepartmentNeoRepository departmentNeoRepository;

    @Autowired
    private UserService userService;

    /**
     * 根据名称找条数
     *
     * @param name
     * @return
     */
    @Override
    public int findCountByName(String name) {
        String sql = "SELECT COUNT(1) FROM DEPARTMENT WHERE NAME=?";
        return jdbcTemplate.queryForObject(sql, Integer.class, name);
    }
    /**
     * 根据名称找编号
     *
     * @param name
     * @return
     */
    @Override
    public Long findIdByName(String name) {
        String sql = "SELECT ID FROM DEPARTMENT WHERE NAME=?";
        List<Map<String, Object>> list=jdbcTemplate.queryForList(sql,name);
        Long id=null;
        if(list.size()>0){
            Map<String,Object> map=list.get(0);
            id=new Long(map.get("ID").toString());
        }
        return id;
    }
    @Transactional
    @Override
    public int create(DepartmentModel departmentModel) {
        return createSelective(departmentModel);
    }

    @Transactional
    @Override
    public int createSelective(DepartmentModel departmentModel) {
        Department department = beanMapper.map(departmentModel, Department.class);
        int id = 0;
        if (findCountByName(department.getName()) == 0) {
            id = departmentRepo.insertSelective(department);
            if (department.getId() != null) {
                elasticSearchHelp.mergeES(department, department.getId().toString());
                DepartmentNeo departmentNeo=new DepartmentNeo();
                departmentNeo.setName(department.getName());
                departmentNeo.setDepartmentId(department.getId());
                departmentNeoRepository.save(departmentNeo);
            }
        }
        return id;
    }

    @Transactional
    @Override
    public int deleteByPrimaryKey(Long id) {
        int pid = departmentRepo.deleteByPrimaryKey(id);
        if (pid > 0) {
            elasticSearchHelp.deleteES(Department.class, id);
            DepartmentNeo departmentNeo=departmentNeoRepository.findByDepartmentId(id);
            departmentNeoRepository.delete(departmentNeo.getId());
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
        return beanMapper.mapAsList(departmentRepo.selectPage(department, pageable), DepartmentModel.class);
    }

    @Transactional
    @Override
    public int updateByPrimaryKey(DepartmentModel departmentModel) {
        return updateByPrimaryKeySelective(departmentModel);
    }

    @Transactional
    @Override
    public int updateByPrimaryKeySelective(DepartmentModel departmentModel) {

        Department department = beanMapper.map(departmentModel, Department.class);
        int id = departmentRepo.updateByPrimaryKeySelective(department);
        if (department.getId() != null) {
            elasticSearchHelp.mergeES(department, department.getId().toString());
            DepartmentNeo departmentNeo=departmentNeoRepository.findByDepartmentId(department.getId());
            departmentNeo.setName(department.getName());
            departmentNeoRepository.save(departmentNeo);
        }
        return id;
    }

    @Transactional
    @Override
    public List<DepartmentModel> getDepartment(DepartmentModel departmentModel) {
        Department department = beanMapper.map(departmentModel, Department.class);
        List<DepartmentModel> departmentModels = elasticSearchHelp.findESForList(department);
        return departmentModels;
    }

    @Transactional(readOnly = true)
    @Override
    public List<UserModel> getOneRelatCompany(Long userId , String name) {
        Collection<UserNeo> list = departmentNeoRepository.getConnecOneUserFromName(userId,name);
        List<UserModel> userModels=new ArrayList<>();
        for (UserNeo userNeo : list) {
            UserModel userModel=   userService.findByPrimaryKey(userNeo.getUserId());
            userModels.add(userModel);
        }
        return userModels;
    }
    @Transactional(readOnly = true)
    @Override
    public List<UserModel> getTwoRelatCompany(Long userId ,String name) {
        Collection<UserNeo> list = departmentNeoRepository.getConnecTwoUserFromName(userId,name);
        List<UserModel> userModels=new ArrayList<>();
        for (UserNeo userNeo : list) {
            UserModel userModel=   userService.findByPrimaryKey(userNeo.getUserId());
            userModels.add(userModel);
        }
        return userModels;
    }

}
