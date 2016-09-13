package com.cci.projectx.core.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.voodoodyne.jackson.jsog.JSOGGenerator;
import org.neo4j.ogm.annotation.EndNode;
import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.RelationshipEntity;
import org.neo4j.ogm.annotation.StartNode;

/**
 * Created by 33303 on 2016/9/10.
 */
@JsonIdentityInfo(generator=JSOGGenerator.class)
@RelationshipEntity(type = "FRIEND")
public class UserRelatFriend {
    @GraphId
    private Long id;
    @StartNode
    private UserNeo user;
    @EndNode
    private UserNeo friend;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserNeo getUser() {
        return user;
    }

    public void setUser(UserNeo user) {
        this.user = user;
    }

    public UserNeo getFriend() {
        return friend;
    }

    public void setFriend(UserNeo friend) {
        this.friend = friend;
    }
}
