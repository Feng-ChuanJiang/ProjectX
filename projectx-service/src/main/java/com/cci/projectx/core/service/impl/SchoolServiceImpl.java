package com.cci.projectx.core.service.impl;

import com.cci.projectx.core.ElasticSearchHelp;
import com.cci.projectx.core.domain.SchoolNeo;
import com.cci.projectx.core.domain.UserNeo;
import com.cci.projectx.core.entity.School;
import com.cci.projectx.core.model.SchoolModel;
import com.cci.projectx.core.model.UserContactsModel;
import com.cci.projectx.core.model.UserModel;
import com.cci.projectx.core.neorepository.SchoolNeoRepository;
import com.cci.projectx.core.repository.SchoolRepository;
import com.cci.projectx.core.service.SchoolService;
import com.cci.projectx.core.service.UserService;
import com.wlw.pylon.core.beans.mapping.BeanMapper;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class SchoolServiceImpl implements SchoolService {

    @Autowired
    private BeanMapper beanMapper;

    @Autowired
    private SchoolRepository schoolRepo;

    @Autowired
    private ElasticSearchHelp elasticSearchHelp;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private SchoolNeoRepository schoolNeoRepository;

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
        String sql = "SELECT COUNT(1) FROM SCHOOL WHERE NAME=?";
        return jdbcTemplate.queryForObject(sql, Integer.class, name);
    }
    @Override
    public Long findIdByName(String name) {
        String sql = "SELECT ID FROM SCHOOL WHERE NAME=?";
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
    public int create(SchoolModel schoolModel) {
        return createSelective(schoolModel);
    }

    @Transactional
    @Override
    public int createSelective(SchoolModel schoolModel) {

        School school = beanMapper.map(schoolModel, School.class);
        int id = 0;
        if (findCountByName(school.getName()) == 0) {
            id = schoolRepo.insertSelective(school);
            if (school.getId() != null) {
                elasticSearchHelp.mergeES(school, school.getId().toString());
                SchoolNeo schoolNeo=new SchoolNeo();
                schoolNeo.setName(school.getName());
                schoolNeo.setSchoolId(school.getId());
                schoolNeoRepository.save(schoolNeo);
            }
        }
        return id;
    }

    @Transactional
    @Override
    public int deleteByPrimaryKey(Long id) {

        int sid = schoolRepo.deleteByPrimaryKey(id);
        if (sid > 0) {
            elasticSearchHelp.deleteES(School.class, id);
            SchoolNeo schoolNeo=schoolNeoRepository.findBySchoolId(id);
            schoolNeoRepository.delete(schoolNeo.getId());
        }
        return sid;
    }

    @Transactional(readOnly = true)
    @Override
    public SchoolModel findByPrimaryKey(Long id) {
        School school = schoolRepo.selectByPrimaryKey(id);
        return beanMapper.map(school, SchoolModel.class);
    }

    @Transactional(readOnly = true)
    @Override
    public long selectCount(SchoolModel schoolModel) {
        return schoolRepo.selectCount(beanMapper.map(schoolModel, School.class));
    }

    @Transactional(readOnly = true)
    @Override
    public List<SchoolModel> selectPage(SchoolModel schoolModel, Pageable pageable) {
        School school = beanMapper.map(schoolModel, School.class);
        return beanMapper.mapAsList(schoolRepo.selectPage(school, pageable), SchoolModel.class);
    }

    @Transactional
    @Override
    public int updateByPrimaryKey(SchoolModel schoolModel) {
        return  updateByPrimaryKeySelective(schoolModel);
    }

    @Transactional
    @Override
    public int updateByPrimaryKeySelective(SchoolModel schoolModel) {
        School school = beanMapper.map(schoolModel, School.class);
        int id = schoolRepo.updateByPrimaryKeySelective(school);
        if (school.getId() != null) {
            elasticSearchHelp.mergeES(school, school.getId().toString());
            SchoolNeo schoolNeo=schoolNeoRepository.findBySchoolId(school.getId());
            schoolNeo.setName(school.getName());
            schoolNeoRepository.save(schoolNeo);
        }
        return id;
    }

    /**
     * es模糊查询
     *
     * @param schoolModel
     * @return
     */
    @Transactional
    @Override
    public List<SchoolModel> getSchool(SchoolModel schoolModel) {
        School school = beanMapper.map(schoolModel, School.class);
        List<SchoolModel> schoolModelList = elasticSearchHelp.findESForList(school);

        return schoolModelList;
    }

    @Transactional(readOnly = true)
    @Override
    public List<UserModel> getOneRelatCompany(Long userId , String name) {
        Collection<UserNeo> list = schoolNeoRepository.getConnecOneUserFromName(userId,name);
        List<UserModel> userModels=new ArrayList<>();
        for (UserNeo userNeo : list) {
            UserModel userModel=   userService.findByPrimaryKey(userNeo.getUserId());
            userModels.add(userModel);
        }
        return userModels;
    }
    @Transactional(readOnly = true)
    @Override
    public List<UserContactsModel> getTwoRelatCompany(Long userId ,String name) {
        Collection<Map<String, Object>> list = schoolNeoRepository.getConnecTwoUserFromName(userId, name);
        Collection<UserNeo> listOneUser = schoolNeoRepository.getConnecOneUserFromName(userId, name);

        Map<Long, List<Long>> mapp = new LinkedHashMap<>();
        if (list != null) {
            for (Map<String, Object> map : list) {
                Long firdId = new Long(map.get("fridId").toString());
                Long muserId = new Long(map.get("userId").toString());
                List<Long> userids = new ArrayList<>();
                if (mapp.get(firdId) != null) {
                    userids = mapp.get(firdId);
                    userids.add(muserId);
                    mapp.put(firdId, userids);
                } else {
                    userids.add(muserId);
                    mapp.put(firdId, userids);
                }
            }
        }
        Map<Long,String> neoMap=new HashedMap();
        for (UserNeo userNeo : listOneUser) {
            neoMap.put(userNeo.getUserId(),"");
        }
        List<UserContactsModel> users = new ArrayList<>();
        for (Long key : mapp.keySet()) {
            List<Long> userids=mapp.get(key);
            UserModel userModel=   userService.findByPrimaryKey(key);
            UserContactsModel userContactsModel = beanMapper.map(userModel, UserContactsModel.class);
            List<UserModel> userModels = new ArrayList<>();
            for (Long userid : userids) {
                if(neoMap.get(userid)==null){
                    userModels.add(userService.findByPrimaryKey(userid));
                }else{
                    UserModel userModel1=   userService.findByPrimaryKey(userid);
                    userContactsModel = beanMapper.map(userModel1, UserContactsModel.class);
                }

            }
            userContactsModel.setFriends(userModels);
            users.add(userContactsModel);
        }
        return users;
    }

}
