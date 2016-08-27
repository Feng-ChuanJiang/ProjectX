
package com.cci.projectx.core.service;

import com.cci.projectx.core.model.ImageModel;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface ImageService{

public int create(ImageModel imageModel);

public int createSelective(ImageModel imageModel);

public ImageModel findByPrimaryKey(Long id);

public int updateByPrimaryKey(ImageModel imageModel);

public int updateByPrimaryKeySelective(ImageModel imageModel);

public int deleteByPrimaryKey(Long id);

public long selectCount(ImageModel imageModel);

public List<ImageModel> selectPage(ImageModel imageModel,Pageable pageable);

}