package com.cci.projectx.core.service.impl;

import com.cci.projectx.core.entity.DiscussPermission;
import com.cci.projectx.core.model.DiscussPermissionModel;
import com.cci.projectx.core.repository.DiscussPermissionRepository;
import com.cci.projectx.core.service.DiscussPermissionService;
import com.wlw.pylon.core.beans.mapping.BeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DiscussPermissionServiceImpl implements DiscussPermissionService {

	@Autowired
	private BeanMapper beanMapper;

	@Autowired
	private DiscussPermissionRepository discussPermissionRepo;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Transactional
	@Override
	public int create(DiscussPermissionModel discussPermissionModel) {
		return discussPermissionRepo.insert(beanMapper.map(discussPermissionModel, DiscussPermission.class));
	}

	@Transactional
	@Override
	public int createSelective(DiscussPermissionModel discussPermissionModel) {
		return discussPermissionRepo.insertSelective(beanMapper.map(discussPermissionModel, DiscussPermission.class));
	}

	@Transactional
	@Override
	public int deleteByPrimaryKey(Long id) {
		return discussPermissionRepo.deleteByPrimaryKey(id);
	}

	@Transactional(readOnly = true)
	@Override
	public DiscussPermissionModel findByPrimaryKey(Long id) {
		DiscussPermission discussPermission = discussPermissionRepo.selectByPrimaryKey(id);
		return beanMapper.map(discussPermission, DiscussPermissionModel.class);
	}

	@Transactional(readOnly = true)
	@Override
	public long selectCount(DiscussPermissionModel discussPermissionModel) {
		return discussPermissionRepo.selectCount(beanMapper.map(discussPermissionModel, DiscussPermission.class));
	}

	@Transactional(readOnly = true)
	@Override
	public List<DiscussPermissionModel> selectPage(DiscussPermissionModel discussPermissionModel,Pageable pageable) {
		DiscussPermission discussPermission = beanMapper.map(discussPermissionModel, DiscussPermission.class);
		return beanMapper.mapAsList(discussPermissionRepo.selectPage(discussPermission,pageable),DiscussPermissionModel.class);
	}

	@Transactional
	@Override
	public int updateByPrimaryKey(DiscussPermissionModel discussPermissionModel) {
		return discussPermissionRepo.updateByPrimaryKey(beanMapper.map(discussPermissionModel, DiscussPermission.class));
	}
	
	@Transactional
	@Override
	public int updateByPrimaryKeySelective(DiscussPermissionModel discussPermissionModel) {
		return discussPermissionRepo.updateByPrimaryKeySelective(beanMapper.map(discussPermissionModel, DiscussPermission.class));
	}
	@Transactional
	@Override
	public int deleteByPrimary(DiscussPermissionModel permissionModel) {
		String sql="DELETE FROM DISCUSS_PERMISSION WHERE USER_ID=? AND DISCUSS_ID=?";
		return jdbcTemplate.update(sql,permissionModel.getUserId(),permissionModel.getDiscussId());

	}
	@Transactional
	@Override
	public int deleteByPrimaryDiscussId(Long discussId) {
		String sql="DELETE FROM DISCUSS_PERMISSION WHERE  DISCUSS_ID=?";
		return jdbcTemplate.update(sql,discussId);

	}

}
