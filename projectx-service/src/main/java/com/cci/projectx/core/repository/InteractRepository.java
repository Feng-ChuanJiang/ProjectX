package com.cci.projectx.core.repository;

import com.cci.projectx.core.entity.Interact;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface InteractRepository {
    int deleteByPrimaryKey(@Param("id") Long id);

    int insert(@Param("interact") Interact interact);

    int insertSelective(@Param("interact") Interact interact);

    Interact selectByPrimaryKey(@Param("id") Long id);

    int updateByPrimaryKeySelective(@Param("interact") Interact interact);

    int updateByPrimaryKey(@Param("interact") Interact interact);

    int selectCount(@Param("interact") Interact interact);

    java.util.List<com.cci.projectx.core.entity.Interact> selectPage(@Param("interact") Interact interact, @Param("pageable") Pageable pageable);
}