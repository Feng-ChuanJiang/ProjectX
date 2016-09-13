package com.cci.projectx.core.service.impl;

import com.cci.projectx.core.ElasticSearchHelp;
import com.cci.projectx.core.domain.PostNeo;
import com.cci.projectx.core.domain.UserNeo;
import com.cci.projectx.core.entity.Post;
import com.cci.projectx.core.model.PostModel;
import com.cci.projectx.core.model.UserModel;
import com.cci.projectx.core.neorepository.PostNeoRepository;
import com.cci.projectx.core.repository.PostRepository;
import com.cci.projectx.core.service.PostService;
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
public class PostServiceImpl implements PostService {

    @Autowired
    private BeanMapper beanMapper;

    @Autowired
    private PostRepository postRepo;

    @Autowired
    private ElasticSearchHelp elasticSearchHelp;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private PostNeoRepository postNeoRepository;

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
        String sql = "SELECT COUNT(1) FROM POST WHERE NAME=?";
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
        String sql = "SELECT ID FROM POST WHERE NAME=?";
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
    public int create(PostModel postModel) {
        return createSelective(postModel);
    }

    @Transactional
    @Override
    public int createSelective(PostModel postModel) {
        Post post = beanMapper.map(postModel, Post.class);
        int id = 0;
        if (findCountByName(post.getName()) == 0) {
            id = postRepo.insertSelective(post);
            if (post.getId() != null) {
                elasticSearchHelp.mergeES(post, post.getId().toString());
                PostNeo postNeo=new PostNeo();
                postNeo.setName(post.getName());
                postNeo.setPostId(post.getId());
                postNeoRepository.save(postNeo);
            }
        }
        return id;
    }

    @Transactional
    @Override
    public int deleteByPrimaryKey(Long id) {
        int pid = postRepo.deleteByPrimaryKey(id);
        if (pid > 0) {
            elasticSearchHelp.deleteES(Post.class, id);
            PostNeo postNeo=postNeoRepository.findByPostId(id);
            postNeoRepository.delete(postNeo.getId());
        }

        return pid;
    }

    @Transactional(readOnly = true)
    @Override
    public PostModel findByPrimaryKey(Long id) {
        Post post = postRepo.selectByPrimaryKey(id);
        return beanMapper.map(post, PostModel.class);
    }

    @Transactional(readOnly = true)
    @Override
    public long selectCount(PostModel postModel) {
        return postRepo.selectCount(beanMapper.map(postModel, Post.class));
    }

    @Transactional(readOnly = true)
    @Override
    public List<PostModel> selectPage(PostModel postModel, Pageable pageable) {
        Post post = beanMapper.map(postModel, Post.class);
        return beanMapper.mapAsList(postRepo.selectPage(post, pageable), PostModel.class);
    }

    @Transactional
    @Override
    public int updateByPrimaryKey(PostModel postModel) {
        return updateByPrimaryKeySelective(postModel);
    }

    @Transactional
    @Override
    public int updateByPrimaryKeySelective(PostModel postModel) {
        Post post = beanMapper.map(postModel, Post.class);
        int id = postRepo.updateByPrimaryKeySelective(post);
        if (post.getId() != null) {
            elasticSearchHelp.mergeES(post, post.getId().toString());
            PostNeo postNeo=postNeoRepository.findByPostId(post.getId());
            postNeo.setName(post.getName());
            postNeoRepository.save(postNeo);
        }
        return id;
    }

    @Transactional
    @Override
    public List<PostModel> getPost(PostModel postModel) {
        Post post = beanMapper.map(postModel, Post.class);
        List<PostModel> postModelList = elasticSearchHelp.findESForList(post);
        return postModelList;
    }

    @Transactional(readOnly = true)
    @Override
    public List<UserModel> getOneRelatCompany(Long userId , String name) {
        Collection<UserNeo> list = postNeoRepository.getConnecOneUserFromName(userId,name);
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
        Collection<UserNeo> list = postNeoRepository.getConnecTwoUserFromName(userId,name);
        List<UserModel> userModels=new ArrayList<>();
        for (UserNeo userNeo : list) {
            UserModel userModel=   userService.findByPrimaryKey(userNeo.getUserId());
            userModels.add(userModel);
        }
        return userModels;
    }


}
