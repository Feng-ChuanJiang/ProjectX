package com.cci.projectx.core.service.impl;

import com.cci.projectx.core.entity.Audio;
import com.cci.projectx.core.model.AudioModel;
import com.cci.projectx.core.repository.AudioRepository;
import com.cci.projectx.core.service.AudioService;
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
public class AudioServiceImpl implements AudioService {
	private final Logger logger = LoggerFactory.getLogger(AudioServiceImpl.class);

	@Autowired
	private BeanMapper beanMapper;

	@Autowired
	private AudioRepository audioRepo;

	@Value("${audio.base.url}")
	private String baseDirectory;

	@Transactional
	@Override
	public int create(AudioModel audioModel) {
		return audioRepo.insert(beanMapper.map(audioModel, Audio.class));
	}

	@Transactional
	@Override
	public int createSelective(AudioModel audioModel) {
		Audio audio = beanMapper.map(audioModel,Audio.class);
		int retVal = audioRepo.insertSelective(audio);
		audioModel.setId(audio.getId());
		return retVal;
	}

	@Transactional
	@Override
	public int deleteByPrimaryKey(Long id) {
		return audioRepo.deleteByPrimaryKey(id);
	}

	@Transactional
	@Override
	public int deleteAudio(Long id) {
		AudioModel audioModel = findByPrimaryKey(id);
		deleteByPrimaryKey(id);
		try {
			FileUtils.forceDelete(new File(baseDirectory + "/" + audioModel.getPath()));
		} catch (IOException e) {
			logger.error("unexpected error,", e);
		}
		return 0;
	}

	@Transactional(readOnly = true)
	@Override
	public AudioModel findByPrimaryKey(Long id) {
		Audio audio = audioRepo.selectByPrimaryKey(id);
		return beanMapper.map(audio, AudioModel.class);
	}

	@Transactional(readOnly = true)
	@Override
	public long selectCount(AudioModel audioModel) {
		return audioRepo.selectCount(beanMapper.map(audioModel, Audio.class));
	}

	@Transactional(readOnly = true)
	@Override
	public List<AudioModel> selectPage(AudioModel audioModel, Pageable pageable) {
		Audio audio = beanMapper.map(audioModel, Audio.class);
		return beanMapper.mapAsList(audioRepo.selectPage(audio, pageable), AudioModel.class);
	}

	@Transactional
	@Override
	public int updateByPrimaryKey(AudioModel audioModel) {
		return audioRepo.updateByPrimaryKey(beanMapper.map(audioModel, Audio.class));
	}

	@Transactional
	@Override
	public int updateByPrimaryKeySelective(AudioModel audioModel) {
		return audioRepo.updateByPrimaryKeySelective(beanMapper.map(audioModel, Audio.class));
	}

}
