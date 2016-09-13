package com.cci.projectx.core.vo;

/**
 * Created by 33303 on 2016/9/13.
 */
public class RegisterVO {
    private String mobilePhone;
    private String password;
    private String captcha;

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCaptcha() {
        return captcha;
    }

    public void setCaptcha(String captcha) {
        this.captcha = captcha;
    }
}
