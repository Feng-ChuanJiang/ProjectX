package com.cci.projectx.core.service.impl;

import com.cci.projectx.core.entity.Comment;
import com.cci.projectx.core.model.CommentModel;
import com.cci.projectx.core.repository.CommentRepository;
import com.cci.projectx.core.service.CommentService;
import com.wlw.pylon.core.beans.mapping.BeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

	@Autowired
	private BeanMapper beanMapper;

	@Autowired
	private CommentRepository commentRepo;

	@Transactional
	@Override
	public int create(CommentModel commentModel) {
		return commentRepo.insert(beanMapper.map(commentModel, Comment.class));
	}

	@Transactional
	@Override
	public int createSelective(CommentModel commentModel) {
		return commentRepo.insertSelective(beanMapper.map(commentModel, Comment.class));
	}

	@Transactional
	@Override
	public int deleteByPrimaryKey(Long id) {
		return commentRepo.deleteByPrimaryKey(id);
	}

	@Transactional(readOnly = true)
	@Override
	public CommentModel findByPrimaryKey(Long id) {
		Comment comment = commentRepo.selectByPrimaryKey(id);
		return beanMapper.map(comment, CommentModel.class);
	}

	@Transactional(readOnly = true)
	@Override
	public long selectCount(CommentModel commentModel) {
		return commentRepo.selectCount(beanMapper.map(commentModel, Comment.class));
	}

	@Transactional(readOnly = true)
	@Override
	public List<CommentModel> selectPage(CommentModel commentModel, Pageable pageable) {
		Comment comment = beanMapper.map(commentModel, Comment.class);
		return beanMapper.mapAsList(commentRepo.selectPage(comment,pageable),CommentModel.class);
	}

	@Transactional
	@Override
	public int updateByPrimaryKey(CommentModel commentModel) {
		return commentRepo.updateByPrimaryKey(beanMapper.map(commentModel, Comment.class));
	}
	
	@Transactional
	@Override
	public int updateByPrimaryKeySelective(CommentModel commentModel) {
		return commentRepo.updateByPrimaryKeySelective(beanMapper.map(commentModel, Comment.class));
	}

}
