package com.cci.projectx.core.service.impl;

import com.cci.projectx.core.ElasticSearchHelp;
import com.cci.projectx.core.domain.MajorNeo;
import com.cci.projectx.core.domain.UserNeo;
import com.cci.projectx.core.entity.major;
import com.cci.projectx.core.model.MajorModel;
import com.cci.projectx.core.model.UserContactsModel;
import com.cci.projectx.core.model.UserModel;
import com.cci.projectx.core.neorepository.MajorNeoRepository;
import com.cci.projectx.core.repository.majorRepository;
import com.cci.projectx.core.service.MajorService;
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
public class majorServiceImpl implements MajorService {

    @Autowired
    private BeanMapper beanMapper;

    @Autowired
    private majorRepository majorRepo;

    @Autowired
    private ElasticSearchHelp elasticSearchHelp;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private MajorNeoRepository majorNeoRepository;

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
        String sql = "SELECT COUNT(1) FROM MAJOR WHERE NAME=?";
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
        String sql = "SELECT ID FROM MAJOR WHERE NAME=?";
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
    public int create(MajorModel majorModel) {
        return createSelective(majorModel);
    }

    @Transactional
    @Override
    public int createSelective(MajorModel majorModel) {
        major m = beanMapper.map(majorModel, major.class);
        int id = 0;
        if (findCountByName(m.getName()) == 0) {
            id = majorRepo.insertSelective(m);
            if (m.getId() != null) {
                elasticSearchHelp.mergeES(m, m.getId().toString());
                MajorNeo majorNeo=new MajorNeo();
                majorNeo.setMajorId(m.getId());
                majorNeo.setName(m.getName());
                majorNeoRepository.save(majorNeo);
            }
        }
        return id;
    }

    @Transactional
    @Override
    public int deleteByPrimaryKey(Long id) {
        int mid = majorRepo.deleteByPrimaryKey(id);
        if (mid > 0) {
            elasticSearchHelp.deleteES(major.class, id);
            MajorNeo majorNeo=majorNeoRepository.findByMajorId(id);
            majorNeoRepository.delete(majorNeo.getId());
        }

        return mid;
    }

    @Transactional(readOnly = true)
    @Override
    public MajorModel findByPrimaryKey(Long id) {
        major major = majorRepo.selectByPrimaryKey(id);
        return beanMapper.map(major, MajorModel.class);
    }

    @Transactional(readOnly = true)
    @Override
    public long selectCount(MajorModel majorModel) {
        return majorRepo.selectCount(beanMapper.map(majorModel, major.class));
    }

    @Transactional(readOnly = true)
    @Override
    public List<MajorModel> selectPage(MajorModel majorModel, Pageable pageable) {
        major major = beanMapper.map(majorModel, com.cci.projectx.core.entity.major.class);
        return beanMapper.mapAsList(majorRepo.selectPage(major, pageable), MajorModel.class);
    }

    @Transactional
    @Override
    public int updateByPrimaryKey(MajorModel majorModel) {
        return updateByPrimaryKeySelective(majorModel);
    }

    @Transactional
    @Override
    public int updateByPrimaryKeySelective(MajorModel majorModel) {
        major m = beanMapper.map(majorModel, major.class);
        int id = majorRepo.updateByPrimaryKeySelective(m);
        if (m.getId() != null) {
            elasticSearchHelp.mergeES(m, m.getId().toString());
            MajorNeo majorNeo=majorNeoRepository.findByMajorId(m.getId());
            majorNeo.setName(m.getName());
            majorNeoRepository.save(majorNeo);
        }
        return id;
    }

    @Override
    public List<MajorModel> getMajor(MajorModel model) {
        major m = beanMapper.map(model, major.class);
        List<MajorModel> modelList = elasticSearchHelp.findESForList(m);

        return modelList;
    }

    @Transactional(readOnly = true)
    @Override
    public List<UserModel> getOneRelatCompany(Long userId , String name) {
        Collection<UserNeo> list = majorNeoRepository.getConnecOneUserFromName(userId,name);
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
        Collection<Map<String, Object>> list = majorNeoRepository.getConnecTwoUserFromName(userId, name);
        Collection<UserNeo> listOneUser = majorNeoRepository.getConnecOneUserFromName(userId, name);

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
