
package com.cci.projectx.core.service;

import com.cci.projectx.core.model.CircleModel;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CircleService {

    public int create(CircleModel circleModel);

    public int createSelective(CircleModel circleModel);

    public CircleModel findByPrimaryKey(Long id);

    public List<CircleModel> findByUserId(Long id);

    public int updateByPrimaryKey(CircleModel circleModel);

    public int updateByPrimaryKeySelective(CircleModel circleModel);

    public int deleteByPrimaryKey(Long id);

    public long selectCount(CircleModel circleModel);

    public List<CircleModel> selectPage(CircleModel circleModel, Pageable pageable);

}