
package com.cci.projectx.core.service;

import com.cci.projectx.core.model.PostModel;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PostService{

public int create(PostModel postModel);

public int createSelective(PostModel postModel);

public PostModel findByPrimaryKey(Long id);

public int updateByPrimaryKey(PostModel postModel);

public int updateByPrimaryKeySelective(PostModel postModel);

public int deleteByPrimaryKey(Long id);

public long selectCount(PostModel postModel);

public List<PostModel> selectPage(PostModel postModel, Pageable pageable);

}