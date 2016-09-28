
package com.cci.projectx.core.service;

import com.cci.projectx.core.model.DiscussInviteModel;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface DiscussInviteService {

    public int create(DiscussInviteModel discussInviteModel);

    public int createSelective(DiscussInviteModel discussInviteModel);

    public DiscussInviteModel findByPrimaryKey(Long id);

    public int updateByPrimaryKey(DiscussInviteModel discussInviteModel);

    public int updateByPrimaryKeySelective(DiscussInviteModel discussInviteModel);

    public int deleteByPrimaryKey(Long id);

    public long selectCount(DiscussInviteModel discussInviteModel);

    public List<DiscussInviteModel> selectPage(DiscussInviteModel discussInviteModel, Pageable pageable);

    public int deleteByPrimary(DiscussInviteModel discussInvite);

    public int deleteByPrimaryDiscussId(Long discussId);

}