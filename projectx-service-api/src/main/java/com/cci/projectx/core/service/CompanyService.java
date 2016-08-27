
package com.cci.projectx.core.service;

import com.cci.projectx.core.model.CompanyModel;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface CompanyService{

public int create(CompanyModel companyModel);

public int createSelective(CompanyModel companyModel);

public CompanyModel findByPrimaryKey(Long id);

public int updateByPrimaryKey(CompanyModel companyModel);

public int updateByPrimaryKeySelective(CompanyModel companyModel);

public int deleteByPrimaryKey(Long id);

public long selectCount(CompanyModel companyModel);

public List<CompanyModel> selectPage(CompanyModel companyModel,Pageable pageable);

}