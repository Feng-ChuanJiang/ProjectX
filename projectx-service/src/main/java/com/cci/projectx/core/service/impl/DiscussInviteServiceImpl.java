package com.cci.projectx.core.service.impl;

import com.cci.projectx.core.entity.DiscussInvite;
import com.cci.projectx.core.model.DiscussInviteModel;
import com.cci.projectx.core.repository.DiscussInviteRepository;
import com.cci.projectx.core.service.DiscussInviteService;
import com.wlw.pylon.core.beans.mapping.BeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DiscussInviteServiceImpl implements DiscussInviteService {

	@Autowired
	private BeanMapper beanMapper;

	@Autowired
	private DiscussInviteRepository discussInviteRepo;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Transactional
	@Override
	public int create(DiscussInviteModel discussInviteModel) {
		return discussInviteRepo.insert(beanMapper.map(discussInviteModel, DiscussInvite.class));
	}

	@Transactional
	@Override
	public int createSelective(DiscussInviteModel discussInviteModel) {
		return discussInviteRepo.insertSelective(beanMapper.map(discussInviteModel, DiscussInvite.class));
	}

	@Transactional
	@Override
	public int deleteByPrimaryKey(Long id) {
		return discussInviteRepo.deleteByPrimaryKey(id);
	}

	@Transactional(readOnly = true)
	@Override
	public DiscussInviteModel findByPrimaryKey(Long id) {
		DiscussInvite discussInvite = discussInviteRepo.selectByPrimaryKey(id);
		return beanMapper.map(discussInvite, DiscussInviteModel.class);
	}

	@Transactional(readOnly = true)
	@Override
	public long selectCount(DiscussInviteModel discussInviteModel) {
		return discussInviteRepo.selectCount(beanMapper.map(discussInviteModel, DiscussInvite.class));
	}

	@Transactional(readOnly = true)
	@Override
	public List<DiscussInviteModel> selectPage(DiscussInviteModel discussInviteModel,Pageable pageable) {
		DiscussInvite discussInvite = beanMapper.map(discussInviteModel, DiscussInvite.class);
		return beanMapper.mapAsList(discussInviteRepo.selectPage(discussInvite,pageable),DiscussInviteModel.class);
	}

	@Transactional
	@Override
	public int updateByPrimaryKey(DiscussInviteModel discussInviteModel) {
		return discussInviteRepo.updateByPrimaryKey(beanMapper.map(discussInviteModel, DiscussInvite.class));
	}
	
	@Transactional
	@Override
	public int updateByPrimaryKeySelective(DiscussInviteModel discussInviteModel) {
		return discussInviteRepo.updateByPrimaryKeySelective(beanMapper.map(discussInviteModel, DiscussInvite.class));
	}

	@Transactional
	@Override
	public int deleteByPrimary(DiscussInviteModel discussInvite) {
		String sql="DELETE FROM DISCUSS_INVITE WHERE USER_ID=? AND FRIEND_ID=? AND DISCUSS_ID=?";
		return jdbcTemplate.update(sql,discussInvite.getUserId(),discussInvite.getFriendId(),discussInvite.getDiscussId());

	}

	@Transactional
	@Override
	public int deleteByPrimaryDiscussId(Long discussId) {
		String sql="DELETE FROM DISCUSS_INVITE WHERE  DISCUSS_ID=?";
		return jdbcTemplate.update(sql,discussId);

	}

}
