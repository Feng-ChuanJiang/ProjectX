package com.cci.projectx.core.neorepository;

import com.cci.projectx.core.domain.CompanyNeo;
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
public interface CompanyNeoRepository extends GraphRepository<CompanyNeo> {
    CompanyNeo findByCompanyId(Long companyId);

    //根据用户和公司名称查找二度人脉
    @Query("MATCH (p:User { userId:{0}})-[r:FRIEND]->(m:User)-[a:FRIEND]->(b:User),(b)-[s:COMPANY]->(c:Company{name:{1}}) where b.userId<>p.userId  return b.userId as userId,m.userId as fridId")
    Collection<Map<String,Object>> getConnecTwoUserFromName(Long userId, String companyName);
    //根据用户和公司名称查找一度度人脉
    @Query("MATCH (p:User{ userId:{0}})-[r:FRIEND]->(m:User),(m)-[s:COMPANY]->(c:Company{name:{1}}) return m")
    Collection<UserNeo> getConnecOneUserFromName(Long userId, String companyName);

    //删除公司关系
    @Query("match (u:User{userId:{0}})-[r:COMPANY]->(s:Company{companyId:{1}}) delete r")
    void deleteCompanyRelat(Long userId,Long companyId);
    //添加公司关系
    @Query(" match (u:User{userId:{0}}),(a:Company{companyId:{1}})create (u)-[:COMPANY]->(a)")
    void addCompanyRelat(Long userId,Long companyId);
}
