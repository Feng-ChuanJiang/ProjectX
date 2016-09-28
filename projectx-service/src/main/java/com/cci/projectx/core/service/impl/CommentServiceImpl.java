package com.cci.projectx.core.service.impl;

import com.cci.projectx.core.entity.Comment;
import com.cci.projectx.core.model.CommentModel;
import com.cci.projectx.core.model.InteractModel;
import com.cci.projectx.core.repository.CommentRepository;
import com.cci.projectx.core.service.CommentService;
import com.wlw.pylon.core.beans.mapping.BeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

	@Autowired
	private BeanMapper beanMapper;

	@Autowired
	private CommentRepository commentRepo;

	@Autowired
	private JdbcTemplate jdbcTemplate;

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

	/**
	 * 所有评论和点赞
	 * @param interactModels
	 * @return
     */
	@Transactional(readOnly = true)
	@Override
	public List<InteractModel> setAllCommentByInteract( List<InteractModel> interactModels) {
		BeanPropertyRowMapper<CommentModel> bpr=new BeanPropertyRowMapper<>(CommentModel.class);
		for (InteractModel i : interactModels) {
			//得到所有评论
			String sql="SELECT * FROM `COMMENT` WHERE  INTERACT_ID=? AND TYPE<>2 AND COMMENT_ID IS NULL ORDER BY CREAT_TIME DESC";
			List<CommentModel> comment=jdbcTemplate.query(sql,bpr,i.getId());
			i.setComments(comment);
			//得到评论的评论
			for (CommentModel c : comment) {
				 sql="SELECT * FROM `COMMENT` WHERE  INTERACT_ID=? AND TYPE<>2 AND COMMENT_ID =? ORDER BY CREAT_TIME DESC";
				 List<CommentModel> commen=jdbcTemplate.query(sql,bpr,i.getId(),c.getId());
				 c.setCommentModels(commen);
			}
			//得到所有点赞
			sql="SELECT * FROM `COMMENT` WHERE  INTERACT_ID=? AND TYPE=2 ORDER BY CREAT_TIME DESC";
			List<CommentModel> praises=jdbcTemplate.query(sql,bpr,i.getId());
			i.setPraises(praises);

		}

		return interactModels;
	}

	/**
	 * 朋友评论和所有点赞
	 * @param interactModels
	 * @return
	 */
	@Transactional(readOnly = true)
	@Override
	public List<InteractModel> setFriendCommentByInteract( List<InteractModel> interactModels,Long userId) {
		BeanPropertyRowMapper<CommentModel> bpr=new BeanPropertyRowMapper<>(CommentModel.class);
		for (InteractModel i : interactModels) {
			//得到朋友评论
			String sql="SELECT * FROM (SELECT A.* FROM `COMMENT` A,FRIENDS B WHERE  A.USER_ID=B.USER_ID AND B.FRIEND_ID=? AND B.STATE=1 AND  A.INTERACT_ID=? AND A.TYPE<>2 AND A.COMMENT_ID IS NULL\n" +
					"UNION ALL\n" +
					"SELECT A.* FROM `COMMENT` A,FRIENDS B WHERE  A.USER_ID=B.FRIEND_ID AND B.USER_ID=? AND B.STATE=1 AND  A.INTERACT_ID=? AND A.TYPE<>2 AND A.COMMENT_ID IS NULL )C ORDER BY C.CREAT_TIME DESC";
			List<CommentModel> comment=jdbcTemplate.query(sql,bpr,userId,i.getId(),userId,i.getId());
			i.setComments(comment);
			//得到评论的评论
			for (CommentModel c : comment) {
				sql="SELECT * FROM `COMMENT` WHERE  INTERACT_ID=? AND TYPE<>2 AND COMMENT_ID =? ORDER BY CREAT_TIME DESC";
				List<CommentModel> commen=jdbcTemplate.query(sql,bpr,i.getId(),c.getId());
				c.setCommentModels(commen);
			}
			//得到所有点赞
			sql="SELECT * FROM `COMMENT` WHERE  INTERACT_ID=? AND TYPE=2 ORDER BY CREAT_TIME DESC";
			List<CommentModel> praises=jdbcTemplate.query(sql,bpr,i.getId());
			i.setPraises(praises);

		}

		return interactModels;
	}

}
