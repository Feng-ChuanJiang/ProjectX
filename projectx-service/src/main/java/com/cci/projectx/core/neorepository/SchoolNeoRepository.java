package com.cci.projectx.core.neorepository;

import com.cci.projectx.core.domain.SchoolNeo;
import com.cci.projectx.core.domain.UserNeo;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

/**
 * Created by 33303 on 2016/9/11.
 */
@Repository
public interface SchoolNeoRepository extends GraphRepository<SchoolNeo> {
    SchoolNeo findBySchoolId(Long schoolId);
    //根据用户和学校名称查找二度人脉
    @Query("MATCH (p:User { userId:{0}})-[r:FRIEND]->(m:User)-[a:FRIEND]->(b:User),(b)-[s:SCHOOL]->(c:School{name:{1}}) return b")
    Collection<UserNeo> getConnecTwoUserFromName(Long userId, String schoolName);
    //根据用户和学校名称查找一度度人脉
    @Query("MATCH (p:User{ userId:{0}})-[r:FRIEND]->(m:User),(m)-[s:SCHOOL]->(c:School{name:{1}}) return m")
    Collection<UserNeo> getConnecOneUserFromName(Long userId, String schoolName);

    //删除学校关系
    @Query("match (u:User{userId:{0}})-[r:SCHOOL]->(s:School{schoolId:{1}}) delete r")
    void deleteSchoolRelat(Long userId,Long schoolId);
    //添加学校关系
    @Query(" match (u:User{userId:{0}}),(a:School{schoolId:{1}})create (u)-[:SCHOOL]->(a)")
    void addSchoolRelat(Long userId,Long schoolId);


}
