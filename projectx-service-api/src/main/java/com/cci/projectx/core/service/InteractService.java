
package com.cci.projectx.core.service;

import com.cci.projectx.core.model.InteractModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface InteractService {

    public int create(InteractModel interactModel);

    public int createSelective(InteractModel interactModel);

    public InteractModel findByPrimaryKey(Long id);

    public int updateByPrimaryKey(InteractModel interactModel);

    public int updateByPrimaryKeySelective(InteractModel interactModel);

    public int deleteByPrimaryKey(Long id);

    public long selectCount(InteractModel interactModel);

    public List<InteractModel> selectPage(InteractModel interactModel, Pageable pageable,Long userId);

    public Page<InteractModel> selectPageByCircleId(Long circleId, Pageable pageable,Long userId);

    public Page<InteractModel> selectPageByFriend(Long userId,Pageable pageable);

}