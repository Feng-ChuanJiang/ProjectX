package com.cci.projectx.core.service.impl;

import com.cci.projectx.core.ElasticSearchHelp;
import com.cci.projectx.core.entity.Post;
import com.cci.projectx.core.model.PostModel;
import com.cci.projectx.core.repository.PostRepository;
import com.cci.projectx.core.service.PostService;
import com.wlw.pylon.core.beans.mapping.BeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PostServiceImpl implements PostService {

	@Autowired
	private BeanMapper beanMapper;

	@Autowired
	private PostRepository postRepo;

	@Autowired
	private ElasticSearchHelp elasticSearchHelp;
	@Transactional
	@Override
	public int create(PostModel postModel) {
		Post post=beanMapper.map(postModel,Post.class);
		int id=postRepo.insert(post);
		if(post.getId()!=null){
			elasticSearchHelp.mergeES(post,post.getId().toString());
		}

		return id;
	}

	@Transactional
	@Override
	public int createSelective(PostModel postModel) {
		Post post=beanMapper.map(postModel,Post.class);
		int id=postRepo.insertSelective(post);
		if(post.getId()!=null){
			elasticSearchHelp.mergeES(post,post.getId().toString());
		}
		return id;
	}

	@Transactional
	@Override
	public int deleteByPrimaryKey(Long id) {
		int pid=postRepo.deleteByPrimaryKey(id);
		if(pid>0){
			elasticSearchHelp.deleteES(Post.class,id);
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
		return beanMapper.mapAsList(postRepo.selectPage(post,pageable),PostModel.class);
	}

	@Transactional
	@Override
	public int updateByPrimaryKey(PostModel postModel) {

		Post post=beanMapper.map(postModel,Post.class);
		int id=postRepo.updateByPrimaryKey(post);
		if(post.getId()!=null){
			elasticSearchHelp.mergeES(post,post.getId().toString());
		}
		return id;
	}
	
	@Transactional
	@Override
	public int updateByPrimaryKeySelective(PostModel postModel) {
		Post post=beanMapper.map(postModel,Post.class);
		int id=postRepo.updateByPrimaryKeySelective(post);
		if(post.getId()!=null){
			elasticSearchHelp.mergeES(post,post.getId().toString());
		}
		return id;
	}
	@Transactional
	@Override
	public List<PostModel> getPost(PostModel postModel){
		Post post=beanMapper.map(postModel,Post.class);
		List<PostModel> postModelList=elasticSearchHelp.findESForList(post);
		return  postModelList;
	}

}
