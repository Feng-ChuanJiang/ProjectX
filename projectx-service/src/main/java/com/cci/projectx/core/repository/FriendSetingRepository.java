package com.cci.projectx.core.repository;

import com.cci.projectx.core.entity.FriendSeting;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface FriendSetingRepository {
    int deleteByPrimaryKey(@Param("id") Long id);

    int insert(@Param("friendseting") FriendSeting friendseting);

    int insertSelective(@Param("friendseting") FriendSeting friendseting);

    FriendSeting selectByPrimaryKey(@Param("id") Long id);

    int updateByPrimaryKeySelective(@Param("friendseting") FriendSeting friendseting);

    int updateByPrimaryKey(@Param("friendseting") FriendSeting friendseting);

    int selectCount(@Param("friendseting") FriendSeting friendseting);

    java.util.List<com.cci.projectx.core.entity.FriendSeting> selectPage(@Param("friendseting") FriendSeting friendseting, @Param("pageable") Pageable pageable);
}