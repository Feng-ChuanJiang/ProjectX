package com.cci.projectx.core.service.impl;

import com.cci.projectx.core.entity.Photo;
import com.cci.projectx.core.model.PhotoModel;
import com.cci.projectx.core.repository.PhotoRepository;
import com.cci.projectx.core.service.PhotoService;
import com.wlw.pylon.core.beans.mapping.BeanMapper;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
public class PhotoServiceImpl implements PhotoService {

	private final Logger logger = LoggerFactory.getLogger(PhotoServiceImpl.class);

	@Autowired
	private BeanMapper beanMapper;

	@Autowired
	private PhotoRepository photoRepo;

	@Value("${photo.base.url}")
	private String baseDirectory;

	@Transactional
	@Override
	public int create(PhotoModel photoModel) {
		return photoRepo.insert(beanMapper.map(photoModel, Photo.class));
	}

	@Transactional
	@Override
	public int createSelective(PhotoModel photoModel) {
		Photo photo = beanMapper.map(photoModel, Photo.class);
		int retVal = photoRepo.insertSelective(photo);
		photoModel.setId(photo.getId());
		return retVal;
	}

	@Transactional
	@Override
	public int deleteByPrimaryKey(Long id) {
		return photoRepo.deleteByPrimaryKey(id);
	}

	@Override
	public int deletePhoto(Long id) {
		PhotoModel photoModel = findByPrimaryKey(id);
		try {
			FileUtils.forceDelete(new File(baseDirectory + "/" + photoModel.getPath()));
			deleteByPrimaryKey(id);
		} catch (IOException e) {
			logger.error("unexpected error,", e);
		}
		return 0;
	}

	@Transactional(readOnly = true)
	@Override
	public PhotoModel findByPrimaryKey(Long id) {
		Photo photo = photoRepo.selectByPrimaryKey(id);
		return beanMapper.map(photo, PhotoModel.class);
	}

	@Transactional(readOnly = true)
	@Override
	public long selectCount(PhotoModel photoModel) {
		return photoRepo.selectCount(beanMapper.map(photoModel, Photo.class));
	}

	@Transactional(readOnly = true)
	@Override
	public List<PhotoModel> selectPage(PhotoModel photoModel, Pageable pageable) {
		Photo photo = beanMapper.map(photoModel, Photo.class);
		return beanMapper.mapAsList(photoRepo.selectPage(photo, pageable), PhotoModel.class);
	}

	@Transactional
	@Override
	public int updateByPrimaryKey(PhotoModel photoModel) {
		return photoRepo.updateByPrimaryKey(beanMapper.map(photoModel, Photo.class));
	}

	@Transactional
	@Override
	public int updateByPrimaryKeySelective(PhotoModel photoModel) {
		return photoRepo.updateByPrimaryKeySelective(beanMapper.map(photoModel, Photo.class));
	}



}
