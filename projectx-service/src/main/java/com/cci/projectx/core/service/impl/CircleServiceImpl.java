package com.cci.projectx.core.service.impl;

import com.cci.projectx.core.entity.Circle;
import com.cci.projectx.core.model.CircleModel;
import com.cci.projectx.core.repository.CircleRepository;
import com.cci.projectx.core.service.CircleService;
import com.wlw.pylon.core.beans.mapping.BeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CircleServiceImpl implements CircleService {

	@Autowired
	private BeanMapper beanMapper;

	@Autowired
	private CircleRepository circleRepo;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Transactional
	@Override
	public int create(CircleModel circleModel) {
		return circleRepo.insert(beanMapper.map(circleModel, Circle.class));
	}

	@Transactional
	@Override
	public int createSelective(CircleModel circleModel) {
		return circleRepo.insertSelective(beanMapper.map(circleModel, Circle.class));
	}

	@Transactional
	@Override
	public int deleteByPrimaryKey(Long id) {
		return circleRepo.deleteByPrimaryKey(id);
	}

	@Transactional(readOnly = true)
	@Override
	public CircleModel findByPrimaryKey(Long id) {
		Circle circle = circleRepo.selectByPrimaryKey(id);
		return beanMapper.map(circle, CircleModel.class);
	}

	@Override
	public List<CircleModel> findByUserId(Long userId) {
		String sql="SELECT A.* FROM CIRCLE A,USER_CIRCLE B WHERE A.ID=B.CIRCLE_ID AND B.USER_ID=?";
		BeanPropertyRowMapper<CircleModel> bpr=new BeanPropertyRowMapper<>(CircleModel.class);
		List<CircleModel> circles=jdbcTemplate.query(sql,bpr,userId);
		return circles;
	}

	@Transactional(readOnly = true)
	@Override
	public long selectCount(CircleModel circleModel) {
		return circleRepo.selectCount(beanMapper.map(circleModel, Circle.class));
	}

	@Transactional(readOnly = true)
	@Override
	public List<CircleModel> selectPage(CircleModel circleModel, Pageable pageable) {
		Circle circle = beanMapper.map(circleModel, Circle.class);
		return beanMapper.mapAsList(circleRepo.selectPage(circle,pageable),CircleModel.class);
	}

	@Transactional
	@Override
	public int updateByPrimaryKey(CircleModel circleModel) {
		return circleRepo.updateByPrimaryKey(beanMapper.map(circleModel, Circle.class));
	}
	
	@Transactional
	@Override
	public int updateByPrimaryKeySelective(CircleModel circleModel) {
		return circleRepo.updateByPrimaryKeySelective(beanMapper.map(circleModel, Circle.class));
	}

}
