
package com.cci.projectx.core.service;

import com.cci.projectx.core.model.SModel;
import java.util.Date;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface SService{

public int create(SModel sModel);

public int createSelective(SModel sModel);

public SModel findByPrimaryKey(Long id);

public int updateByPrimaryKey(SModel sModel);

public int updateByPrimaryKeySelective(SModel sModel);

public int deleteByPrimaryKey(Long id);

public long selectCount(SModel sModel);

public List<SModel> selectPage(SModel sModel,Pageable pageable);

}