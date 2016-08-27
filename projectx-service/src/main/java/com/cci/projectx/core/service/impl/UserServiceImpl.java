package com.cci.projectx.core.service.impl;

import com.cci.projectx.core.ElasticSearchBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Pageable;

import com.cci.projectx.core.entity.User;
import com.cci.projectx.core.repository.UserRepository;
import com.cci.projectx.core.model.UserModel;
import com.cci.projectx.core.service.UserService;
import com.wlw.pylon.core.beans.mapping.BeanMapper;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private BeanMapper beanMapper;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    ElasticSearchBase elasticSearchBase;

    @Transactional
    @Override
    public int create(UserModel userModel) {
        User user = beanMapper.map(userModel, User.class);
        int id = userRepo.insert(user);
        if (id > 0) {
            userModel.setId(new Long(id));
            elasticSearchBase.mergeES(user, String.valueOf(id));
        }
        return id;
    }

    @Transactional
    @Override
    public int createSelective(UserModel userModel) {
        User user = beanMapper.map(userModel, User.class);
        int id = userRepo.insertSelective(user);
        if (id > 0) {
            userModel.setId(new Long(id));
            elasticSearchBase.mergeES(user, String.valueOf(id));
        }
        return id;
    }

    @Transactional
    @Override
    public int deleteByPrimaryKey(Long id) {
        int uid = userRepo.deleteByPrimaryKey(id);
        if (uid > 0) {
            elasticSearchBase.deleteES(User.class, id);
        }
        return uid;
    }

    @Transactional(readOnly = true)
    @Override
    public UserModel findByPrimaryKey(Long id) {
        User user = userRepo.selectByPrimaryKey(id);
        return beanMapper.map(user, UserModel.class);
    }

    @Transactional(readOnly = true)
    @Override
    public long selectCount(UserModel userModel) {
        return userRepo.selectCount(beanMapper.map(userModel, User.class));
    }

    @Transactional(readOnly = true)
    @Override
    public List<UserModel> selectPage(UserModel userModel, Pageable pageable) {
        User user = beanMapper.map(userModel, User.class);
        return beanMapper.mapAsList(userRepo.selectPage(user, pageable), UserModel.class);
    }

    @Transactional
    @Override
    public int updateByPrimaryKey(UserModel userModel) {
        User user = beanMapper.map(userModel, User.class);
        int id = userRepo.updateByPrimaryKey(user);
        if (id > 0) {
            userModel.setId(new Long(id));
            elasticSearchBase.mergeES(user, String.valueOf(id));
        }
        return id;
    }

    @Transactional
    @Override
    public int updateByPrimaryKeySelective(UserModel userModel) {
        User user = beanMapper.map(userModel, User.class);
        int id = userRepo.updateByPrimaryKeySelective(user);
        if (id > 0) {
            userModel.setId(new Long(id));
            elasticSearchBase.mergeES(user, String.valueOf(id));
        }
        return id;
    }

}
