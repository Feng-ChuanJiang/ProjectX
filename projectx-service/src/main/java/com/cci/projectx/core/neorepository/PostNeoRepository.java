package com.cci.projectx.core.neorepository;

import com.cci.projectx.core.domain.PostNeo;
import com.cci.projectx.core.domain.UserNeo;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

/**
 * Created by 33303 on 2016/9/11.
 */
@Repository
public interface PostNeoRepository extends GraphRepository<PostNeo> {
    PostNeo findByPostId(Long postId);
    //根据用户和职位名称查找二度人脉
    @Query("MATCH (p:User { userId:{0}})-[r:FRIEND]->(m:User)-[a:FRIEND]->(b:User),(b)-[s:POST]->(c:Post{name:{1}}) return b")
    Collection<UserNeo> getConnecTwoUserFromName(Long userId, String postName);
    //根据用户和职位名称查找一度度人脉
    @Query("MATCH (p:User{ userId:{0}})-[r:FRIEND]->(m:User),(m)-[s:POST]->(c:Post{name:{1}}) return m")
    Collection<UserNeo> getConnecOneUserFromName(Long userId, String postName);

    //删除职位关系
    @Query("match (u:User{userId:{0}})-[r:POST]->(s:Post{postId:{1}}) delete r")
    void deletePostRelat(Long userId,Long postId);
    //添加职位关系
    @Query(" match (u:User{userId:{0}}),(a:Post{postId:{1}})create (u)-[:POST]->(a)")
    void addPostRelat(Long userId,Long postId);
}
