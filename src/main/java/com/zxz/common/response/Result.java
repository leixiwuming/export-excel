package com.zxz.common.response;

import com.zxz.common.config.HttpStatus;

/**
 * @author 24447
 */
public class Result<T> {
    private int code;
    private String msg;
    private T data;

    private Result(int code) {
        this.code = code;
    }

    private Result(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    private Result(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static Result success(String msg) {
        return new Result(HttpStatus.OK.getValue(), msg);
    }

    public static Result success() {
        return new Result(HttpStatus.OK.getValue());
    }

    public static Result fail(String msg) {
        return new Result(HttpStatus.INTERNAL_SERVER_ERROR.getValue(), msg);
    }

    public static Result fail(int code, String msg) {
        return new Result(code, msg);
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public T getData() {
        return data;
    }

}
