package com.cci.projectx.core.neorepository;

import com.cci.projectx.core.domain.LndustryNeo;
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
public interface LndustryNeoRepository extends GraphRepository<LndustryNeo> {
    LndustryNeo findByLndustryId(Long lndustryId);
    //根据用户和行业名称查找二度人脉
    @Query("MATCH (p:User { userId:{0}})-[r:FRIEND]->(m:User)-[a:FRIEND]->(b:User),(b)-[s:LNDUSTRY]->(c:Lndustry{name:{1}}) where b.userId<>p.userId  return b.userId as userId,m.userId as fridId")
    Collection<Map<String,Object>> getConnecTwoUserFromName(Long userId, String lndustryName);
    //根据用户和行业名称查找一度度人脉
    @Query("MATCH (p:User{ userId:{0}})-[r:FRIEND]->(m:User),(m)-[s:LNDUSTRY]->(c:Lndustry{name:{1}}) return m")
    Collection<UserNeo> getConnecOneUserFromName(Long userId, String lndustryName);

    //删除行业关系
    @Query("match (u:User{userId:{0}})-[r:LNDUSTRY]->(s:Lndustry{lndustryId:{1}}) delete r")
    void deleteLndustryRelat(Long userId,Long lndustryId);
    //添加行业关系
    @Query(" match (u:User{userId:{0}}),(a:Lndustry{lndustryId:{1}})create (u)-[:LNDUSTRY]->(a)")
    void addLndustryRelat(Long userId,Long lndustryId);

}
