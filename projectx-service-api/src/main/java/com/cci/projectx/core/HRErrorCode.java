package com.cci.projectx.core;

import com.wlw.pylon.core.ErrorCode;

/**
 * Created by haiquanli on 16/7/28.
 */
public class HRErrorCode extends ErrorCode{

    public static final ErrorCode UN_LOGIN = ErrorCode("10000", "未登录或session过期");

    public static final ErrorCode USER_NOT_EXISTED = ErrorCode("20000", "用户不存在");
    public static final ErrorCode PASSWORD_INCORRECT = ErrorCode("20001", "密码不正确");
    public static final ErrorCode OLD_PASSWORD_INCORRECT = ErrorCode("20002", "旧密码不正确");
    public static final ErrorCode ROLE_INVALID = ErrorCode("20003", "角色非法");
    public static final ErrorCode USER_HAVE_EXISTED = ErrorCode("20003", "该用户名已存在");

    public static final ErrorCode TASK_STATUS_INCORRECT = ErrorCode("30000", "任务状态不对");


    public static final ErrorCode HAVE_SIGNED = ErrorCode("40000", "已经签到过");

    protected HRErrorCode(String code, String errorMsg) {
        super(code, errorMsg);
    }
}
