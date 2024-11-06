package com.sxlinks.modules.system.util.sms;


import java.util.Arrays;
import java.util.Objects;


/**
 * 短信相关操作
 *
 * @author qcdl
 * @date 2019/6/6
 */
public enum CodeTypeEnum {

    /**
     * 用户绑定手机号
     */
    Binding(1, true),

    /**
     * 用户注册
     */
    Register(2, false),

    /**
     * 用户更换手机号
     */
    ChangeBind(3, true),

    /**
     * 用户更改密码
     */
    ChangePassword(4, true),

    /**
     * 用户忘记密码
     */
    ForgetPassword(5, false),

    /**
     * 用户登录
     */
    Login(6, false);

    private final Integer code;
    private final boolean needLogin;

    public static CodeTypeEnum parse(Integer code) {
        return Arrays.stream(CodeTypeEnum.values()).filter(obj -> Objects.equals(obj.getCode(), code)).findFirst().orElse(null);
    }

    CodeTypeEnum(Integer code, boolean needLogin) {
        this.code = code;
        this.needLogin = needLogin;
    }

    public Integer getCode() {
        return code;
    }

    public boolean isNeedLogin() {
        return needLogin;
    }
}

