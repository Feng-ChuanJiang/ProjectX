package com.cci.projectx.core;

import com.wlw.pylon.core.ErrorCode;

/**
 * Created by haiquanli on 16/7/28.
 */
public class HRErrorCode extends ErrorCode {

    public static final ErrorCode UN_LOGIN = ErrorCode("10000", "未登录或session过期");

    public static final ErrorCode USER_NOT_EXISTED = ErrorCode("20000", "用户不存在");
    public static final ErrorCode PASSWORD_INCORRECT = ErrorCode("20001", "密码不正确");
    public static final ErrorCode OLD_PASSWORD_INCORRECT = ErrorCode("20002", "旧密码不正确");
    public static final ErrorCode ROLE_INVALID = ErrorCode("20003", "角色非法");
    public static final ErrorCode USER_HAVE_EXISTED = ErrorCode("20004", "该用户名已存在");
    public static final ErrorCode CAPTCHA_IS_NULL = ErrorCode("20005", "验证码不能为空");
    public static final ErrorCode USER_IS_NULL = ErrorCode("20005", "电话不能为空");
    public static final ErrorCode PASSWORD_ID_NULL = ErrorCode("20005", "密码不能为空");
    public static final ErrorCode CAPTCHA_INCORRECT = ErrorCode("20006", "验证码不正确");

    public static final ErrorCode RUNUSER_HAVE_EXISIED = ErrorCode("30000", "用户已经添加过跑步信息，请更新");


    public static final ErrorCode HAVE_SIGNED = ErrorCode("40000", "已经签到过");

    protected HRErrorCode(String code, String errorMsg) {
        super(code, errorMsg);
    }
}
