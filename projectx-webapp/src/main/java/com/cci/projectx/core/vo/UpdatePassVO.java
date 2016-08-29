package com.cci.projectx.core.vo;

/**
 * Created by haiquanli on 16/7/29.
 */
public class UpdatePassVO {

    private String oldPassword;
    private String newPassword;

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }
}
