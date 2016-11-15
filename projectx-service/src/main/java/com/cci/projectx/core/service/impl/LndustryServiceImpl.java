package com.cci.projectx.core.service.impl;

import com.cci.projectx.core.ElasticSearchHelp;
import com.cci.projectx.core.domain.LndustryNeo;
import com.cci.projectx.core.domain.UserNeo;
import com.cci.projectx.core.entity.Lndustry;
import com.cci.projectx.core.model.LndustryModel;
import com.cci.projectx.core.model.UserContactsModel;
import com.cci.projectx.core.model.UserModel;
import com.cci.projectx.core.neorepository.LndustryNeoRepository;
import com.cci.projectx.core.repository.LndustryRepository;
import com.cci.projectx.core.service.LndustryService;
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
public class LndustryServiceImpl implements LndustryService {

    @Autowired
    private BeanMapper beanMapper;

    @Autowired
    private LndustryRepository lndustryRepo;

    @Autowired
    private ElasticSearchHelp elasticSearchHelp;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private LndustryNeoRepository lndustryNeoRepository;

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
        String sql = "SELECT COUNT(1) FROM LNDUSTRY WHERE NAME=?";
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
        String sql = "SELECT ID FROM LNDUSTRY WHERE NAME=?";
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
    public int create(LndustryModel lndustryModel) {
        return createSelective(lndustryModel);
    }

    @Transactional
    @Override
    public int createSelective(LndustryModel lndustryModel) {
        Lndustry lndustry = beanMapper.map(lndustryModel, Lndustry.class);
        int id = 0;
        if (findCountByName(lndustry.getName()) == 0) {
            id = lndustryRepo.insertSelective(lndustry);
            if (lndustry.getId() != null) {
                elasticSearchHelp.mergeES(lndustry, lndustry.getId().toString());
                LndustryNeo lndustryNeo=new LndustryNeo();
                lndustryNeo.setLndustryId(lndustry.getId());
                lndustryNeo.setName(lndustry.getName());
                lndustryNeoRepository.save(lndustryNeo);
            }
        }
        return id;
    }

    @Transactional
    @Override
    public int deleteByPrimaryKey(Long id) {
        int pid = lndustryRepo.deleteByPrimaryKey(id);
        if (pid > 0) {
            elasticSearchHelp.deleteES(Lndustry.class, id);
            LndustryNeo lndustryNeo=lndustryNeoRepository.findByLndustryId(id);
            lndustryNeoRepository.delete(lndustryNeo.getId());

        }
        return pid;
    }

    @Transactional(readOnly = true)
    @Override
    public LndustryModel findByPrimaryKey(Long id) {
        Lndustry lndustry = lndustryRepo.selectByPrimaryKey(id);
        return beanMapper.map(lndustry, LndustryModel.class);
    }

    @Transactional(readOnly = true)
    @Override
    public long selectCount(LndustryModel lndustryModel) {
        return lndustryRepo.selectCount(beanMapper.map(lndustryModel, Lndustry.class));
    }

    @Transactional(readOnly = true)
    @Override
    public List<LndustryModel> selectPage(LndustryModel lndustryModel, Pageable pageable) {
        Lndustry lndustry = beanMapper.map(lndustryModel, Lndustry.class);
        return beanMapper.mapAsList(lndustryRepo.selectPage(lndustry, pageable), LndustryModel.class);
    }

    @Transactional
    @Override
    public int updateByPrimaryKey(LndustryModel lndustryModel) {
        return updateByPrimaryKeySelective( lndustryModel);
    }

    @Transactional
    @Override
    public int updateByPrimaryKeySelective(LndustryModel lndustryModel) {
        Lndustry lndustry = beanMapper.map(lndustryModel, Lndustry.class);
        int id = lndustryRepo.updateByPrimaryKeySelective(lndustry);
        if (lndustry.getId() != null) {
            elasticSearchHelp.mergeES(lndustry, lndustry.getId().toString());
            LndustryNeo lndustryNeo=lndustryNeoRepository.findByLndustryId(lndustry.getId());
            lndustryNeo.setName(lndustry.getName());
            lndustryNeoRepository.save(lndustryNeo);
        }
        return id;
    }

    @Transactional
    @Override
    public List<LndustryModel> getLndustry(LndustryModel lndustryModel) {
        Lndustry lndustry = beanMapper.map(lndustryModel, Lndustry.class);
        List<LndustryModel> lndustryModelList = elasticSearchHelp.findESForList(lndustry);
        return lndustryModelList;
    }

    @Transactional(readOnly = true)
    @Override
    public List<UserModel> getOneRelatCompany(Long userId , String name) {
        Collection<UserNeo> list = lndustryNeoRepository.getConnecOneUserFromName(userId,name);
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
        Collection<Map<String, Object>> list = lndustryNeoRepository.getConnecTwoUserFromName(userId, name);
        Collection<UserNeo> listOneUser = lndustryNeoRepository.getConnecOneUserFromName(userId, name);

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
