
package com.cci.projectx.core.service;

import com.cci.projectx.core.model.LndustryModel;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface LndustryService{

public int create(LndustryModel lndustryModel);

public int createSelective(LndustryModel lndustryModel);

public LndustryModel findByPrimaryKey(Long id);

public int updateByPrimaryKey(LndustryModel lndustryModel);

public int updateByPrimaryKeySelective(LndustryModel lndustryModel);

public int deleteByPrimaryKey(Long id);

public long selectCount(LndustryModel lndustryModel);

public List<LndustryModel> selectPage(LndustryModel lndustryModel, Pageable pageable);
    public List<LndustryModel> getLndustry(LndustryModel lndustryModel);

}