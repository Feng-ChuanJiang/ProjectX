
package com.cci.projectx.core.service;

import com.cci.projectx.core.entity.WorkingExperience;
import com.cci.projectx.core.model.WorkingExperienceModel;
import java.util.Date;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface WorkingExperienceService{

public int create(WorkingExperienceModel workingExperienceModel);

public int createSelective(WorkingExperienceModel workingExperienceModel);

public WorkingExperienceModel findByPrimaryKey(Long id);

public int updateByPrimaryKey(WorkingExperienceModel workingExperienceModel);

public int updateByPrimaryKeySelective(WorkingExperienceModel workingExperienceModel);

public int deleteByPrimaryKey(Long id);

public long selectCount(WorkingExperienceModel workingExperienceModel);

public List<WorkingExperienceModel> selectPage(WorkingExperienceModel workingExperienceModel,Pageable pageable);

    public List<WorkingExperience> getWorkingByWorkInfo(WorkingExperience workingExperience);

}