package com.cci.projectx.core.neorepository;


import com.cci.projectx.core.domain.UserNeo;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by 33303 on 2016/9/11.
 */
@Repository
public interface UserNeoRepository extends GraphRepository<UserNeo> {
    UserNeo findByUserId(Long userId);


    //添加好友关系
    @Query("match (u:User{userId:{0}}),(a:User{userId:{1}})create (u)-[:FRIEND]->(a),(a)-[:FRIEND]->(u)")
    void addFriend(Long userId1,Long userId2);

    //删除好友关系
    @Query("match (u:User{userId:{0}})-[r:FRIEND]-(a:User{userId:{1}})delete r")
    void deleteFriend(Long userId1,Long userId2);



}
