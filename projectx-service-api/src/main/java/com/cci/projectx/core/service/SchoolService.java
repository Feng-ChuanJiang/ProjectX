
package com.cci.projectx.core.service;

import com.cci.projectx.core.model.SchoolModel;
import com.cci.projectx.core.model.UserModel;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface SchoolService {

    public int create(SchoolModel schoolModel);

    public int createSelective(SchoolModel schoolModel);

    public SchoolModel findByPrimaryKey(Long id);

    public int updateByPrimaryKey(SchoolModel schoolModel);

    public int updateByPrimaryKeySelective(SchoolModel schoolModel);

    public int deleteByPrimaryKey(Long id);

    public long selectCount(SchoolModel schoolModel);

    public List<SchoolModel> selectPage(SchoolModel schoolModel, Pageable pageable);

    public List<SchoolModel> getSchool(SchoolModel schoolModel);

    public int findCountByName(String name);

    public Long findIdByName(String name);

    public List<UserModel> getOneRelatCompany(Long userId, String name);

    public List<UserModel> getTwoRelatCompany(Long userId, String name);
}