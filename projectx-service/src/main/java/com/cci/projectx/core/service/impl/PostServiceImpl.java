package com.cci.projectx.core.service.impl;

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

	@Transactional
	@Override
	public int create(PostModel postModel) {
		return postRepo.insert(beanMapper.map(postModel, Post.class));
	}

	@Transactional
	@Override
	public int createSelective(PostModel postModel) {
		return postRepo.insertSelective(beanMapper.map(postModel, Post.class));
	}

	@Transactional
	@Override
	public int deleteByPrimaryKey(Long id) {
		return postRepo.deleteByPrimaryKey(id);
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
		return postRepo.updateByPrimaryKey(beanMapper.map(postModel, Post.class));
	}
	
	@Transactional
	@Override
	public int updateByPrimaryKeySelective(PostModel postModel) {
		return postRepo.updateByPrimaryKeySelective(beanMapper.map(postModel, Post.class));
	}

}
