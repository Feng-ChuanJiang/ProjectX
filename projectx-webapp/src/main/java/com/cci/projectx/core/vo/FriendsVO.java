package com.cci.projectx.core.vo;

/**
 * Created by xiaowei on 2016-08-30.
 */
public class FriendsVO {
    private Long userId;
    private Long friendId;
    private Long state;

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

    public Long getState() {
        return state;
    }

    public void setState(Long state) {
        this.state = state;
    }
}
