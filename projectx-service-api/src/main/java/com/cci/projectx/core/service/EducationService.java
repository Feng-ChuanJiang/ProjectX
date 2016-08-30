
package com.cci.projectx.core.service;

import com.cci.projectx.core.entity.Education;
import com.cci.projectx.core.model.EducationModel;
import java.util.Date;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface EducationService{

public int create(EducationModel educationModel);

public int createSelective(EducationModel educationModel);

public EducationModel findByPrimaryKey(Long id);

public int updateByPrimaryKey(EducationModel educationModel);

public int updateByPrimaryKeySelective(EducationModel educationModel);

public int deleteByPrimaryKey(Long id);

public long selectCount(EducationModel educationModel);

public List<EducationModel> selectPage(EducationModel educationModel,Pageable pageable);

    public List<Education> getEducationByEducationInfo(Education education);
}