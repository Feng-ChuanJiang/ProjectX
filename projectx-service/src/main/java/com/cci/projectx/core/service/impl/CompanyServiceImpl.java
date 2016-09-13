package com.cci.projectx.core.service.impl;

import com.cci.projectx.core.ElasticSearchHelp;
import com.cci.projectx.core.domain.CompanyNeo;
import com.cci.projectx.core.domain.UserNeo;
import com.cci.projectx.core.entity.Company;
import com.cci.projectx.core.model.CompanyModel;
import com.cci.projectx.core.model.UserModel;
import com.cci.projectx.core.neorepository.CompanyNeoRepository;
import com.cci.projectx.core.repository.CompanyRepository;
import com.cci.projectx.core.service.CompanyService;
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
public class CompanyServiceImpl implements CompanyService {

    @Autowired
    private BeanMapper beanMapper;

    @Autowired
    private CompanyRepository companyRepo;

    @Autowired
    private ElasticSearchHelp elasticSearchHelp;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private CompanyNeoRepository companyNeoRepository;

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
        String sql = "SELECT COUNT(1) FROM COMPANY WHERE NAME=?";
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
        String sql = "SELECT ID FROM COMPANY WHERE NAME=?";
        List<Map<String, Object>> list=jdbcTemplate.queryForList(sql,name);
        Long id=null;
        if(list.size()>0){
            Map<String,Object> map=list.get(0);
            id=new Long(map.get("ID").toString());
        }
        return id;
    }

    /**
     * 插入的的时候保存到es中去
     *
     * @param companyModel
     * @return
     */
    @Transactional
    @Override
    public int create(CompanyModel companyModel) {
        return createSelective(companyModel);
    }

    /**
     * 插入的时候保存到es和neo4j
     *
     * @param companyModel
     * @return
     */
    @Transactional
    @Override
    public int createSelective(CompanyModel companyModel) {
        Company company = beanMapper.map(companyModel, Company.class);
        int id = 0;
        if (findCountByName(company.getName()) ==0) {
            //mysql
            id = companyRepo.insertSelective(company);
            if (company.getId() != null) {
                //es
                elasticSearchHelp.mergeES(company, company.getId().toString());
                //neo4j
                CompanyNeo companyNeo=new CompanyNeo();
                companyNeo.setName(company.getName());
                companyNeo.setCompanyId(company.getId());
                companyNeoRepository.save(companyNeo);
            }
        }
        return id;
    }

    /**
     * 同时清理es数据和neo4j
     *
     * @param id
     * @return
     */
    @Transactional
    @Override
    public int deleteByPrimaryKey(Long id) {
        //mysql
        int pid = companyRepo.deleteByPrimaryKey(id);
        if (pid > 0) {
            //es
            elasticSearchHelp.deleteES(Company.class, id);
            //neo4j
            CompanyNeo companyNeo=companyNeoRepository.findByCompanyId(id);
            companyNeoRepository.delete(companyNeo.getId());
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
        return beanMapper.mapAsList(companyRepo.selectPage(company, pageable), CompanyModel.class);
    }

    @Transactional
    @Override
    public int updateByPrimaryKey(CompanyModel companyModel) {
       return updateByPrimaryKeySelective(companyModel);
    }

    @Transactional
    @Override
    public int updateByPrimaryKeySelective(CompanyModel companyModel) {
        Company company = beanMapper.map(companyModel, Company.class);
        //mysql
        int id = companyRepo.updateByPrimaryKeySelective(company);
        if (company.getId() != null) {
            //es
            elasticSearchHelp.mergeES(company, company.getId().toString());
            //neo4j
            CompanyNeo companyNeo=companyNeoRepository.findByCompanyId(company.getId());
            companyNeo.setName(company.getName());
            companyNeoRepository.save(companyNeo);
        }
        return id;
    }
    @Transactional(readOnly = true)
    @Override
    public List<CompanyModel> getCompany(CompanyModel companyModel) {
        Company company = beanMapper.map(companyModel, Company.class);
        List<CompanyModel> companyModelList = elasticSearchHelp.findESForList(company);

        return companyModelList;
    }

    @Transactional(readOnly = true)
    @Override
    public List<UserModel> getOneRelatCompany(Long userId ,String name) {
        Collection<UserNeo> list = companyNeoRepository.getConnecOneUserFromName(userId,name);
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
        Collection<UserNeo> list = companyNeoRepository.getConnecTwoUserFromName(userId,name);
        List<UserModel> userModels=new ArrayList<>();
        for (UserNeo userNeo : list) {
            UserModel userModel=   userService.findByPrimaryKey(userNeo.getUserId());
            userModels.add(userModel);
        }
        return userModels;
    }
}
