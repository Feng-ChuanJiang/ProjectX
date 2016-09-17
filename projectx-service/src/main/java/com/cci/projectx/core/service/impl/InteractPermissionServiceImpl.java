package com.cci.projectx.core.service.impl;

import com.cci.projectx.core.entity.InteractPermission;
import com.cci.projectx.core.model.InteractPermissionModel;
import com.cci.projectx.core.repository.InteractPermissionRepository;
import com.cci.projectx.core.service.InteractPermissionService;
import com.wlw.pylon.core.beans.mapping.BeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class InteractPermissionServiceImpl implements InteractPermissionService {

	@Autowired
	private BeanMapper beanMapper;

	@Autowired
	private InteractPermissionRepository interactPermissionRepo;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Transactional
	@Override
	public int create(InteractPermissionModel interactPermissionModel) {
		return interactPermissionRepo.insert(beanMapper.map(interactPermissionModel, InteractPermission.class));
	}

	@Transactional
	@Override
	public int createSelective(InteractPermissionModel interactPermissionModel) {
		return interactPermissionRepo.insertSelective(beanMapper.map(interactPermissionModel, InteractPermission.class));
	}

	@Transactional
	@Override
	public int deleteByPrimaryKey(Long id) {
		return interactPermissionRepo.deleteByPrimaryKey(id);
	}

	@Transactional(readOnly = true)
	@Override
	public InteractPermissionModel findByPrimaryKey(Long id) {
		InteractPermission interactPermission = interactPermissionRepo.selectByPrimaryKey(id);
		return beanMapper.map(interactPermission, InteractPermissionModel.class);
	}

	@Transactional(readOnly = true)
	@Override
	public long selectCount(InteractPermissionModel interactPermissionModel) {
		return interactPermissionRepo.selectCount(beanMapper.map(interactPermissionModel, InteractPermission.class));
	}

	@Transactional(readOnly = true)
	@Override
	public List<InteractPermissionModel> selectPage(InteractPermissionModel interactPermissionModel, Pageable pageable) {
		InteractPermission interactPermission = beanMapper.map(interactPermissionModel, InteractPermission.class);
		return beanMapper.mapAsList(interactPermissionRepo.selectPage(interactPermission,pageable),InteractPermissionModel.class);
	}

	@Transactional
	@Override
	public int updateByPrimaryKey(InteractPermissionModel interactPermissionModel) {
		return interactPermissionRepo.updateByPrimaryKey(beanMapper.map(interactPermissionModel, InteractPermission.class));
	}
	
	@Transactional
	@Override
	public int updateByPrimaryKeySelective(InteractPermissionModel interactPermissionModel) {
		return interactPermissionRepo.updateByPrimaryKeySelective(beanMapper.map(interactPermissionModel, InteractPermission.class));
	}

	@Transactional
	@Override
	public int deleteByUserIdandInteractId(Long userId ,Long interactId) {
		String sql ="DELETE FROM INTERACT_PERMISSION WHERE USER_ID=? AND INTERACT_ID=?";
		return jdbcTemplate.update(sql,userId,interactId);
	}

}
