
package com.cci.projectx.core.service;

import com.cci.projectx.core.model.AudioModel;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AudioService {

    public int create(AudioModel audioModel);

    public int createSelective(AudioModel audioModel);

    public AudioModel findByPrimaryKey(Long id);

    public int updateByPrimaryKey(AudioModel audioModel);

    public int updateByPrimaryKeySelective(AudioModel audioModel);

    public int deleteByPrimaryKey(Long id);

    public long selectCount(AudioModel audioModel);

    public List<AudioModel> selectPage(AudioModel audioModel, Pageable pageable);

    public int deleteAudio(Long id);

}