package com.sxlinks.common.exception;

/**
 * @author rickliu
 * @desc 描述信息
 * @date 2022/5/26
 */
public class MiniException extends RuntimeException {
    private Integer code;
    private String msg;

    public MiniException(Integer code, String msg) {
        super(msg);
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return this.code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return this.msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
