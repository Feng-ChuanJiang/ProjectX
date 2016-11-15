package com.cci.projectx.core.neorepository;

import com.cci.projectx.core.domain.MajorNeo;
import com.cci.projectx.core.domain.UserNeo;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Map;

/**
 * Created by 33303 on 2016/9/11.
 */
@Repository
public interface MajorNeoRepository extends GraphRepository<MajorNeo> {
    MajorNeo findByMajorId(Long majorId);
    //根据用户和专业名称查找二度人脉
    @Query("MATCH (p:User { userId:{0}})-[r:FRIEND]->(m:User)-[a:FRIEND]->(b:User),(b)-[s:MAJOR]->(c:Major{name:{1}}) where b.userId<>p.userId  return b.userId as userId,m.userId as fridId")
    Collection<Map<String,Object>> getConnecTwoUserFromName(Long userId, String majorName);
    //根据用户和专业名称查找一度度人脉
    @Query("MATCH (p:User{ userId:{0}})-[r:FRIEND]->(m:User),(m)-[s:MAJOR]->(c:Major{name:{1}}) return m")
    Collection<UserNeo> getConnecOneUserFromName(Long userId, String majorName);

    //删除专业关系
    @Query("match (u:User{userId:{0}})-[r:MAJOR]->(s:Major{majorId:{1}}) delete r")
    void deleteMajorRelat(Long userId,Long majorId);
    //添加专业关系
    @Query(" match (u:User{userId:{0}}),(a:Major{majorId:{1}})create (u)-[:MAJOR]->(a)")
    void addMajorRelat(Long userId,Long majorId);
}
