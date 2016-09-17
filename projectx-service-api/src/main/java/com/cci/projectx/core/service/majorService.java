
package com.cci.projectx.core.service;

import com.cci.projectx.core.model.MajorModel;
import com.cci.projectx.core.model.UserModel;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MajorService {

    public int create(MajorModel majorModel);

    public int createSelective(MajorModel majorModel);

    public MajorModel findByPrimaryKey(Long id);

    public int updateByPrimaryKey(MajorModel majorModel);

    public int updateByPrimaryKeySelective(MajorModel majorModel);

    public int deleteByPrimaryKey(Long id);

    public long selectCount(MajorModel majorModel);

    public List<MajorModel> selectPage(MajorModel majorModel, Pageable pageable);

    public List<MajorModel> getMajor(MajorModel model);

    public int findCountByName(String name);

    public Long findIdByName(String name);

    public List<UserModel> getOneRelatCompany(Long userId, String name);

    public List<UserModel> getTwoRelatCompany(Long userId, String name);
}