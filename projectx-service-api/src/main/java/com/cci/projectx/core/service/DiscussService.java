
package com.cci.projectx.core.service;

import com.cci.projectx.core.model.DiscussModel;
import com.cci.projectx.core.model.DiscussMyModel;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface DiscussService {

    public int create(DiscussModel discussModel);

    public int createSelective(DiscussModel discussModel);

    public DiscussModel findByPrimaryKey(Long id);

    public int updateByPrimaryKey(DiscussModel discussModel);

    public int updateByPrimaryKeySelective(DiscussModel discussModel);

    public int deleteByPrimaryKey(Long id);

    public long selectCount(DiscussModel discussModel);

    public List<DiscussMyModel> selectPage(DiscussModel discussModel, Pageable pageable);

    public List<DiscussMyModel> findUserByPrimary(Long userId, String title);

    public List<DiscussMyModel> selectDiscussAll(Long userId);



}