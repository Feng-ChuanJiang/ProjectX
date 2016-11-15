package com.cci.projectx.core.neorepository;


import com.cci.projectx.core.domain.DepartmentNeo;
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
public interface DepartmentNeoRepository extends GraphRepository<DepartmentNeo> {
    DepartmentNeo findByDepartmentId(Long departmentId);

    //根据用户和部门名称查找二度人脉
    @Query("MATCH (p:User { userId:{0}})-[r:FRIEND]->(m:User)-[a:FRIEND]->(b:User),(b)-[s:DEPARTMENT]->(c:Department{name:{1}}) where b.userId<>p.userId  return b.userId as userId,m.userId as fridId")
    Collection<Map<String,Object>> getConnecTwoUserFromName(Long userId, String departmentName);
    //根据用户和部门名称查找一度度人脉
    @Query("MATCH (p:User{ userId:{0}})-[r:FRIEND]->(m:User),(m)-[s:DEPARTMENT]->(c:Department{name:{1}}) return m")
    Collection<UserNeo> getConnecOneUserFromName(Long userId, String departmentName);

    //删除部门关系
    @Query("match (u:User{userId:{0}})-[r:DEPARTMENT]->(s:Department{departmentId:{1}}) delete r")
    void deleteDepartmentRelat(Long userId,Long departmentId);
    //添加部门关系
    @Query(" match (u:User{userId:{0}}),(a:Department{departmentId:{1}})create (u)-[:DEPARTMENT]->(a)")
    void addDepartmentRelat(Long userId,Long departmentId);
}
