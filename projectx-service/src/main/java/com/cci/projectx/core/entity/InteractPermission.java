package com.cci.projectx.core.entity;

public class InteractPermission {
    private Long id;

    private Long interactId;

    private Long userId;

    private Long friendId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getInteractId() {
        return interactId;
    }

    public void setInteractId(Long interactId) {
        this.interactId = interactId;
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
}