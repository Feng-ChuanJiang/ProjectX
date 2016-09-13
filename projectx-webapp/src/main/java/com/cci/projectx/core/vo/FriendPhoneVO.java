package com.cci.projectx.core.vo;

import java.util.List;

/**
 * Created by 33303 on 2016/9/2.
 */
public class FriendPhoneVO {
    Long userId;
    List<String> phones;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public List<String> getPhones() {
        return phones;
    }

    public void setPhones(List<String> phones) {
        this.phones = phones;
    }
}
