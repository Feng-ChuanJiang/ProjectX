package com.cci.projectx.core.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Pageable;

import com.cci.projectx.core.entity.Image;
import com.cci.projectx.core.repository.ImageRepository;
import com.cci.projectx.core.model.ImageModel;
import com.cci.projectx.core.service.ImageService;
import com.wlw.pylon.core.beans.mapping.BeanMapper;

import java.util.List;

@Service
public class ImageServiceImpl implements ImageService {

	@Autowired
	private BeanMapper beanMapper;

	@Autowired
	private ImageRepository imageRepo;

	@Transactional
	@Override
	public int create(ImageModel imageModel) {
		return imageRepo.insert(beanMapper.map(imageModel, Image.class));
	}

	@Transactional
	@Override
	public int createSelective(ImageModel imageModel) {
		return imageRepo.insertSelective(beanMapper.map(imageModel, Image.class));
	}

	@Transactional
	@Override
	public int deleteByPrimaryKey(Long id) {
		return imageRepo.deleteByPrimaryKey(id);
	}

	@Transactional(readOnly = true)
	@Override
	public ImageModel findByPrimaryKey(Long id) {
		Image image = imageRepo.selectByPrimaryKey(id);
		return beanMapper.map(image, ImageModel.class);
	}

	@Transactional(readOnly = true)
	@Override
	public long selectCount(ImageModel imageModel) {
		return imageRepo.selectCount(beanMapper.map(imageModel, Image.class));
	}

	@Transactional(readOnly = true)
	@Override
	public List<ImageModel> selectPage(ImageModel imageModel,Pageable pageable) {
		Image image = beanMapper.map(imageModel, Image.class);
		return beanMapper.mapAsList(imageRepo.selectPage(image,pageable),ImageModel.class);
	}

	@Transactional
	@Override
	public int updateByPrimaryKey(ImageModel imageModel) {
		return imageRepo.updateByPrimaryKey(beanMapper.map(imageModel, Image.class));
	}
	
	@Transactional
	@Override
	public int updateByPrimaryKeySelective(ImageModel imageModel) {
		return imageRepo.updateByPrimaryKeySelective(beanMapper.map(imageModel, Image.class));
	}

}
