package com.cci.projectx.core.service.impl;

import com.cci.projectx.core.ElasticSearchHelp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Pageable;

import com.cci.projectx.core.entity.Comment;
import com.cci.projectx.core.repository.CommentRepository;
import com.cci.projectx.core.model.CommentModel;
import com.cci.projectx.core.service.CommentService;
import com.wlw.pylon.core.beans.mapping.BeanMapper;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

	@Autowired
	private BeanMapper beanMapper;

	@Autowired
	private CommentRepository commentRepo;

	@Autowired
	private ElasticSearchHelp searchHelp;

	/**
	 * 在新增数据的时候同时插入ES
	 * @param commentModel
	 * @return
	 */
	@Transactional
	@Override
	public int create(CommentModel commentModel) {

		Comment comment= beanMapper.map(commentModel,Comment.class);
		int id =commentRepo.insert(comment);
		if(comment.getId()!=null){
			searchHelp.mergeES(comment,comment.getId().toString());
		}


		return id;
	}
	/**
	 * 在新增数据的时候同时插入ES
	 * @param commentModel
	 * @return
	 */
	@Transactional
	@Override
	public int createSelective(CommentModel commentModel) {
		Comment comment=beanMapper.map(commentModel,Comment.class);
		int id= commentRepo.insertSelective(comment);
		if(comment.getId()!=null){
			searchHelp.mergeES(comment,comment.getId().toString());
		}
		return id;
	}

	/**
	 * 删除数据同时删除es数据
	 * @param id
	 * @return
	 */
	@Transactional
	@Override
	public int deleteByPrimaryKey(Long id) {

		int wid=commentRepo.deleteByPrimaryKey(id);
		if(wid>0){
			searchHelp.deleteES(Comment.class,id);
		}
		return wid;
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
	public List<CommentModel> selectPage(CommentModel commentModel,Pageable pageable) {
		Comment comment = beanMapper.map(commentModel, Comment.class);
		return beanMapper.mapAsList(commentRepo.selectPage(comment,pageable),CommentModel.class);
	}

	@Transactional
	@Override
	public int updateByPrimaryKey(CommentModel commentModel) {
		Comment comment=beanMapper.map(commentModel,Comment.class);
		int id=commentRepo.updateByPrimaryKey(comment);
		if(comment.getId()!=null){
			searchHelp.mergeES(comment,comment.getId().toString());
		}
		return id;
	}
	
	@Transactional
	@Override
	public int updateByPrimaryKeySelective(CommentModel commentModel) {
		Comment comment=beanMapper.map(commentModel,Comment.class);
		int id=commentRepo.updateByPrimaryKeySelective(comment);
		if(comment.getId()!=null){
			searchHelp.mergeES(comment,comment.getId().toString());
		}
		return id;
	}

	/**
	 * 通过模糊查询互动信息es
	 * @param commentModel
	 * @return
	 */
	@Transactional
	@Override
	public List<CommentModel> getComment(CommentModel commentModel){
		Comment comment=beanMapper.map(commentModel,Comment.class);
		List<CommentModel> commentModelList=searchHelp.findESForList(comment);
		return  commentModelList;
	}

}
