
package com.cci.projectx.core.service;

import com.cci.projectx.core.model.CommentModel;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CommentService {

public int create(CommentModel commentModel);

public int createSelective(CommentModel commentModel);

public CommentModel findByPrimaryKey(Long id);

public int updateByPrimaryKey(CommentModel commentModel);

public int updateByPrimaryKeySelective(CommentModel commentModel);

public int deleteByPrimaryKey(Long id);

public long selectCount(CommentModel commentModel);

public List<CommentModel> selectPage(CommentModel commentModel, Pageable pageable);

}