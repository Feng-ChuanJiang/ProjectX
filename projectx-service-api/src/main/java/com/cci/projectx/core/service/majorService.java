
package com.cci.projectx.core.service;

import com.cci.projectx.core.model.majorModel;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface majorService{

public int create(majorModel majorModel);

public int createSelective(majorModel majorModel);

public majorModel findByPrimaryKey(Long id);

public int updateByPrimaryKey(majorModel majorModel);

public int updateByPrimaryKeySelective(majorModel majorModel);

public int deleteByPrimaryKey(Long id);

public long selectCount(majorModel majorModel);

public List<majorModel> selectPage(majorModel majorModel, Pageable pageable);

    public List<majorModel> getMajor(majorModel model);
}