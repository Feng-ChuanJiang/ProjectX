package com.cci.projectx.core.repository;

import com.cci.projectx.core.entity.InteractGroup;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface InteractGroupRepository {
    int deleteByPrimaryKey(@Param("id") Long id);

    int insert(@Param("interactgroup") InteractGroup interactgroup);

    int insertSelective(@Param("interactgroup") InteractGroup interactgroup);

    InteractGroup selectByPrimaryKey(@Param("id") Long id);

    int updateByPrimaryKeySelective(@Param("interactgroup") InteractGroup interactgroup);

    int updateByPrimaryKey(@Param("interactgroup") InteractGroup interactgroup);

    int selectCount(@Param("interactgroup") InteractGroup interactgroup);

    java.util.List<com.cci.projectx.core.entity.InteractGroup> selectPage(@Param("interactgroup") InteractGroup interactgroup, @Param("pageable") Pageable pageable);
}