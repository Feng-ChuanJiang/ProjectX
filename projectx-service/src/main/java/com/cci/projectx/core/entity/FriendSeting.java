package com.cci.projectx.core.entity;

public class FriendSeting {
    private Long id;

    private Long userId;

    private Long friendId;

    private Integer hisInteract;

    private Integer myInteract;

    private Integer blacklist;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getFriendId() {
        return friendId;
    }

    public void setFriendId(Long friendId) {
        this.friendId = friendId;
    }

    public Integer getHisInteract() {
        return hisInteract;
    }

    public void setHisInteract(Integer hisInteract) {
        this.hisInteract = hisInteract;
    }

    public Integer getMyInteract() {
        return myInteract;
    }

    public void setMyInteract(Integer myInteract) {
        this.myInteract = myInteract;
    }

    public Integer getBlacklist() {
        return blacklist;
    }

    public void setBlacklist(Integer blacklist) {
        this.blacklist = blacklist;
    }
}